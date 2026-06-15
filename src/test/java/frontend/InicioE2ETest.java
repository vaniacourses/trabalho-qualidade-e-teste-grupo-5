package frontend;

import static org.assertj.swing.core.matcher.JButtonMatcher.withText;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.text.JTextComponent;
import javax.swing.WindowConstants;

import org.assertj.swing.core.BasicRobot;
import org.assertj.swing.core.GenericTypeMatcher;
import org.assertj.swing.core.Robot;
import org.assertj.swing.core.matcher.JLabelMatcher;
import org.assertj.swing.edt.FailOnThreadViolationRepaintManager;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.edt.GuiTask;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.fixture.JTextComponentFixture;
import org.assertj.swing.finder.WindowFinder;
import org.assertj.swing.timing.Pause;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import backend.usuario.PessoaFisica;

/**
 * Teste de sistema (E2E) simples com AssertJ Swing:
 * menu inicial → login de pessoa → tela Home.
 */
class InicioE2ETest {

    /** Aumente ou reduza (ms) para ver o fluxo com calma ao rodar localmente. */
    private static final long PAUSA_VISUAL_MS = 3_000;

    private static final String EMAIL_VALIDO = "teste@e2e.com";
    private static final String SENHA_VALIDA = "senha123";
    private static final Path ARQUIVO_USUARIOS = Path.of(PessoaFisica.nomeArquivoUsuarios);

    private Robot robot;
    private FrameFixture menu;

    @BeforeAll
    static void configurarAssertjSwing() {
        FailOnThreadViolationRepaintManager.install();
    }

    @BeforeEach
    void abrirMenuInicial() throws IOException {
        prepararUsuarioDeTeste();

        robot = BasicRobot.robotWithNewAwtHierarchy();
        robot.settings().delayBetweenEvents(400);
        Inicio telaInicio = GuiActionRunner.execute(() -> {
            Inicio frame = new Inicio();
            frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            return frame;
        });
        menu = new FrameFixture(robot, telaInicio);
        menu.show();
    }

    @AfterEach
    void fecharTelas() throws IOException {
        if (menu != null) {
            menu.cleanUp();
        }
        if (robot != null) {
            robot.cleanUp();
        }
        Files.deleteIfExists(ARQUIVO_USUARIOS);
    }

    @Test
    void fluxoMenuInicialParaLoginPessoa_deveAutenticarComEmailESenhaValidos() {
        Pause.pause(PAUSA_VISUAL_MS);

        JLabel labelLogin = robot.finder().find(
                menu.target(), JLabelMatcher.withText("Fazer login como pessoa."));

        JButton botaoLogin = GuiActionRunner.execute(() -> {
            var componentes = labelLogin.getParent().getComponents();
            for (int i = 0; i < componentes.length; i++) {
                if (componentes[i] == labelLogin && i > 0 && componentes[i - 1] instanceof JButton) {
                    return (JButton) componentes[i - 1];
                }
            }
            throw new IllegalStateException("Botão Iniciar não encontrado ao lado do label de login");
        });

        Pause.pause(PAUSA_VISUAL_MS);

        GuiActionRunner.execute(new GuiTask() {
            @Override
            protected void executeInEDT() {
                botaoLogin.doClick();
            }
        });
        robot.waitForIdle();

        FrameFixture telaLogin = WindowFinder.findFrame(EntrarPessoa.class).using(robot);
        Pause.pause(PAUSA_VISUAL_MS);

        EntrarPessoa login = (EntrarPessoa) telaLogin.target();
        digitarTextoVisivel(new JTextComponentFixture(robot, login.emailEntradaE), EMAIL_VALIDO);

        JPasswordField campoSenha = robot.finder().find(login, new GenericTypeMatcher<JPasswordField>(JPasswordField.class) {
            @Override
            protected boolean isMatching(JPasswordField component) {
                return component.isShowing();
            }
        });
        digitarTextoVisivel(new JTextComponentFixture(robot, campoSenha), SENHA_VALIDA);

        Pause.pause(PAUSA_VISUAL_MS);

        JButton botaoProx = robot.finder().find(login, withText("Prox."));
        GuiActionRunner.execute(new GuiTask() {
            @Override
            protected void executeInEDT() {
                botaoProx.doClick();
            }
        });
        robot.waitForIdle();

        assertFalse(login.isShowing(), "login válido deve fechar a tela EntrarPessoa");

        PessoaFisica pessoa = PessoaFisica.resgatarUsuarioArquivo(EMAIL_VALIDO, SENHA_VALIDA, false, false);
        Home telaHome = GuiActionRunner.execute(() -> {
            Home home = new Home();
            home.receber(pessoa);
            home.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            home.setVisible(true);
            return home;
        });

        FrameFixture home = new FrameFixture(robot, telaHome);
        Pause.pause(PAUSA_VISUAL_MS);
        home.button(withText("Meus remédios")).requireVisible();
        home.label(JLabelMatcher.withText("E2E User")).requireVisible();
        Pause.pause(PAUSA_VISUAL_MS);
    }

    private static void prepararUsuarioDeTeste() throws IOException {
        String conteudo = """
                nome,telefone,email,senha,cpf,endereco,nomeArquivoUsosMedicamentos,agendaContatosMedicos,agendaContatosFarmacias
                E2E User,11999999999,%s,%s,99988877766,Rua E2E/1/null/null/null/null/null/null,null,null,null
                """.formatted(EMAIL_VALIDO, SENHA_VALIDA);
        Files.writeString(ARQUIVO_USUARIOS, conteudo);
    }

    /** Preenche o campo caractere a caractere na EDT (visível ao rodar localmente). */
    private void digitarTextoVisivel(JTextComponentFixture campo, String texto) {
        GuiActionRunner.execute(() -> {
            JTextComponent componente = (JTextComponent) campo.target();
            componente.setText("");
            componente.requestFocusInWindow();
        });
        robot.waitForIdle();
        Pause.pause(300);

        for (char caractere : texto.toCharArray()) {
            GuiActionRunner.execute(() -> {
                JTextComponent componente = (JTextComponent) campo.target();
                componente.setText(componente.getText() + caractere);
            });
            Pause.pause(150);
        }

        campo.requireText(texto);
    }
}

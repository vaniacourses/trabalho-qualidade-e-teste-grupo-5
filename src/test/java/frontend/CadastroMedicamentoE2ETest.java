package frontend;

import static org.assertj.swing.core.matcher.JButtonMatcher.withText;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.event.ActionEvent;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.text.JTextComponent;
import javax.swing.WindowConstants;

import org.assertj.swing.core.GenericTypeMatcher;
import org.assertj.swing.core.Robot;
import org.assertj.swing.core.matcher.JLabelMatcher;
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
 * Teste de sistema (E2E) com AssertJ Swing:
 * fluxo login → cadastro de medicamento → confirmação na interface.
 */
class CadastroMedicamentoE2ETest {

    private static final String EMAIL_VALIDO = "teste.medicamento@e2e.com";
    private static final String SENHA_VALIDA = "senha123";
    private static final Path ARQUIVO_USUARIOS = Path.of(PessoaFisica.nomeArquivoUsuarios);

    private Robot robot;
    private FrameFixture menu;

    @BeforeAll
    static void configurarAssertjSwing() {
        FrontendTestSupport.instalarAssertjSwing();
    }

    @BeforeEach
    void abrirMenuInicial() throws IOException {
        FrontendTestSupport.prepararUsuarioDeTeste(EMAIL_VALIDO, SENHA_VALIDA);

        robot = FrontendTestSupport.criarRobot();
        menu = FrontendTestSupport.abrirMenuInicial(robot);
    }

    @AfterEach
    void fecharTelas() throws IOException {
        if (menu != null) {
            menu.cleanUp();
        }
        if (robot != null) {
            robot.cleanUp();
        }
        FrontendTestSupport.limparArquivosDeTeste();
        Files.deleteIfExists(Path.of("null.txt")); // Cleanup empty usages file if created
    }

    @Test
    void fluxoCadastroMedicamento() {
        Pause.pause(FrontendTestSupport.PAUSA_VISUAL_MS);

        // 1. Fazer login
        FrameFixture telaLogin = FrontendTestSupport.abrirTelaLoginPessoa(robot, menu);
        Pause.pause(FrontendTestSupport.PAUSA_VISUAL_MS);

        EntrarPessoa login = (EntrarPessoa) telaLogin.target();
        FrontendTestSupport.preencherLoginPessoa(robot, login, EMAIL_VALIDO, SENHA_VALIDA);

        Pause.pause(FrontendTestSupport.PAUSA_VISUAL_MS);
        FrontendTestSupport.clicarProx(robot, login);

        assertFalse(login.isShowing(), "login válido deve fechar a tela EntrarPessoa");

        PessoaFisica pessoa = PessoaFisica.resgatarUsuarioArquivo(EMAIL_VALIDO, SENHA_VALIDA, false, false);
        Home telaHome = GuiActionRunner.execute(() -> {
            Home h = new Home();
            h.receber(pessoa);
            h.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            h.setVisible(true);
            return h;
        });

        // 2. Aguarda a tela Home
        FrameFixture home = new FrameFixture(robot, telaHome);
        Pause.pause(FrontendTestSupport.PAUSA_VISUAL_MS);

        // 3. Clica em "Meus remédios"
        JButton meusRemedios = robot.finder().find(home.target(), withText("Meus remédios"));
        FrontendTestSupport.clicarBotao(robot, meusRemedios);

        // 4. Aguarda a tela ListaRemedios
        FrameFixture telaListaRemedios = WindowFinder.findFrame(ListaRemedios.class).using(robot);
        Pause.pause(FrontendTestSupport.PAUSA_VISUAL_MS);
        
        // 5. Clica em "Novo Remedio"
        JButton novoRemedio = robot.finder().find(telaListaRemedios.target(), withText("Novo Remedio"));
        FrontendTestSupport.clicarBotao(robot, novoRemedio);
        Pause.pause(FrontendTestSupport.PAUSA_VISUAL_MS);

        // 6. Preenche o formulário
        ListaRemedios listaRemedios = (ListaRemedios) telaListaRemedios.target();
        
        try {
            java.lang.reflect.Field fNome = ListaRemedios.class.getDeclaredField("nomeRemedio");
            fNome.setAccessible(true);
            javax.swing.JTextField nomeRemedio = (javax.swing.JTextField) fNome.get(listaRemedios);
            FrontendTestSupport.digitarTexto(robot, new JTextComponentFixture(robot, nomeRemedio), "Dipirona");

            java.lang.reflect.Field fQtd = ListaRemedios.class.getDeclaredField("quantidadeRemedio");
            fQtd.setAccessible(true);
            javax.swing.JTextField quantidadeRemedio = (javax.swing.JTextField) fQtd.get(listaRemedios);
            FrontendTestSupport.digitarTexto(robot, new JTextComponentFixture(robot, quantidadeRemedio), "20");

            java.lang.reflect.Field fDose = ListaRemedios.class.getDeclaredField("doseRemedio");
            fDose.setAccessible(true);
            javax.swing.JTextField doseRemedio = (javax.swing.JTextField) fDose.get(listaRemedios);
            FrontendTestSupport.digitarTexto(robot, new JTextComponentFixture(robot, doseRemedio), "2");

            java.lang.reflect.Field fDuracao = ListaRemedios.class.getDeclaredField("duracaoRemedio");
            fDuracao.setAccessible(true);
            javax.swing.JTextField duracaoRemedio = (javax.swing.JTextField) fDuracao.get(listaRemedios);
            FrontendTestSupport.digitarTexto(robot, new JTextComponentFixture(robot, duracaoRemedio), "10");

            java.lang.reflect.Field fSegunda = ListaRemedios.class.getDeclaredField("segunda");
            fSegunda.setAccessible(true);
            javax.swing.JCheckBox checkSegunda = (javax.swing.JCheckBox) fSegunda.get(listaRemedios);
            GuiActionRunner.execute(() -> checkSegunda.setSelected(true));

            java.lang.reflect.Field fHora = ListaRemedios.class.getDeclaredField("horaRemedio");
            fHora.setAccessible(true);
            javax.swing.JComboBox<?> comboHora = (javax.swing.JComboBox<?>) fHora.get(listaRemedios);

            java.lang.reflect.Field fIntervalo = ListaRemedios.class.getDeclaredField("intervaloRemedio");
            fIntervalo.setAccessible(true);
            javax.swing.JComboBox<?> comboIntervalo = (javax.swing.JComboBox<?>) fIntervalo.get(listaRemedios);

            GuiActionRunner.execute(() -> {
                comboHora.setSelectedItem("08hr");
                for (var listener : comboHora.getActionListeners()) {
                    listener.actionPerformed(new ActionEvent(comboHora, ActionEvent.ACTION_PERFORMED, "command"));
                }
                comboIntervalo.setEnabled(true);
                comboIntervalo.setSelectedItem("de 12 em 12 horas");
            });
            robot.waitForIdle();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Pause.pause(FrontendTestSupport.PAUSA_VISUAL_MS);

        // 7. Clica em "salvar"
        JButton salvar = robot.finder().find(telaListaRemedios.target(), withText("salvar"));
        FrontendTestSupport.clicarBotao(robot, salvar);

        Pause.pause(FrontendTestSupport.PAUSA_VISUAL_MS);

        // A tela é recarregada, precisamos encontrar a nova instância (ou a tabela da nova tela)
        FrameFixture novaTelaListaRemedios = WindowFinder.findFrame(ListaRemedios.class).using(robot);
        
        // 8. Valida se foi salvo na tabela
        novaTelaListaRemedios.table().requireRowCount(1);
        String conteudoTabela = novaTelaListaRemedios.table().contents()[0][0];
        assertTrue(conteudoTabela.contains("Dipirona"), "A tabela deve conter o remédio 'Dipirona'");
        
        Pause.pause(FrontendTestSupport.PAUSA_VISUAL_MS);
    }
}

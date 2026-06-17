package frontend;

import static org.assertj.swing.core.matcher.JButtonMatcher.withText;

import java.awt.Component;
import java.awt.Container;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;
import javax.swing.WindowConstants;

import org.assertj.swing.core.BasicRobot;
import org.assertj.swing.core.GenericTypeMatcher;
import org.assertj.swing.core.Robot;
import org.assertj.swing.core.matcher.DialogMatcher;
import org.assertj.swing.core.matcher.JLabelMatcher;
import org.assertj.swing.edt.FailOnThreadViolationRepaintManager;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.edt.GuiTask;
import org.assertj.swing.fixture.DialogFixture;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.fixture.JTextComponentFixture;
import org.assertj.swing.finder.WindowFinder;

import backend.farmacia.PessoaJuridica;
import backend.usuario.PessoaFisica;

final class FrontendTestSupport {

    /** Pausa entre passos em testes E2E executados localmente para visualizar a interface. */
    static final long PAUSA_VISUAL_MS = 500;

    private FrontendTestSupport() {
    }

    static void instalarAssertjSwing() {
        if (assertjSwingInstalado) {
            return;
        }
        FailOnThreadViolationRepaintManager.install();
        aquecerInterfaceGrafica();
        assertjSwingInstalado = true;
    }

    /** Inicializa AWT/Swing antes dos testes para evitar cold start nos cronômetros. */
    static void aquecerInterfaceGrafica() {
        GuiActionRunner.execute(() -> {
            javax.swing.JFrame aquecimento = new javax.swing.JFrame();
            aquecimento.setSize(1, 1);
            aquecimento.setVisible(true);
            aquecimento.dispose();

            Inicio menu = new Inicio();
            menu.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            menu.dispose();
        });
    }

    private static boolean assertjSwingInstalado;

    static Robot criarRobot() {
        Robot robot = BasicRobot.robotWithNewAwtHierarchy();
        robot.settings().delayBetweenEvents(0);
        robot.settings().timeoutToBeVisible(2_000);
        return robot;
    }

    static FrameFixture abrirMenuInicial(Robot robot) {
        Inicio telaInicio = GuiActionRunner.execute(() -> {
            Inicio frame = new Inicio();
            frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            return frame;
        });
        FrameFixture menu = new FrameFixture(robot, telaInicio);
        menu.show();
        return menu;
    }

    static void encerrar(Robot robot, FrameFixture... telas) {
        fecharTelas(telas);
        if (robot != null) {
            robot.cleanUp();
        }
    }

    static void fecharTelas(FrameFixture... telas) {
        for (FrameFixture tela : telas) {
            if (tela != null) {
                tela.cleanUp();
            }
        }
    }

    static JButton botaoIniciarAoLadoDoLabel(Robot robot, FrameFixture menu, String textoLabel) {
        JLabel label = robot.finder().find(menu.target(), JLabelMatcher.withText(textoLabel));
        return GuiActionRunner.execute(() -> {
            var componentes = label.getParent().getComponents();
            for (int i = 0; i < componentes.length; i++) {
                if (componentes[i] == label && i > 0 && componentes[i - 1] instanceof JButton) {
                    return (JButton) componentes[i - 1];
                }
            }
            throw new IllegalStateException("Botão Iniciar não encontrado ao lado de: " + textoLabel);
        });
    }

    static void clicarBotao(Robot robot, JButton botao) {
        GuiActionRunner.execute(new GuiTask() {
            @Override
            protected void executeInEDT() {
                botao.doClick();
            }
        });
        robot.waitForIdle();
    }

    static FrameFixture abrirTelaLoginPessoa(Robot robot, FrameFixture menu) {
        clicarBotao(robot, botaoIniciarAoLadoDoLabel(robot, menu, "Fazer login como pessoa."));
        return WindowFinder.findFrame(EntrarPessoa.class).using(robot);
    }

    static FrameFixture abrirTelaCadastroPessoa(Robot robot, FrameFixture menu) {
        clicarBotao(robot, botaoIniciarAoLadoDoLabel(robot, menu, "Fazer cadastro como pessoa."));
        return WindowFinder.findFrame(LoginPessoa.class).using(robot);
    }

    static FrameFixture abrirTelaLoginFarmacia(Robot robot, FrameFixture menu) {
        clicarBotao(robot, botaoIniciarAoLadoDoLabel(robot, menu, "Fazer login como farmácia."));
        return WindowFinder.findFrame(EntrarFarmacia.class).using(robot);
    }

    static void digitarTexto(Robot robot, JTextComponentFixture campo, String texto) {
        GuiActionRunner.execute(() -> {
            JTextComponent componente = (JTextComponent) campo.target();
            componente.setText("");
            componente.requestFocusInWindow();
        });
        robot.waitForIdle();

        for (char caractere : texto.toCharArray()) {
            GuiActionRunner.execute(() -> {
                JTextComponent componente = (JTextComponent) campo.target();
                componente.setText(componente.getText() + caractere);
            });
        }

        campo.requireText(texto);
    }

    static void digitarSenha(Robot robot, Container tela, String senha) {
        JPasswordField campoSenha = robot.finder().find(tela, new GenericTypeMatcher<JPasswordField>(JPasswordField.class) {
            @Override
            protected boolean isMatching(JPasswordField component) {
                return component.isShowing();
            }
        });
        digitarTexto(robot, new JTextComponentFixture(robot, campoSenha), senha);
    }

    static void preencherCadastroPessoa(Robot robot, LoginPessoa cadastro, DadosCadastroPessoa dados) {
        List<JTextField> camposRestantes = GuiActionRunner.execute(() -> {
            cadastro.nome_l_e.setText(dados.nome());
            cadastro.emailLPE.setText(dados.email());
            cadastro.ruaL.setText(dados.rua());

            return listarCamposTextoOrdenadosPorTela(cadastro).stream()
                    .filter(campo -> campo != cadastro.nome_l_e
                            && campo != cadastro.emailLPE
                            && campo != cadastro.ruaL)
                    .toList();
        });
        robot.waitForIdle();

        if (camposRestantes.size() < 4) {
            throw new IllegalStateException(
                    "Esperados 4 campos auxiliares no cadastro, encontrados: " + camposRestantes.size());
        }

        // CPF, telefone, número e complemento (campos privados, ordem vertical na tela)
        digitarTexto(robot, new JTextComponentFixture(robot, camposRestantes.get(0)), dados.cpf());
        digitarSenha(robot, cadastro, dados.senha());
        digitarTexto(robot, new JTextComponentFixture(robot, camposRestantes.get(1)), dados.telefone());
        digitarTexto(robot, new JTextComponentFixture(robot, camposRestantes.get(2)), dados.numero());
        digitarTexto(robot, new JTextComponentFixture(robot, camposRestantes.get(3)), dados.complemento());

        robot.waitForIdle();
    }

    static void preencherLoginPessoa(Robot robot, EntrarPessoa login, String email, String senha) {
        digitarTexto(robot, new JTextComponentFixture(robot, login.emailEntradaE), email);
        digitarSenha(robot, login, senha);
    }

    static void preencherLoginFarmacia(Robot robot, EntrarFarmacia login, String email, String senha) {
        digitarTexto(robot, new JTextComponentFixture(robot, login.emailEntradaE), email);
        digitarSenha(robot, login, senha);
    }

    static void clicarProx(Robot robot, EntrarPessoa login) {
        clicarProx(robot, (Container) login);
    }

    static void clicarProx(Robot robot, Container tela) {
        JButton botaoProx = robot.finder().find(tela, withText("Prox."));
        clicarBotao(robot, botaoProx);
    }

    static void clicarProxEmThreadSeparada(Robot robot, Container tela) {
        Thread thread = new Thread(() -> clicarProx(robot, tela));
        thread.start();
    }

    static DialogFixture aguardarDialogoMensagem(Robot robot, String mensagem) {
        DialogFixture dialogo = WindowFinder.findDialog(DialogMatcher.withTitle("Message"))
                .withTimeout(5_000)
                .using(robot);
        dialogo.label(JLabelMatcher.withText(mensagem)).requireVisible();
        return dialogo;
    }

    static DialogFixture aguardarDialogoErroLogin(Robot robot) {
        return aguardarDialogoMensagem(robot, "Erro, email ou senha incorretos!");
    }

    static void fecharDialogo(Robot robot, DialogFixture dialogo) {
        JButton botaoOk = robot.finder().find(dialogo.target(), withText("OK"));
        clicarBotao(robot, botaoOk);
    }

    static boolean algumaTelaHomeVisivel() {
        return GuiActionRunner.execute(() -> {
            for (java.awt.Frame frame : java.awt.Frame.getFrames()) {
                if (frame instanceof Home && frame.isShowing()) {
                    return true;
                }
            }
            return false;
        });
    }

    static boolean algumaTelaHomeFarmaciaVisivel() {
        return GuiActionRunner.execute(() -> {
            for (java.awt.Frame frame : java.awt.Frame.getFrames()) {
                if (frame instanceof HomeDaFarmacia && frame.isShowing()) {
                    return true;
                }
            }
            return false;
        });
    }

    static void prepararArquivoUsuariosVazio() throws IOException {
        Path p = Path.of(PessoaFisica.nomeArquivoUsuarios);
        Files.createDirectories(p.getParent());
        String cabecalho = """
                nome,telefone,email,senha,cpf,endereco,nomeArquivoUsosMedicamentos,agendaContatosMedicos,agendaContatosFarmacias
                """;
        Files.writeString(p, cabecalho);
    }

    static void prepararUsuarioDeTeste(String email, String senha) throws IOException {
        Path p = Path.of(PessoaFisica.nomeArquivoUsuarios);
        Files.createDirectories(p.getParent());
        String conteudo = """
                nome,telefone,email,senha,cpf,endereco,nomeArquivoUsosMedicamentos,agendaContatosMedicos,agendaContatosFarmacias
                E2E User,11999999999,%s,%s,99988877766,Rua E2E/1/null/null/null/null/null/null,null,null,null
                """.formatted(email, senha);
        Files.writeString(p, conteudo);
    }

    static void prepararFarmaciaDeTeste(String email, String senha) throws IOException {
        Path p = Path.of(PessoaJuridica.nomeArquivoFarmacias);
        Files.createDirectories(p.getParent());
        String conteudo = """
                nome,telefone,email,senha,cnpj,endereco,nomeArquivoEstoque,AgendaContatosClientes
                Farmacia E2E,11988887777,%s,%s,12345678000199,Rua Farmacia/10/Sala 1/null/null/null/null/null,null,null
                """.formatted(email, senha);
        Files.writeString(p, conteudo);
    }

    static void limparArquivosDeTeste() throws IOException {
        Files.deleteIfExists(Path.of(PessoaFisica.nomeArquivoUsuarios));
        Files.deleteIfExists(Path.of(PessoaJuridica.nomeArquivoFarmacias));
    }

    private static List<JTextField> listarCamposTextoOrdenadosPorTela(Container raiz) {
        List<JTextField> campos = listarCamposTexto(raiz);
        campos.sort(Comparator
                .comparingInt((JTextField campo) -> campo.getLocationOnScreen().y)
                .thenComparingInt(campo -> campo.getLocationOnScreen().x));
        return campos;
    }

    private static List<JTextField> listarCamposTexto(Container raiz) {
        List<JTextField> campos = new ArrayList<>();
        for (Component componente : raiz.getComponents()) {
            if (componente instanceof JTextField campoTexto && campoTexto.isShowing()) {
                campos.add(campoTexto);
            }
            if (componente instanceof Container container) {
                campos.addAll(listarCamposTexto(container));
            }
        }
        campos.sort(Comparator.comparingInt(campo -> campo.getBounds().y));
        return campos;
    }

    record DadosCadastroPessoa(
            String nome,
            String cpf,
            String email,
            String senha,
            String telefone,
            String rua,
            String numero,
            String complemento) {
    }
}

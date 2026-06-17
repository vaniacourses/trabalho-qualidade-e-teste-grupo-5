package frontend;

import static org.assertj.swing.core.matcher.JButtonMatcher.withText;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;

import javax.swing.WindowConstants;

import org.assertj.swing.core.Robot;
import org.assertj.swing.core.matcher.JLabelMatcher;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import backend.usuario.PessoaFisica;

/**
 * E2E avançado: cadastro de pessoa → novo acesso → login com as mesmas credenciais.
 */
class FluxoCadastroLoginE2ETest {

    private static final String EMAIL = "fluxo.e2e@test.com";
    private static final String SENHA = "senhaFluxo123";

    private Robot robot;
    private FrameFixture menu;
    private FrameFixture telaCadastro;
    private FrameFixture telaLogin;
    private FrameFixture telaHome;

    @BeforeAll
    static void configurarAssertjSwing() {
        FrontendTestSupport.instalarAssertjSwing();
    }

    @AfterEach
    void fechar() throws IOException {
        FrontendTestSupport.encerrar(robot, telaLogin, telaCadastro, menu, telaHome);
        FrontendTestSupport.limparArquivosDeTeste();
    }

    @Test
    void cadastroSeguidoDeLogin_deveAutenticarComCredenciaisCriadas() throws InterruptedException, IOException {
        FrontendTestSupport.prepararArquivoUsuariosVazio();
        robot = FrontendTestSupport.criarRobot();

        menu = FrontendTestSupport.abrirMenuInicial(robot);
        telaCadastro = FrontendTestSupport.abrirTelaCadastroPessoa(robot, menu);
        LoginPessoa cadastro = (LoginPessoa) telaCadastro.target();

        FrontendTestSupport.preencherCadastroPessoa(robot, cadastro, new FrontendTestSupport.DadosCadastroPessoa(
                "Fluxo E2E",
                "55566677788",
                EMAIL,
                SENHA,
                "21911112222",
                "Rua Fluxo",
                "100",
                "Casa"));

        FrontendTestSupport.clicarProx(robot, cadastro);
        assertNotNull(PessoaFisica.resgatarUsuarioArquivo(EMAIL, SENHA, false, false));

        menu = FrontendTestSupport.abrirMenuInicial(robot);
        telaLogin = FrontendTestSupport.abrirTelaLoginPessoa(robot, menu);
        EntrarPessoa login = (EntrarPessoa) telaLogin.target();

        FrontendTestSupport.preencherLoginPessoa(robot, login, EMAIL, SENHA);

        Thread threadProx = new Thread(() -> FrontendTestSupport.clicarProx(robot, login));
        threadProx.start();
        threadProx.join(10_000);

        assertFalse(login.isShowing(), "login após cadastro deve fechar a tela EntrarPessoa");

        PessoaFisica autenticado = PessoaFisica.resgatarUsuarioArquivo(EMAIL, SENHA, false, false);
        assertNotNull(autenticado);
        assertEquals("Fluxo E2E", autenticado.getNome());

        // 4. Home autenticada
        Home homeFrame = GuiActionRunner.execute(() -> {
            Home h = new Home();
            h.receber(autenticado);
            h.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            h.setVisible(true);
            return h;
        });

        telaHome = new FrameFixture(robot, homeFrame);
        telaHome.requireVisible();
        telaHome.label(JLabelMatcher.withText("Fluxo E2E")).requireVisible();
        telaHome.label(JLabelMatcher.withText("55566677788")).requireVisible();
        telaHome.button(withText("Meus remédios")).requireVisible();
    }
}

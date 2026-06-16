package frontend;

import static org.assertj.swing.core.matcher.JButtonMatcher.withText;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.assertj.swing.core.Robot;
import org.assertj.swing.core.matcher.JLabelMatcher;
import org.assertj.swing.fixture.DialogFixture;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.finder.WindowFinder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * E2E avançado: login de farmácia (credenciais inválidas e válidas).
 */
class EntrarFarmaciaE2ETest {

    private static final String EMAIL_VALIDO = "farmacia.e2e@test.com";
    private static final String SENHA_VALIDA = "senhaFarmacia1";

    private Robot robot;
    private FrameFixture menu;
    private FrameFixture telaLogin;
    private DialogFixture dialogo;

    @BeforeAll
    static void configurarAssertjSwing() {
        FrontendTestSupport.instalarAssertjSwing();
    }

    @BeforeEach
    void preparar() throws IOException {
        robot = FrontendTestSupport.criarRobot();
        menu = FrontendTestSupport.abrirMenuInicial(robot);
    }

    @AfterEach
    void fechar() throws IOException {
        FrontendTestSupport.encerrar(robot, telaLogin, menu);
        FrontendTestSupport.limparArquivosDeTeste();
    }

    @Test
    void loginFarmaciaComCredenciaisInvalidas_deveNegarAcesso() throws InterruptedException, IOException {
        FrontendTestSupport.prepararFarmaciaDeTeste(EMAIL_VALIDO, SENHA_VALIDA);

        telaLogin = FrontendTestSupport.abrirTelaLoginFarmacia(robot, menu);
        EntrarFarmacia login = (EntrarFarmacia) telaLogin.target();

        FrontendTestSupport.preencherLoginFarmacia(robot, login, "invalido@test.com", "senhaErrada");

        Thread threadProx = new Thread(() -> FrontendTestSupport.clicarProx(robot, login));
        threadProx.start();

        dialogo = FrontendTestSupport.aguardarDialogoErroLogin(robot);
        FrontendTestSupport.fecharDialogo(robot, dialogo);
        threadProx.join(10_000);

        assertFalse(FrontendTestSupport.algumaTelaHomeFarmaciaVisivel());

        telaLogin = WindowFinder.findFrame(EntrarFarmacia.class).using(robot);
        telaLogin.label(JLabelMatcher.withText("Insira seu email e senha:")).requireVisible();
        assertTrue(telaLogin.target().isShowing());
    }

    @Test
    void loginFarmaciaComCredenciaisValidas_deveAbrirHomeDaFarmacia() throws InterruptedException, IOException {
        FrontendTestSupport.prepararFarmaciaDeTeste(EMAIL_VALIDO, SENHA_VALIDA);

        telaLogin = FrontendTestSupport.abrirTelaLoginFarmacia(robot, menu);
        EntrarFarmacia login = (EntrarFarmacia) telaLogin.target();

        FrontendTestSupport.preencherLoginFarmacia(robot, login, EMAIL_VALIDO, SENHA_VALIDA);

        Thread threadProx = new Thread(() -> FrontendTestSupport.clicarProx(robot, login));
        threadProx.start();
        threadProx.join(10_000);

        FrameFixture homeFarmacia = WindowFinder.findFrame(HomeDaFarmacia.class).using(robot);
        homeFarmacia.button(withText("Catalogar estoque")).requireVisible();
        homeFarmacia.label(JLabelMatcher.withText("Farmacia E2E")).requireVisible();
        assertFalse(login.isShowing(), "login válido deve fechar EntrarFarmacia");

        telaLogin = homeFarmacia;
    }
}

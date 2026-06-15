package frontend;

import static org.assertj.swing.core.matcher.JButtonMatcher.withText;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.swing.JPasswordField;

import org.assertj.swing.core.GenericTypeMatcher;
import org.assertj.swing.core.Robot;
import org.assertj.swing.core.matcher.JLabelMatcher;
import org.assertj.swing.fixture.DialogFixture;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.fixture.JTextComponentFixture;
import org.assertj.swing.finder.WindowFinder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * RNF - Segurança (autenticação): credenciais inválidas não devem conceder acesso ao sistema.
 */
class EntrarPessoaSegurancaTest {

    private static final String EMAIL_INVALIDO = "invalido@teste.com";
    private static final String SENHA_INVALIDA = "senhaErrada";

    private Robot robot;
    private FrameFixture menu;
    private FrameFixture telaLogin;
    private DialogFixture dialogoErro;

    @BeforeAll
    static void configurarAssertjSwing() {
        FrontendTestSupport.instalarAssertjSwing();
    }

    @BeforeEach
    void abrirMenu() {
        robot = FrontendTestSupport.criarRobot();
        menu = FrontendTestSupport.abrirMenuInicial(robot);
    }

    @AfterEach
    void fecharTelas() {
        FrontendTestSupport.encerrar(robot, telaLogin, menu);
    }

    @Test
    void loginComCredenciaisInvalidas_deveNegarAcessoEMostrarErro() throws InterruptedException {
        telaLogin = FrontendTestSupport.abrirTelaLoginPessoa(robot, menu);
        EntrarPessoa login = (EntrarPessoa) telaLogin.target();

        FrontendTestSupport.digitarTexto(
                robot, new JTextComponentFixture(robot, login.emailEntradaE), EMAIL_INVALIDO);

        JPasswordField campoSenha = robot.finder().find(login, new GenericTypeMatcher<JPasswordField>(JPasswordField.class) {
            @Override
            protected boolean isMatching(JPasswordField component) {
                return component.isShowing();
            }
        });
        FrontendTestSupport.digitarTexto(robot, new JTextComponentFixture(robot, campoSenha), SENHA_INVALIDA);

        // Prox dispara showMessageDialog na EDT; clicar em thread separada evita deadlock
        Thread threadProx = new Thread(() -> FrontendTestSupport.clicarProx(robot, login));
        threadProx.start();

        dialogoErro = FrontendTestSupport.aguardarDialogoErroLogin(robot);
        FrontendTestSupport.fecharDialogo(robot, dialogoErro);

        threadProx.join(10_000);

        assertFalse(FrontendTestSupport.algumaTelaHomeVisivel(),
                "credenciais inválidas não devem abrir a tela Home");

        telaLogin = WindowFinder.findFrame(EntrarPessoa.class).using(robot);
        telaLogin.label(JLabelMatcher.withText("Insira seu email e senha:")).requireVisible();
        telaLogin.button(withText("Prox.")).requireEnabled();
        assertTrue(telaLogin.target().isShowing(),
                "após falha de login o usuário deve permanecer na tela de entrada");
    }
}

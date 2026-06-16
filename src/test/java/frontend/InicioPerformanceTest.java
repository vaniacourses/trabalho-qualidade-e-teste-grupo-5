package frontend;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.List;

import org.assertj.swing.core.Robot;
import org.assertj.swing.core.matcher.JLabelMatcher;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.timing.Condition;
import org.assertj.swing.timing.Pause;
import org.assertj.swing.timing.Timeout;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * RNF - Desempenho: tempos de resposta da interface devem permanecer dentro de limites aceitáveis.
 */
class InicioPerformanceTest {

    private static final List<String> OPCOES_MENU = List.of(
            "Fazer cadastro como pessoa.",
            "Fazer login como pessoa.",
            "Fazer cadastro como farmácia.",
            "Fazer login como farmácia.");

    private static final String EMAIL_VALIDO = "perf.e2e@test.com";
    private static final String SENHA_VALIDA = "senhaPerf123";

    /** Tempo máximo para o menu inicial ficar operável (ms). */
    private static final long TEMPO_MAXIMO_ABERTURA_MENU_MS = 3_000;

    /** Tempo máximo para abrir a tela de login a partir do menu (ms). */
    private static final long TEMPO_MAXIMO_NAVEGACAO_LOGIN_MS = 2_000;

    /** Tempo máximo para concluir autenticação válida após clicar em Prox. (ms). */
    private static final long TEMPO_MAXIMO_AUTENTICACAO_MS = 5_000;

    private static Robot robot;

    private FrameFixture menu;
    private FrameFixture telaLogin;

    @BeforeAll
    static void configurarAssertjSwing() {
        FrontendTestSupport.instalarAssertjSwing();
        robot = FrontendTestSupport.criarRobot();
        FrameFixture menuAquecimento = FrontendTestSupport.abrirMenuInicial(robot);
        FrontendTestSupport.fecharTelas(menuAquecimento);
    }

    @AfterAll
    static void encerrarRobot() {
        if (robot != null) {
            robot.cleanUp();
        }
    }

    @BeforeEach
    void preparar() {
        menu = null;
        telaLogin = null;
    }

    @AfterEach
    void fecharTelas() throws IOException {
        FrontendTestSupport.fecharTelas(telaLogin, menu);
        FrontendTestSupport.limparArquivosDeTeste();
    }

    @Test
    void menuInicial_deveAbrirEmTempoAceitavel() {
        menu = FrontendTestSupport.abrirMenuInicial(robot);
        robot.waitForIdle();

        long inicio = System.nanoTime();
        for (String opcao : OPCOES_MENU) {
            menu.label(JLabelMatcher.withText(opcao)).requireVisible();
        }
        robot.waitForIdle();

        assertTempoMenorOuIgual(inicio, TEMPO_MAXIMO_ABERTURA_MENU_MS,
                "menu inicial deve expor opções visíveis");
    }

    @Test
    void navegacaoMenuParaLoginPessoa_deveOcorrerEmTempoAceitavel() {
        menu = FrontendTestSupport.abrirMenuInicial(robot);

        long inicio = System.nanoTime();
        telaLogin = FrontendTestSupport.abrirTelaLoginPessoa(robot, menu);
        telaLogin.label(JLabelMatcher.withText("Insira seu email e senha:")).requireVisible();

        assertTempoMenorOuIgual(inicio, TEMPO_MAXIMO_NAVEGACAO_LOGIN_MS,
                "navegação do menu para login de pessoa");
    }

    @Test
    void autenticacaoValida_deveConcluirEmTempoAceitavel() throws InterruptedException, IOException {
        FrontendTestSupport.prepararUsuarioDeTeste(EMAIL_VALIDO, SENHA_VALIDA);
        menu = FrontendTestSupport.abrirMenuInicial(robot);
        telaLogin = FrontendTestSupport.abrirTelaLoginPessoa(robot, menu);
        EntrarPessoa login = (EntrarPessoa) telaLogin.target();

        FrontendTestSupport.preencherLoginPessoa(robot, login, EMAIL_VALIDO, SENHA_VALIDA);

        long inicio = System.nanoTime();
        Thread threadProx = new Thread(() -> FrontendTestSupport.clicarProx(robot, login));
        threadProx.start();

        Pause.pause(new Condition("login válido deve fechar a tela EntrarPessoa") {
            @Override
            public boolean test() {
                return !login.isShowing();
            }
        }, Timeout.timeout(TEMPO_MAXIMO_AUTENTICACAO_MS));

        threadProx.join(10_000);

        assertTempoMenorOuIgual(inicio, TEMPO_MAXIMO_AUTENTICACAO_MS,
                "autenticação válida de pessoa");
    }

    private static void assertTempoMenorOuIgual(long inicioNanos, long limiteMs, String descricao) {
        long decorridoMs = (System.nanoTime() - inicioNanos) / 1_000_000;
        assertTrue(decorridoMs <= limiteMs,
                () -> descricao + " demorou " + decorridoMs + " ms (limite: " + limiteMs + " ms)");
    }
}

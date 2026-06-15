package frontend;

import static org.assertj.swing.core.matcher.JButtonMatcher.withText;

import java.util.List;

import org.assertj.swing.core.Robot;
import org.assertj.swing.core.matcher.JLabelMatcher;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.timing.Condition;
import org.assertj.swing.timing.Pause;
import org.assertj.swing.timing.Timeout;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * RNF - Usabilidade: o menu inicial deve expor claramente todas as opções de entrada.
 */
class InicioUsabilidadeTest {

    private static final List<String> OPCOES_MENU = List.of(
            "Fazer cadastro como pessoa.",
            "Fazer login como pessoa.",
            "Fazer cadastro como farmácia.",
            "Fazer login como farmácia.");

    /** Tempo máximo aceitável para o menu inicial ficar operável (ms). */
    private static final long TEMPO_MAXIMO_ABERTURA_MS = 3_000;

    private Robot robot;
    private FrameFixture menu;

    @BeforeAll
    static void configurarAssertjSwing() {
        FrontendTestSupport.instalarAssertjSwing();
    }

    @BeforeEach
    void abrirMenu() {
        robot = FrontendTestSupport.criarRobot();
    }

    @AfterEach
    void fecharTelas() {
        FrontendTestSupport.encerrar(robot, menu);
    }

    @Test
    void menuInicial_deveExibirTodasOpcoesVisiveisEHabilitadas() {
        menu = FrontendTestSupport.abrirMenuInicial(robot);

        Pause.pause(new Condition("menu inicial operável em até 3 segundos") {
            @Override
            public boolean test() {
                return OPCOES_MENU.stream().allMatch(opcao -> {
                    try {
                        robot.finder().find(menu.target(), JLabelMatcher.withText(opcao));
                        return true;
                    } catch (RuntimeException e) {
                        return false;
                    }
                });
            }
        }, Timeout.timeout(TEMPO_MAXIMO_ABERTURA_MS));

        for (String opcao : OPCOES_MENU) {
            menu.label(JLabelMatcher.withText(opcao)).requireVisible();
        }

        var botoesIniciar = robot.finder().findAll(menu.target(), withText("Iniciar"));
        org.junit.jupiter.api.Assertions.assertEquals(4, botoesIniciar.size(),
                "menu inicial deve exibir quatro botões Iniciar");

        for (var botao : botoesIniciar) {
            new org.assertj.swing.fixture.JButtonFixture(robot, (javax.swing.JButton) botao)
                    .requireVisible()
                    .requireEnabled();
        }
    }
}

package frontend;

import static org.assertj.swing.core.matcher.JButtonMatcher.withText;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.text.JTextComponent;
import javax.swing.WindowConstants;

import org.assertj.swing.core.BasicRobot;
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

final class FrontendTestSupport {

    private FrontendTestSupport() {
    }

    static void instalarAssertjSwing() {
        FailOnThreadViolationRepaintManager.install();
    }

    static Robot criarRobot() {
        Robot robot = BasicRobot.robotWithNewAwtHierarchy();
        robot.settings().delayBetweenEvents(400);
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
        for (FrameFixture tela : telas) {
            if (tela != null) {
                tela.cleanUp();
            }
        }
        if (robot != null) {
            robot.cleanUp();
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
        JButton botaoLogin = botaoIniciarAoLadoDoLabel(
                robot, menu, "Fazer login como pessoa.");
        clicarBotao(robot, botaoLogin);
        return WindowFinder.findFrame(EntrarPessoa.class).using(robot);
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

    static void clicarProx(Robot robot, EntrarPessoa login) {
        JButton botaoProx = robot.finder().find(login, withText("Prox."));
        clicarBotao(robot, botaoProx);
    }

    /** JOptionPane de erro de login abre em JDialog modal separado (título "Message"). */
    static DialogFixture aguardarDialogoErroLogin(Robot robot) {
        DialogFixture dialogo = WindowFinder.findDialog(DialogMatcher.withTitle("Message"))
                .withTimeout(5_000)
                .using(robot);
        dialogo.label(JLabelMatcher.withText("Erro, email ou senha incorretos!")).requireVisible();
        return dialogo;
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
}

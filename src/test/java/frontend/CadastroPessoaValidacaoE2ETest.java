package frontend;

import static org.assertj.swing.core.matcher.JButtonMatcher.withText;
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
 * E2E avançado: validação de campos obrigatórios no cadastro de pessoa.
 */
class CadastroPessoaValidacaoE2ETest {

    private Robot robot;
    private FrameFixture menu;
    private FrameFixture telaCadastro;
    private DialogFixture dialogo;

    @BeforeAll
    static void configurarAssertjSwing() {
        FrontendTestSupport.instalarAssertjSwing();
    }

    @BeforeEach
    void preparar() throws IOException {
        FrontendTestSupport.prepararArquivoUsuariosVazio();
        robot = FrontendTestSupport.criarRobot();
        menu = FrontendTestSupport.abrirMenuInicial(robot);
    }

    @AfterEach
    void fechar() throws IOException {
        FrontendTestSupport.encerrar(robot, telaCadastro, menu);
        FrontendTestSupport.limparArquivosDeTeste();
    }

    @Test
    void cadastroSemNome_deveExibirMensagemDeValidacao() throws InterruptedException {
        telaCadastro = FrontendTestSupport.abrirTelaCadastroPessoa(robot, menu);
        LoginPessoa cadastro = (LoginPessoa) telaCadastro.target();

        FrontendTestSupport.digitarTexto(
                robot,
                new org.assertj.swing.fixture.JTextComponentFixture(robot, cadastro.emailLPE),
                "sem.nome@test.com");

        Thread threadProx = new Thread(() -> FrontendTestSupport.clicarProx(robot, cadastro));
        threadProx.start();

        dialogo = FrontendTestSupport.aguardarDialogoMensagem(
                robot, "Precisa preencher todas as opções corretamente!");
        FrontendTestSupport.fecharDialogo(robot, dialogo);
        threadProx.join(10_000);

        telaCadastro = WindowFinder.findFrame(LoginPessoa.class).using(robot);
        telaCadastro.label(JLabelMatcher.withText("Nome completo:")).requireVisible();
        telaCadastro.button(withText("Prox.")).requireEnabled();
        assertTrue(telaCadastro.target().isShowing(), "usuário deve permanecer na tela de cadastro após erro");
    }
}

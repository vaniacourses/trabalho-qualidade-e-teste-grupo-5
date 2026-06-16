package frontend;

import static org.assertj.swing.core.matcher.JButtonMatcher.withText;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;

import org.assertj.swing.core.Robot;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.finder.WindowFinder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import backend.usuario.PessoaFisica;

/**
 * E2E avançado: cadastro completo de pessoa física pela interface.
 */
class CadastroPessoaE2ETest {

    private static final String EMAIL = "cadastro.e2e@test.com";
    private static final String SENHA = "senhaCadastro1";

    private Robot robot;
    private FrameFixture menu;
    private FrameFixture telaCadastro;

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
    void cadastroCompleto_devePersistirUsuarioNoArquivo() {
        telaCadastro = FrontendTestSupport.abrirTelaCadastroPessoa(robot, menu);
        LoginPessoa cadastro = (LoginPessoa) telaCadastro.target();

        FrontendTestSupport.preencherCadastroPessoa(robot, cadastro, new FrontendTestSupport.DadosCadastroPessoa(
                "Usuario Cadastro E2E",
                "11122233344",
                EMAIL,
                SENHA,
                "21987654321",
                "Rua Cadastro",
                "42",
                "Apto 10"));

        FrontendTestSupport.clicarProx(robot, cadastro);

        PessoaFisica usuario = PessoaFisica.resgatarUsuarioArquivo(EMAIL, SENHA, false, false);
        assertNotNull(usuario, "cadastro pela GUI deve gravar o usuário no arquivo");
    }
}

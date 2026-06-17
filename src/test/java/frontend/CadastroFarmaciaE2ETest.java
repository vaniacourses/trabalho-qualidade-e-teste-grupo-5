package frontend;

import static org.assertj.swing.core.matcher.JButtonMatcher.withText;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.swing.JButton;
import javax.swing.WindowConstants;

import org.assertj.swing.core.Robot;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.edt.GuiTask;
import org.assertj.swing.fixture.DialogFixture;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.fixture.JTextComponentFixture;
import org.assertj.swing.finder.WindowFinder;
import org.assertj.swing.timing.Pause;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import backend.farmacia.PessoaJuridica;

/**
 * Teste de sistema (E2E) para o cadastro de Farmácia (Entrega 2 - Sandro).
 * Cobre o fluxo feliz e a validação de erro sem CNPJ.
 */
class CadastroFarmaciaE2ETest {

    private static final Path ARQUIVO_FARMACIAS = Path.of(PessoaJuridica.nomeArquivoFarmacias);

    private Robot robot;
    private FrameFixture menu;

    @BeforeAll
    static void configurarAssertjSwing() {
        FrontendTestSupport.instalarAssertjSwing();
    }

    @BeforeEach
    void abrirMenuInicial() {
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
    }

    @Test
    void fluxoCadastroFarmacia_comDadosValidos_deveCriarEEntrarNaHome() {
        Pause.pause(FrontendTestSupport.PAUSA_VISUAL_MS);

        // 1. Acessar a tela de Cadastro de Farmácia pelo menu
        JButton btnCadastroFarmacia = FrontendTestSupport.botaoIniciarAoLadoDoLabel(robot, menu, "Fazer cadastro como farmácia.");
        FrontendTestSupport.clicarBotao(robot, btnCadastroFarmacia);

        FrameFixture telaCadastro = WindowFinder.findFrame(LoginFarmacia.class).using(robot);
        Pause.pause(FrontendTestSupport.PAUSA_VISUAL_MS);

        LoginFarmacia cadastro = (LoginFarmacia) telaCadastro.target();

        // 2. Preencher os dados usando Reflexão (para burlar encapsulamento legado)
        try {
            preencherCampoTexto(cadastro, "emailLFE", "farmacia@e2e.com");
            preencherCampoSenha(cadastro, "senhaLFE", "senha123");
            preencherCampoTexto(cadastro, "nomeFarmaciaEntrada", "Farmácia E2E");
            preencherCampoTexto(cadastro, "cnpjFarmaciaEntrada", "12345678000199");
            preencherCampoTexto(cadastro, "numeroDaFarmaciaEntrada", "11988887777");
            preencherCampoTexto(cadastro, "ruaLF", "Rua das Flores");
            preencherCampoTexto(cadastro, "numeroL", "123");
            preencherCampoTexto(cadastro, "complementoLF", "Loja 1");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Pause.pause(FrontendTestSupport.PAUSA_VISUAL_MS);

        // 3. Clicar em "Prox."
        JButton btnProx = robot.finder().find(telaCadastro.target(), withText("Prox."));
        FrontendTestSupport.clicarBotao(robot, btnProx);

        // 4. Aguardar tela HomeDaFarmacia
        FrameFixture homeFarmacia = WindowFinder.findFrame(HomeDaFarmacia.class).using(robot);
        Pause.pause(FrontendTestSupport.PAUSA_VISUAL_MS);

        // 5. Validar que a tela da farmácia abriu corretamente verificando se a janela está visível
        assertTrue(homeFarmacia.target().isShowing(), "A tela HomeDaFarmacia deve estar visível após cadastro com sucesso.");
    }

    @Test
    void fluxoCadastroFarmacia_semCNPJ_deveExibirErro() {
        Pause.pause(FrontendTestSupport.PAUSA_VISUAL_MS);

        JButton btnCadastroFarmacia = FrontendTestSupport.botaoIniciarAoLadoDoLabel(robot, menu, "Fazer cadastro como farmácia.");
        FrontendTestSupport.clicarBotao(robot, btnCadastroFarmacia);

        FrameFixture telaCadastro = WindowFinder.findFrame(LoginFarmacia.class).using(robot);
        Pause.pause(FrontendTestSupport.PAUSA_VISUAL_MS);

        LoginFarmacia cadastro = (LoginFarmacia) telaCadastro.target();

        // Preenche tudo EXCETO o CNPJ
        try {
            preencherCampoTexto(cadastro, "emailLFE", "farmaciaerro@e2e.com");
            preencherCampoSenha(cadastro, "senhaLFE", "senha123");
            preencherCampoTexto(cadastro, "nomeFarmaciaEntrada", "Farmácia Erro");
            // cnpjFarmaciaEntrada fica vazio
            preencherCampoTexto(cadastro, "numeroDaFarmaciaEntrada", "11988887777");
            preencherCampoTexto(cadastro, "ruaLF", "Rua das Flores");
            preencherCampoTexto(cadastro, "numeroL", "123");
            preencherCampoTexto(cadastro, "complementoLF", "Loja 1");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Pause.pause(FrontendTestSupport.PAUSA_VISUAL_MS);

        // Clica em "Prox." e deve exibir diálogo de erro em vez de seguir
        FrontendTestSupport.clicarProxEmThreadSeparada(robot, cadastro);

        // Verifica diálogo de erro
        DialogFixture dialogoErro = FrontendTestSupport.aguardarDialogoMensagem(robot, "Precisa preencher todas as opções corretamente!");
        assertTrue(dialogoErro.target().isShowing(), "Deve exibir erro de opções incorretas.");
        FrontendTestSupport.fecharDialogo(robot, dialogoErro);

        Pause.pause(FrontendTestSupport.PAUSA_VISUAL_MS);
    }

    private void preencherCampoTexto(LoginFarmacia tela, String nomeVariavel, String valor) throws Exception {
        java.lang.reflect.Field field = LoginFarmacia.class.getDeclaredField(nomeVariavel);
        field.setAccessible(true);
        javax.swing.JTextField campo = (javax.swing.JTextField) field.get(tela);
        FrontendTestSupport.digitarTexto(robot, new JTextComponentFixture(robot, campo), valor);
    }

    private void preencherCampoSenha(LoginFarmacia tela, String nomeVariavel, String valor) throws Exception {
        java.lang.reflect.Field field = LoginFarmacia.class.getDeclaredField(nomeVariavel);
        field.setAccessible(true);
        javax.swing.JPasswordField campo = (javax.swing.JPasswordField) field.get(tela);
        FrontendTestSupport.digitarTexto(robot, new JTextComponentFixture(robot, campo), valor);
    }
}

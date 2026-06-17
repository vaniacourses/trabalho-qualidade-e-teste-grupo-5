package frontend;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertFalse;

import static org.assertj.swing.core.matcher.JButtonMatcher.withText;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.assertj.swing.core.GenericTypeMatcher;
import org.assertj.swing.core.Robot;
import org.assertj.swing.data.TableCell;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.finder.WindowFinder;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.fixture.JTableFixture;
import org.assertj.swing.fixture.JTextComponentFixture;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import backend.Pessoa;
import backend.usuario.PessoaFisica;

/**
 * Teste E2E alinhado ao Teste Manual TM-03: Adicionar Contato Médico.
 * Pré-condição: Estar logado como usuário na página inicial.
 */
class AgendaContatoMedicoE2ETest {

    private static final String EMAIL = "medico.e2e@test.com";
    private static final String SENHA = "senhaMedico123";

    private Robot robot;
    private FrameFixture menu;
    private FrameFixture telaLogin;
    private FrameFixture telaHome;
    private FrameFixture telaContatos;
    private FrameFixture novaTelaContatos;

    @BeforeAll
    static void configurarAssertjSwing() {
        FrontendTestSupport.instalarAssertjSwing();
    }

    @BeforeEach
    void preparar() throws IOException {
        FrontendTestSupport.prepararArquivoUsuariosVazio();
        FrontendTestSupport.prepararUsuarioDeTeste(EMAIL, SENHA);
        robot = FrontendTestSupport.criarRobot();
    }

    @AfterEach
    void fechar() throws IOException {
        FrontendTestSupport.encerrar(robot, novaTelaContatos, telaContatos, telaHome, telaLogin, menu);
        FrontendTestSupport.limparArquivosDeTeste();
    }

    @Test
    void adicionarContatoMedico_deveSeguirFluxoTM03() throws Exception {
        // --- PRÉ-CONDIÇÃO: LOGIN ---
        // 1. Abrir o menu inicial
        menu = FrontendTestSupport.abrirMenuInicial(robot);
        assertNotNull(menu, "Menu inicial deve ser aberto");

        // 2. Navegar para a tela de login como pessoa
        telaLogin = FrontendTestSupport.abrirTelaLoginPessoa(robot, menu);
        assertNotNull(telaLogin, "Tela de login deve ser exibida");
        EntrarPessoa loginFrame = (EntrarPessoa) telaLogin.target();

        // 3. Preencher credenciais e prosseguir
        FrontendTestSupport.preencherLoginPessoa(robot, loginFrame, EMAIL, SENHA);
        FrontendTestSupport.clicarProx(robot, loginFrame);
        robot.waitForIdle();

        // 4. Garantir que a tela de login foi fechada
        assertFalse(loginFrame.isShowing(), "A tela de login deve ser fechada após autenticação com sucesso");

        // 5. Instanciar a Home com o usuário autenticado (ação simulando o comportamento pós-login do MedAlerta.main)
        PessoaFisica autenticado = PessoaFisica.resgatarUsuarioArquivo(EMAIL, SENHA, false, false);
        assertNotNull(autenticado, "Usuário autenticado deve existir no arquivo de registros");

        Home homeFrame = GuiActionRunner.execute(() -> {
            Home frame = new Home();
            frame.receber(autenticado);
            return frame;
        });
        telaHome = new FrameFixture(robot, homeFrame);
        telaHome.show();

        // --- EXECUÇÃO DO FLUXO DO TESTE MANUAL TM-03 ---
        // 6. Clicar no botão "Contatos dos Médicos"
        JButton btnContatosMedicos = robot.finder().find(homeFrame, withText("Contatos dos Médicos"));
        FrontendTestSupport.clicarBotao(robot, btnContatosMedicos);

        // 7. Obter a janela de gerenciamento de contatos médicos
        telaContatos = WindowFinder.findFrame(ContatosMedicos.class).withTimeout(5_000).using(robot);
        assertNotNull(telaContatos, "Tela de Contatos Médicos deve ser exibida");

        // 8. Clicar no botão "Novo contato"
        JButton btnNovoContato = robot.finder().find(telaContatos.target(), withText("Novo contato"));
        FrontendTestSupport.clicarBotao(robot, btnNovoContato);

        // 9. Localizar e ordenar os campos de texto na tela de contatos
        List<JTextField> camposTexto = new ArrayList<>(robot.finder().findAll(telaContatos.target(), new GenericTypeMatcher<JTextField>(JTextField.class) {
            @Override
            protected boolean isMatching(JTextField component) {
                return true;
            }
        }));
        camposTexto.sort(Comparator
                .comparingInt((JTextField tf) -> tf.getBounds().y)
                .thenComparingInt(tf -> tf.getBounds().x));

        assertEquals(3, camposTexto.size(), "Devem existir exatamente 3 campos de texto na tela");

        JTextField nomeMedicoField = camposTexto.get(0);
        JTextField especialidadeField = camposTexto.get(1);
        JTextField numeroMedicoField = camposTexto.get(2);

        // 10. Inserir dados do médico
        FrontendTestSupport.digitarTexto(robot, new JTextComponentFixture(robot, nomeMedicoField), "Dr. Carlos");
        FrontendTestSupport.digitarTexto(robot, new JTextComponentFixture(robot, especialidadeField), "Cardiologista");
        FrontendTestSupport.digitarTexto(robot, new JTextComponentFixture(robot, numeroMedicoField), "11999999999");

        // 11. Clicar no botão "Salvar"
        JButton btnSalvar = robot.finder().find(telaContatos.target(), withText("Salvar"));
        FrontendTestSupport.clicarBotao(robot, btnSalvar);

        // --- RESULTADO ESPERADO: CONTATO SALVO E VISÍVEL ---
        // 12. Obter a nova tela de contatos reaberta após salvamento
        novaTelaContatos = WindowFinder.findFrame(ContatosMedicos.class).withTimeout(5_000).using(robot);
        assertNotNull(novaTelaContatos, "Uma nova janela de Contatos Médicos deve ser reaberta");

        // 13. Verificar se o contato é exibido na tabela da interface gráfica
        JTable tabela = robot.finder().find(novaTelaContatos.target(), new GenericTypeMatcher<JTable>(JTable.class) {
            @Override
            protected boolean isMatching(JTable component) {
                return true;
            }
        });
        JTableFixture tabelaFixture = new JTableFixture(robot, tabela);

        tabelaFixture.requireRowCount(1);
        assertEquals("Dr. Carlos", tabelaFixture.valueAt(TableCell.row(0).column(0)));
        assertEquals("11999999999", tabelaFixture.valueAt(TableCell.row(0).column(1)));
        assertEquals("Cardiologista", tabelaFixture.valueAt(TableCell.row(0).column(2)));

        // 14. Verificar a persistência no arquivo de dados
        PessoaFisica usuarioPersistido = PessoaFisica.resgatarUsuarioArquivo(EMAIL, SENHA, false, false);
        assertNotNull(usuarioPersistido, "O usuário persistido deve ser recuperado com sucesso");
        assertNotNull(usuarioPersistido.getContatosMedicos(), "A lista de contatos médicos não deve ser nula");
        assertEquals(1, usuarioPersistido.getContatosMedicos().getContatos().size());

        Pessoa medicoPersistido = usuarioPersistido.getContatosMedicos().getContatos().get(0);
        assertEquals("Dr. Carlos", medicoPersistido.getNome());
        assertEquals("11999999999", medicoPersistido.getTelefone());
        assertEquals("Cardiologista", medicoPersistido.getParticularidade());
    }
}

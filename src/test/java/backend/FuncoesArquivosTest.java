package backend;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;

import org.junit.jupiter.api.Test;



public class FuncoesArquivosTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @org.junit.jupiter.api.BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @org.junit.jupiter.api.AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    void deveGerarStringDeNulls() {
        String resultado = FuncoesArquivos.obterStringDeNullsCsv(3);

        assertEquals("null,null,null", resultado);
    }

    @Test
    void deveGerarStringDeNullsVazia() {
        String resultado = FuncoesArquivos.obterStringDeNullsCsv(0);

        assertEquals("", resultado);
    }

    @Test
    void deveObterListaLinhasArquivoExistente() throws Exception {
        Path arquivo = Files.createTempFile("teste", ".txt");

        Files.write(
                arquivo,
                List.of(
                        "linha1",
                        "linha2",
                        "linha3"));

        List<String> linhas =
                FuncoesArquivos.obterListaLinhas(arquivo.toString());

        assertEquals(3, linhas.size());
        assertEquals("linha1", linhas.get(0));
        assertEquals("linha2", linhas.get(1));
        assertEquals("linha3", linhas.get(2));
    }

    @Test
    void deveRetornarListaVaziaQuandoArquivoNaoExiste() {

        List<String> linhas =
                FuncoesArquivos.obterListaLinhas(
                        "arquivo_inexistente_123456.txt");

        assertTrue(linhas.isEmpty());
    }

    @Test
    void deveSalvarListaEmArquivo() throws Exception {

        Path arquivo = Files.createTempFile("lista", ".txt");

        List<String> dados =
                List.of(
                        "Joao,111",
                        "Maria,222");

        FuncoesArquivos.salvarListaEmArquivo(
                arquivo.toString(),
                dados,
                false);

        List<String> resultado =
                Files.readAllLines(arquivo);

        assertEquals(dados, resultado);
    }

    @Test
    void deveAdicionarLinhasComAppend() throws Exception {

        Path arquivo = Files.createTempFile("append", ".txt");

        FuncoesArquivos.salvarListaEmArquivo(
                arquivo.toString(),
                List.of("Joao,111"),
                false);

        FuncoesArquivos.salvarListaEmArquivo(
                arquivo.toString(),
                List.of("Maria,222"),
                true);

        List<String> linhas =
                Files.readAllLines(arquivo);

        assertEquals(2, linhas.size());
        assertEquals("Joao,111", linhas.get(0));
        assertEquals("Maria,222", linhas.get(1));
    }

    @Test
    void deveEncontrarNomeNoArquivo() throws Exception {

        Path arquivo = Files.createTempFile("nomes", ".txt");

        Files.write(
                arquivo,
                List.of(
                        "Joao,111",
                        "Maria,222"));

        boolean resultado =
                FuncoesArquivos.checarExistenciaNomeArquivo(
                        arquivo.toString(),
                        "Joao");

        assertTrue(resultado);
    }

    @Test
    void naoDeveEncontrarNomeNoArquivo() throws Exception {

        Path arquivo = Files.createTempFile("nomes", ".txt");

        Files.write(
                arquivo,
                List.of(
                        "Joao,111",
                        "Maria,222"));

        boolean resultado =
                FuncoesArquivos.checarExistenciaNomeArquivo(
                        arquivo.toString(),
                        "Pedro");

        assertFalse(resultado);
    }

    @Test
    void deveAlterarLinhaArquivo() throws Exception {

        Path arquivo = Files.createTempFile("alterar", ".txt");

        Files.write(
                arquivo,
                List.of(
                        "Joao,111",
                        "Maria,222"));

        FuncoesArquivos.alterarLinhaArquivo(
                arquivo.toString(),
                "Joao",
                "Joao,999");

        List<String> linhas =
                Files.readAllLines(arquivo);

        assertEquals("Joao,999", linhas.get(0));
        assertEquals("Maria,222", linhas.get(1));
    }

    @Test
    void naoDeveAlterarQuandoNomeNaoEncontrado() throws Exception {

        Path arquivo = Files.createTempFile("alterar", ".txt");

        List<String> original =
                List.of(
                        "Joao,111",
                        "Maria,222");

        Files.write(arquivo, original);

        FuncoesArquivos.alterarLinhaArquivo(
                arquivo.toString(),
                "Carlos",
                "Carlos,999");

        List<String> resultado =
                Files.readAllLines(arquivo);

        assertEquals(original, resultado);
    }

    @Test
    void deveAlterarInformacaoArquivo() throws Exception {

        Path arquivo = Files.createTempFile("info", ".txt");

        Files.write(
                arquivo,
                List.of(
                        "Joao,111",
                        "Maria,222"));

        FuncoesArquivos.alterarInfoArquivo(
                arquivo.toString(),
                "Joao",
                1,
                "999");

        List<String> linhas =
                Files.readAllLines(arquivo);

        assertEquals("Joao,999", linhas.get(0));
        assertEquals("Maria,222", linhas.get(1));
    }

    @Test
    void deveSalvarObjetoParaArquivo() throws Exception {

        Path arquivo = Files.createTempFile("objeto", ".txt");

        ArrayList<String> dados =
                new ArrayList<>();

        dados.add("Joao");
        dados.add("111");

        new FuncoesArquivos()
                .salvarObjetoParaArquivo(
                        dados,
                        arquivo.toString());

        List<String> linhas =
                Files.readAllLines(arquivo);

        assertEquals(1, linhas.size());
        assertEquals("Joao,111", linhas.get(0));
    }

    @Test
        void deveCriarArquivo() throws Exception {

        Path arquivo = Files.createTempFile("criar", ".txt");

        Files.delete(arquivo);

        FuncoesArquivos.criarArquivo(
                arquivo.toString());

        assertTrue(
                Files.exists(arquivo));
    }

    @Test
    void deveEscreverArquivo() throws Exception {

        Path arquivo = Files.createTempFile("escrever", ".txt");

        FuncoesArquivos.escreverArquivo(
                arquivo.toString(),
                "teste");

        String conteudo =
                Files.readString(arquivo);

        assertEquals(
                "teste",
                conteudo);
    }

    @Test
    void deveAdicionarLinhaArquivo() throws Exception {

        Path arquivo = Files.createTempFile("append", ".txt");

        Files.write(
                arquivo,
                List.of("linha1"));

        FuncoesArquivos.appendLinhaArquivo(
                arquivo.toString(),
                "linha2");

        List<String> linhas =
                Files.readAllLines(arquivo);

        assertEquals(2, linhas.size());
        assertEquals("linha1", linhas.get(0));
        assertEquals("linha2", linhas.get(1));
    }

    @Test
    void deveRetornarListaLinhas() throws Exception {

        Path arquivo = Files.createTempFile("lista", ".txt");

        Files.write(
                arquivo,
                List.of(
                        "a",
                        "b",
                        "c"));

        ArrayList<String> linhas =
                FuncoesArquivos.listaLinhas(
                        arquivo.toFile());

        assertEquals(3, linhas.size());
        assertEquals("a", linhas.get(0));
        assertEquals("b", linhas.get(1));
        assertEquals("c", linhas.get(2));
    }
    
    @Test
    void deveRetornarFalseQuandoArquivoNaoExiste() {

        boolean resultado =
                FuncoesArquivos.checarExistenciaNomeArquivo(
                        "arquivo_inexistente.txt",
                        "Joao");

        assertFalse(resultado);
    }

    @Test
    void deveAlterarUltimaLinhaArquivo() throws Exception {

        Path arquivo = Files.createTempFile("alterar", ".txt");

        Files.write(
                arquivo,
                List.of(
                        "Joao,111",
                        "Maria,222"));

        FuncoesArquivos.alterarLinhaArquivo(
                arquivo.toString(),
                "Maria",
                "Maria,999");

        List<String> linhas =
                Files.readAllLines(arquivo);

        assertEquals(
                "Maria,999",
                linhas.get(1));
    }

    @Test
    void deveGerarStringDeNullsComUmElemento() {

        String resultado =
                FuncoesArquivos.obterStringDeNullsCsv(1);

        assertEquals("null", resultado);
    }

    @Test
    void deveSalvarListaVaziaEmArquivo() throws Exception {

        Path arquivo =
                Files.createTempFile("vazio", ".txt");

        FuncoesArquivos.salvarListaEmArquivo(
                arquivo.toString(),
                List.of(),
                false);

        List<String> linhas =
                Files.readAllLines(arquivo);

        assertTrue(linhas.isEmpty());
    }

    @Test
    void deveEncontrarNomeNaUltimaLinha() throws Exception {

        Path arquivo =
                Files.createTempFile("nomes", ".txt");

        Files.write(
                arquivo,
                List.of(
                        "Joao,111",
                        "Maria,222"));

        assertTrue(
                FuncoesArquivos.checarExistenciaNomeArquivo(
                        arquivo.toString(),
                        "Maria"));
    }

    @Test
    void naoDeveCriarArquivoQuandoJaExiste() throws Exception {

        Path arquivo =
                Files.createTempFile("criar", ".txt");

        FuncoesArquivos.criarArquivo(
                arquivo.toString());

        assertTrue(
                Files.exists(arquivo));
    }

    @Test
    void naoDeveAlterarOutrasLinhasAoAlterarInfo() throws Exception {

        Path arquivo = Files.createTempFile("info", ".txt");

        Files.write(
                arquivo,
                List.of(
                        "Joao,111",
                        "Maria,222",
                        "Pedro,333"));

        FuncoesArquivos.alterarInfoArquivo(
                arquivo.toString(),
                "Maria",
                1,
                "999");

        List<String> linhas =
                Files.readAllLines(arquivo);

        assertEquals("Joao,111", linhas.get(0));
        assertEquals("Maria,999", linhas.get(1));
        assertEquals("Pedro,333", linhas.get(2));
    }

    @Test
    void naoDeveAlterarInfoQuandoNomeNaoExiste() throws Exception {

        Path arquivo = Files.createTempFile("info", ".txt");

        List<String> original =
                List.of(
                        "Joao,111",
                        "Maria,222");

        Files.write(arquivo, original);

        FuncoesArquivos.alterarInfoArquivo(
                arquivo.toString(),
                "Carlos",
                1,
                "999");

        List<String> resultado =
                Files.readAllLines(arquivo);

        assertEquals(original, resultado);
    }   

    @Test
    void deveSalvarObjetoComTresCampos() throws Exception {

        Path arquivo =
                Files.createTempFile("objeto", ".txt");

        ArrayList<String> dados =
                new ArrayList<>();

        dados.add("Joao");
        dados.add("111");
        dados.add("Ativo");

        new FuncoesArquivos()
                .salvarObjetoParaArquivo(
                        dados,
                        arquivo.toString());

        List<String> linhas =
                Files.readAllLines(arquivo);

        assertEquals(
                "Joao,111,Ativo",
                linhas.get(0));
    }

    @Test
    void appendDevePreservarConteudoAnterior() throws Exception {

        Path arquivo =
                Files.createTempFile("append", ".txt");

        Files.writeString(
                arquivo,
                "linha1\n");

        FuncoesArquivos.appendLinhaArquivo(
                arquivo.toString(),
                "linha2");

        String conteudo =
                Files.readString(arquivo);

        assertTrue(
                conteudo.contains("linha1"));

        assertTrue(
                conteudo.contains("linha2"));
    }

    @Test
    void deveRetornarListaVaziaParaArquivoVazio() throws Exception {

        Path arquivo =
                Files.createTempFile("vazio", ".txt");

        List<String> linhas =
                FuncoesArquivos.obterListaLinhas(
                        arquivo.toString());

        assertTrue(
                linhas.isEmpty());
    }

    @Test
    void listaLinhasDeArquivoVazio() throws Exception {

        Path arquivo =
                Files.createTempFile("vazio", ".txt");

        ArrayList<String> linhas =
                FuncoesArquivos.listaLinhas(
                        arquivo.toFile());

        assertTrue(
                linhas.isEmpty());
    }

    @Test
    void criarArquivoQuandoJaExisteDeveManterArquivo() throws Exception {

        Path arquivo =
                Files.createTempFile("criar", ".txt");

        long tamanhoAntes =
                Files.size(arquivo);

        FuncoesArquivos.criarArquivo(
                arquivo.toString());

        assertTrue(
                Files.exists(arquivo));

        assertEquals(
                tamanhoAntes,
                Files.size(arquivo));
    }

    // ==========================================
    // TESTES PARA O MÉTODO lerArquivo
    // ==========================================

    @Test
    void deveLerArquivoExistente() throws Exception {
        Path arquivo = Files.createTempFile("lerArquivo", ".txt");
        Files.writeString(arquivo, "teste leitura");
        
        // Redireciona o System.out para conseguirmos verificar se o print ocorreu corretamente
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        
        FuncoesArquivos.lerArquivo(arquivo.toString()); 
        
        // Verifica se a string foi "printada" no console
        assertTrue(outContent.toString().contains("teste leitura"));
        
        // Restaura o System.out padrão
        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
    }

    // ==========================================
    // TESTES PARA COBRIR OS BLOCOS "CATCH"
    // ==========================================

// ==========================================
    // TESTES PARA COBRIR OS BLOCOS "CATCH"
    // ==========================================

    @Test
    void deveTratarExcecaoEmCriarArquivo() {
        FuncoesArquivos.criarArquivo("");
        assertFalse(errContent.toString().isEmpty());
    }

    @Test
    void deveTratarExcecaoLerArquivo() {
        FuncoesArquivos.lerArquivo("caminho_inexistente_999.txt");
        assertTrue(outContent.toString().toLowerCase().contains("erro"));
        assertFalse(errContent.toString().isEmpty());
    }

    @Test
    void deveTratarExcecaoAoEscreverArquivo() throws Exception {
        Path dir = Files.createTempDirectory("dirInvalido");
        FuncoesArquivos.escreverArquivo(dir.toString(), "teste");
        assertTrue(outContent.toString().toLowerCase().contains("erro"));
        assertFalse(errContent.toString().isEmpty());
    }

    @Test
    void deveTratarExcecaoAoAppendLinhaArquivo() throws Exception {
        Path dir = Files.createTempDirectory("dirInvalido");
        FuncoesArquivos.appendLinhaArquivo(dir.toString(), "teste");
        assertFalse(errContent.toString().isEmpty());
    }

    @Test
    void deveTratarExcecaoAoSalvarObjeto() throws Exception {
        Path dir = Files.createTempDirectory("dirInvalido");
        ArrayList<String> dados = new ArrayList<>();
        dados.add("Atributo1");
        
        new FuncoesArquivos().salvarObjetoParaArquivo(dados, dir.toString());
        assertTrue(outContent.toString().toLowerCase().contains("erro"));
        assertFalse(errContent.toString().isEmpty());
    }

    @Test
    void deveTratarExcecaoAoSalvarLista() throws Exception {
        Path dir = Files.createTempDirectory("dirInvalido");
        FuncoesArquivos.salvarListaEmArquivo(dir.toString(), List.of("Linha A"), true);
        assertTrue(outContent.toString().toLowerCase().contains("erro"));
        assertFalse(errContent.toString().isEmpty());
    }

    @Test
    void deveTratarExcecaoAoListaLinhasFile() {
        File file = new File("arquivo_inexistente_abc.txt");
        ArrayList<String> res = FuncoesArquivos.listaLinhas(file);
        assertTrue(res.isEmpty());
        assertTrue(outContent.toString().toLowerCase().contains("erro"));
        assertFalse(errContent.toString().isEmpty());
    }

    @Test
    void deveTratarExcecaoAoAlterarInfoArquivo() {
        FuncoesArquivos.alterarInfoArquivo("arquivo_inexistente_abc.txt", "ref", 1, "nova info");
        assertTrue(outContent.toString().toLowerCase().contains("erro"));
        assertFalse(errContent.toString().isEmpty());
    }

    @Test
    void deveTratarExcecaoAoAlterarLinhaArquivo() {
        FuncoesArquivos.alterarLinhaArquivo("arquivo_inexistente_abc.txt", "ref", "nova linha");
        assertTrue(outContent.toString().toLowerCase().contains("erro"));
        assertFalse(errContent.toString().isEmpty());
    }

    @Test
    void deveCriarArquivoEImprimirMensagem() throws Exception {
        Path arquivo = Files.createTempFile("criar", ".txt");
        Files.delete(arquivo); // garante que não existe

        FuncoesArquivos.criarArquivo(arquivo.toString());

        // Mata o mutante condicional da linha 13 e o print da linha 14
        assertTrue(outContent.toString().contains("arquivo criado:"));
    }

    @Test
    void naoDeveCriarArquivoQuandoJaExisteEImprimirMensagem() throws Exception {
        Path arquivo = Files.createTempFile("criar", ".txt");

        FuncoesArquivos.criarArquivo(arquivo.toString());

        // Mata o mutante da linha 17
        assertTrue(outContent.toString().contains("arquivo ja existe!"));
    }

    @Test
    void appendDeveAdicionarNovaLinha() throws Exception {
        Path arquivo = Files.createTempFile("append", ".txt");
        
        FuncoesArquivos.appendLinhaArquivo(arquivo.toString(), "linha1");

        // Lê o conteúdo exato do arquivo, incluindo os bytes de quebra de linha
        String conteudo = Files.readString(arquivo);
        
        // Verifica se terminou com a quebra de linha do sistema (mata o mutante da linha 43)
        assertTrue(conteudo.endsWith(System.lineSeparator()) || conteudo.endsWith("\n"));
    }

    public static void escreverArquivo(String nomeArquivo, String linha){
        // Declarando o FileWriter dentro do parênteses do try, 
        // o .close() é automático, mesmo se der erro no meio da escrita!
        try (FileWriter escritorArquivo = new FileWriter(nomeArquivo)) {
            escritorArquivo.write(linha);
        } catch (IOException e){
            System.out.println("Erro: ");
            e.printStackTrace();
        }
    }
}
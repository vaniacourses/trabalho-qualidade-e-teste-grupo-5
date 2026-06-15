package backend.integracao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import backend.FuncoesArquivos;

/**
 * Testes de integração: fluxo real de leitura/escrita em arquivo
 * sem mockar o sistema de arquivos.
 */
class FuncoesArquivosIntegracaoTest {

    @TempDir
    Path pastaTemporaria;

    private String caminhoArquivo;

    @BeforeEach
    void prepararArquivo() {
        caminhoArquivo = pastaTemporaria.resolve("dados.csv").toString();
        FuncoesArquivos.salvarListaEmArquivo(caminhoArquivo, List.of(
                "Maria,21999999999,maria@teste.com",
                "Joao,21888888888,joao@teste.com"), false);
    }

    @Test
    void deveLerTodasAsLinhasDoArquivo() {
        List<String> linhas = FuncoesArquivos.obterListaLinhas(caminhoArquivo);

        assertEquals(2, linhas.size());
        assertTrue(linhas.get(0).startsWith("Maria"));
    }

    @Test
    void deveAlterarLinhaEspecificaNoArquivo() {
        FuncoesArquivos.alterarLinhaArquivo(caminhoArquivo, "Maria",
                "Maria,21911111111,maria.nova@teste.com");

        List<String> linhas = FuncoesArquivos.obterListaLinhas(caminhoArquivo);

        assertEquals("Maria,21911111111,maria.nova@teste.com", linhas.get(0));
        assertTrue(linhas.get(1).startsWith("Joao"));
    }

    @Test
    void deveAlterarColunaEspecificaNoArquivo() {
        FuncoesArquivos.alterarInfoArquivo(caminhoArquivo, "Joao", 1, "21777777777");

        List<String> linhas = FuncoesArquivos.obterListaLinhas(caminhoArquivo);

        assertEquals("Joao,21777777777,joao@teste.com", linhas.get(1));
    }

    @Test
    void deveSalvarListaCompletaSubstituindoConteudo() {
        List<String> novaLista = Arrays.asList(
                "Ana,21666666666,ana@teste.com",
                "Pedro,21555555555,pedro@teste.com");

        FuncoesArquivos.salvarListaEmArquivo(caminhoArquivo, novaLista, false);

        List<String> linhas = FuncoesArquivos.obterListaLinhas(caminhoArquivo);

        assertEquals(2, linhas.size());
        assertEquals("Ana,21666666666,ana@teste.com", linhas.get(0));
    }

    @Test
    void deveVerificarExistenciaDeNomeNoArquivo() {
        assertTrue(FuncoesArquivos.checarExistenciaNomeArquivo(caminhoArquivo, "Maria"));
        assertFalse(FuncoesArquivos.checarExistenciaNomeArquivo(caminhoArquivo, "Inexistente"));
    }

    @Test
    void deveAdicionarLinhaAoFinalDoArquivo() {
        FuncoesArquivos.appendLinhaArquivo(caminhoArquivo, "Carla,21444444444,carla@teste.com");

        List<String> linhas = FuncoesArquivos.obterListaLinhas(caminhoArquivo);

        assertEquals(3, linhas.size());
        assertEquals("Carla,21444444444,carla@teste.com", linhas.get(2));
    }

    @Test
    void checarExistenciaNomeArquivo_arquivoInexistente_deveLancarIOException() {
        assertThrows(IOException.class,
                () -> FuncoesArquivos.checarExistenciaNomeArquivo("arquivo-inexistente-xyz.txt", "Maria"));
    }

    @Test
    void alterarLinhaArquivo_nomeInexistente_mantemArquivoOriginal() {
        FuncoesArquivos.alterarLinhaArquivo(caminhoArquivo, "Inexistente", "X,1,2");

        List<String> linhas = FuncoesArquivos.obterListaLinhas(caminhoArquivo);

        assertEquals(2, linhas.size());
        assertTrue(linhas.get(0).startsWith("Maria"));
    }

    @Test
    void obterListaLinhas_arquivoInexistente_deveLancarIOException() {
        assertThrows(IOException.class,
                () -> FuncoesArquivos.obterListaLinhas("caminho/que/nao/existe.csv"));
    }

    @Test
    void salvarListaEmArquivo_caminhoInvalido_deveLancarIOException() {
        assertThrows(IOException.class, () ->
                FuncoesArquivos.salvarListaEmArquivo("/dev/null/impossivel/dados.csv",
                        List.of("Maria,111,maria@teste.com"), false));
    }
}

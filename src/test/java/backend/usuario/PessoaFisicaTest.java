package backend.usuario;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.never;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import backend.Endereco;
import backend.FuncoesArquivos;
import backend.Medicamento;

class PessoaFisicaTest {

    @Test
    void getNomeArquivoUsos_deveMontarNomeComCpf() {
        PessoaFisica pessoa = novaPessoaBase();

        String nomeArquivo = pessoa.getNomeArquivoUsos();

        assertTrue(nomeArquivo.endsWith("Uso12345678900.txt"));
        assertTrue(nomeArquivo.contains("arquivosUsosUsuarios"));
    }

    @Test
    void getUsoListaUsoMedicamentos_deveRetornarNullQuandoListaNaoExiste() {
        PessoaFisica pessoa = novaPessoaBase();

        Uso uso = pessoa.getUsoListaUsoMedicamentos("Paracetamol");

        assertNull(uso);
    }

    @Test
    void getUsoListaUsoMedicamentos_deveRetornarUsoQuandoNomeExistir() {
        PessoaFisica pessoa = novaPessoaBase();
        Uso usoParacetamol = novoUso("Paracetamol");
        Uso usoDipirona = novoUso("Dipirona");
        pessoa.setListaUsoMedicamentos(new ArrayList<>(Arrays.asList(usoParacetamol, usoDipirona)), false);

        Uso uso = pessoa.getUsoListaUsoMedicamentos("Dipirona");

        assertNotNull(uso);
        assertEquals("Dipirona", uso.getRemedio().getNome());
    }

    @Test
    void toString_deveConterNullParaCamposSemListaUso() {
        PessoaFisica pessoa = novaPessoaBase();

        String pessoaString = pessoa.toString();

        assertTrue(pessoaString.contains(",null,"));
        assertTrue(pessoaString.contains("12345678900"));
        assertTrue(pessoaString.contains("Rua Teste/10/Apto 1"));
    }

    @Test
    void toString_deveUsarSeparadorPortatilNoNomeDoArquivo() {
        PessoaFisica pessoa = novaPessoaBase();
        pessoa.setListaUsoMedicamentos(new ArrayList<>(List.of(novoUso("Paracetamol"))), false);

        String pessoaString = pessoa.toString();

        assertFalse(pessoaString.contains("backend\\usuario\\"));
    }

    @Test
    void toString_deveConterNomeArquivoUsosQuandoListaExistir() {
        PessoaFisica pessoa = novaPessoaBase();
        pessoa.setListaUsoMedicamentos(new ArrayList<>(List.of(novoUso("Paracetamol"))), false);

        String pessoaString = pessoa.toString();

        assertTrue(pessoaString.contains("Uso12345678900.txt"));
        assertTrue(pessoaString.contains("arquivosUsosUsuarios"));
    }

    @Test
    void setParticularidade_deveAtualizarEndereco() {
        PessoaFisica pessoa = novaPessoaBase();
        Endereco novoEndereco = new Endereco("Rua Nova", "200", "Casa");

        pessoa.setParticularidade(novoEndereco);

        assertEquals("Rua Nova", pessoa.getEndereco().getNomeDaRua());
        assertEquals("200", pessoa.getEndereco().getNumero());
    }

    @Test
    void setCpf_deveAtualizarValorCorretamente() {
        PessoaFisica pessoa = novaPessoaBase();

        pessoa.setCpf("99999999999");

        assertEquals("99999999999", pessoa.getCpf());
    }

    @Test
    void setEndereco_deveAtualizarEnderecoCorretamente() {
        PessoaFisica pessoa = novaPessoaBase();
        Endereco novoEndereco = new Endereco("Rua Nova", "99", "Casa 2");

        pessoa.setEndereco(novoEndereco);

        assertEquals("Rua Nova", pessoa.getEndereco().getNomeDaRua());
        assertEquals("99", pessoa.getEndereco().getNumero());
        assertEquals("Casa 2", pessoa.getEndereco().getComplemento());
    }

    @Test
    void setListaUsoMedicamentos_semPersistencia_deveAtualizarLista() {
        PessoaFisica pessoa = novaPessoaBase();
        List<Uso> usos = new ArrayList<>(List.of(novoUso("Dipirona")));

        pessoa.setListaUsoMedicamentos(usos, false);

        assertNotNull(pessoa.getListaUsoMedicamentos());
        assertEquals(1, pessoa.getListaUsoMedicamentos().size());
        assertEquals("Dipirona", pessoa.getListaUsoMedicamentos().get(0).getRemedio().getNome());
    }

    @Test
    void resgatarListaUsoMedicamentosArquivo_deveRetornarListaVaziaQuandoArquivoNaoExiste() {
        List<Uso> listaUsos = PessoaFisica.resgatarListaUsoMedicamentosArquivo("arquivo-que-nao-existe-123.txt");

        assertTrue(listaUsos.isEmpty());
    }

    @Test
    void adicionarUsoNaListaUsoMedicamentos_deveCriarListaQuandoNula() {
        PessoaFisica pessoa = novaPessoaBase();
        Uso novoUso = novoUso("Vitamina D");

        try (MockedStatic<FuncoesArquivos> mockArquivos = mockStatic(FuncoesArquivos.class)) {
            pessoa.adicionarUsoNaListaUsoMedicamentos(novoUso);

            assertNotNull(pessoa.getListaUsoMedicamentos());
            assertEquals(1, pessoa.getListaUsoMedicamentos().size());
            mockArquivos.verify(() -> FuncoesArquivos.salvarListaEmArquivo(anyString(), anyList(), eq(false)));
        }
    }

    @Test
    void removerUsoNaListaUsoMedicamentos_deveRemoverMedicamentoCorreto() {
        PessoaFisica pessoa = novaPessoaBase();
        pessoa.setListaUsoMedicamentos(
                new ArrayList<>(Arrays.asList(novoUso("Dipirona"), novoUso("Paracetamol"))),
                false);

        try (MockedStatic<FuncoesArquivos> mockArquivos = mockStatic(FuncoesArquivos.class)) {
            pessoa.removerUsoNaListaUsoMedicamentos("Dipirona");

            assertEquals(1, pessoa.getListaUsoMedicamentos().size());
            assertEquals("Paracetamol", pessoa.getListaUsoMedicamentos().get(0).getRemedio().getNome());
            mockArquivos.verify(() -> FuncoesArquivos.salvarListaEmArquivo(anyString(), anyList(), eq(false)));
        }
    }

    @Test
    void removerUsoNaListaUsoMedicamentos_naoDeveFalharQuandoListaNula() {
        PessoaFisica pessoa = novaPessoaBase();

        try (MockedStatic<FuncoesArquivos> mockArquivos = mockStatic(FuncoesArquivos.class)) {
            pessoa.removerUsoNaListaUsoMedicamentos("Qualquer");

            assertNull(pessoa.getListaUsoMedicamentos());
            mockArquivos.verify(() -> FuncoesArquivos.salvarListaEmArquivo(anyString(), anyList(), anyBoolean()), never());
        }
    }

    @Test
    void atualizarQntRemediosListaUsoMedicamentos_deveAtualizarQuantidade() {
        PessoaFisica pessoa = novaPessoaBase();
        pessoa.setListaUsoMedicamentos(new ArrayList<>(List.of(novoUso("Dipirona"))), false);

        try (MockedStatic<FuncoesArquivos> mockArquivos = mockStatic(FuncoesArquivos.class)) {
            pessoa.atualizarQntRemediosListaUsoMedicamentos("Dipirona", 5);

            assertEquals(5, pessoa.getUsoListaUsoMedicamentos("Dipirona").getQtdDisponivel());
            mockArquivos.verify(() -> FuncoesArquivos.salvarListaEmArquivo(anyString(), anyList(), eq(false)));
        }
    }

    @Test
    void atualizarQntRemediosListaUsoMedicamentos_comListaNula_deveLancarExcecao() {
        PessoaFisica pessoa = novaPessoaBase();

        assertThrows(IllegalStateException.class,
                () -> pessoa.atualizarQntRemediosListaUsoMedicamentos("Dipirona", 10));
    }

    @Test
    void salvarDadosArquivo_deveInserirQuandoUsuarioNaoExiste() {
        PessoaFisica pessoa = novaPessoaBase();

        try (MockedStatic<FuncoesArquivos> mockArquivos = mockStatic(FuncoesArquivos.class)) {
            mockArquivos.when(() -> FuncoesArquivos.checarExistenciaNomeArquivo(anyString(), eq("Fulano")))
                    .thenReturn(false);

            pessoa.salvarDadosArquivo();

            mockArquivos.verify(() -> FuncoesArquivos.appendLinhaArquivo(anyString(), anyString()));
            mockArquivos.verify(() -> FuncoesArquivos.alterarLinhaArquivo(anyString(), anyString(), anyString()), never());
        }
    }

    @Test
    void salvarDadosArquivo_deveAtualizarQuandoUsuarioJaExiste() {
        PessoaFisica pessoa = novaPessoaBase();

        try (MockedStatic<FuncoesArquivos> mockArquivos = mockStatic(FuncoesArquivos.class)) {
            mockArquivos.when(() -> FuncoesArquivos.checarExistenciaNomeArquivo(anyString(), eq("Fulano")))
                    .thenReturn(true);

            pessoa.salvarDadosArquivo();

            mockArquivos.verify(() -> FuncoesArquivos.alterarLinhaArquivo(anyString(), eq("Fulano"), anyString()));
            mockArquivos.verify(() -> FuncoesArquivos.appendLinhaArquivo(anyString(), anyString()), never());
        }
    }

    @Test
    void salvarArquivoUsos_listaNula_naoDeveLancarExcecao() {
        PessoaFisica pessoa = novaPessoaBase();
        assertDoesNotThrow(() -> pessoa.salvarArquivoUsos());
    }

    @Test
    void toString_enderecoNulo_naoDeveLancarExcecao() {
        PessoaFisica pessoa = novaPessoaBase();
        pessoa.setEndereco(null);
        assertDoesNotThrow(() -> pessoa.toString());
    }

    @Test
    void removerUsoNaLista_mesmoNomeRemoveUsoCorreto() {
        PessoaFisica pessoa = novaPessoaBase();
        Uso usoManha = novoUso("Dipirona");
        usoManha.setDose(1);
        Uso usoNoite = novoUso("Dipirona");
        usoNoite.setDose(2);
        pessoa.setListaUsoMedicamentos(new ArrayList<>(Arrays.asList(usoManha, usoNoite)), false);

        pessoa.removerUsoNaListaUsoMedicamentos("Dipirona");

        assertEquals(1, pessoa.getListaUsoMedicamentos().size());
        assertEquals(1, pessoa.getListaUsoMedicamentos().get(0).getDose());
    }

    @Test
    void getNomeArquivoUsos_deveUsarSeparadorDoSistema() {
        assertFalse(novaPessoaBase().getNomeArquivoUsos().contains("\\"));
    }

    private PessoaFisica novaPessoaBase() {
        Endereco endereco = new Endereco("Rua Teste", "10", "Apto 1");
        return new PessoaFisica("Fulano", "21999999999", "fulano@teste.com", "12345678900", "senha123", endereco);
    }

    private Uso novoUso(String nomeRemedio) {
        return new Uso(
                new Medicamento(nomeRemedio),
                1,
                new ArrayList<>(Arrays.asList("08:00", "20:00")),
                7,
                14,
                8,
                12);
    }
}

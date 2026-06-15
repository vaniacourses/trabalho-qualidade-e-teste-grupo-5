package backend.farmacia;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import org.junit.jupiter.api.Test;

import backend.Endereco;
import backend.Medicamento;

class PessoaJuridicaTest {

    @Test
    void getNomeArquivoEstoque_deveUsarSeparadorDoSistema() {
        PessoaJuridica farmacia = novaFarmacia();

        assertFalse(farmacia.getNomeArquivoEstoque().contains("\\"));
        assertTrue(farmacia.getNomeArquivoEstoque().contains(File.separator));
    }

    @Test
    void salvarEstoqueArquivo_estoqueNulo_naoDeveLancarExcecao() {
        PessoaJuridica farmacia = novaFarmacia();

        assertDoesNotThrow(farmacia::salvarEstoqueArquivo);
    }

    @Test
    void atualizarQntMedicamentoEstoque_medicamentoAusente_deveAdicionarAoEstoque() {
        PessoaJuridica farmacia = novaFarmacia();
        farmacia.setEstoque(new Estoque(), false);

        farmacia.atualizarQntMedicamentoEstoque(new Medicamento("Novalgina"), 15);

        assertEquals(1, farmacia.getEstoque().listaEstoque.size());
        assertEquals(15, farmacia.getEstoque().listaEstoque.get(0).getQntMedicamento());
    }

    private PessoaJuridica novaFarmacia() {
        Endereco endereco = new Endereco("Rua Farmácia", "50");
        return new PessoaJuridica("Farmácia Boa", "2133334444", "farmacia@teste.com",
                "senha123", "12345678000199", endereco);
    }
}

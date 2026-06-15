package backend.farmacia;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import backend.Medicamento;

class EstoqueTest {

    private Estoque estoque;
    private Medicamento dipirona;

    @BeforeEach
    void setUp() {
        estoque = new Estoque();
        dipirona = new Medicamento("Dipirona");
    }

    @Test
    void addMedicamentoEstoque_mesmoMedicamentoDuasVezes_naoDeveDuplicar() {
        estoque.addMedicamentoEstoque(dipirona, 10);
        estoque.addMedicamentoEstoque(dipirona, 5);

        assertEquals(1, estoque.listaEstoque.size());
        assertEquals(15, estoque.listaEstoque.get(0).getQntMedicamento());
    }

    @Test
    void atualizarQntMedicamento_medicamentoInexistente_deveLancarExcecao() {
        estoque.addMedicamentoEstoque(dipirona, 10);

        assertThrows(IllegalArgumentException.class,
                () -> estoque.atualizarQntMedicamento(new Medicamento("Paracetamol"), 99));
    }
}

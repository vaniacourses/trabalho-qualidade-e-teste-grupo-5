package backend.farmacia;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import backend.Medicamento;

class ItemEstoqueTest {

    @Test
    void setQntMedicamento_negativa_deveLancarExcecao() {
        ItemEstoque item = new ItemEstoque(new Medicamento("Aspirina"), 10);

        assertThrows(IllegalArgumentException.class, () -> item.setQntMedicamento(-5));
    }

    @Test
    void stringToItemEstoque_nomeComVirgula_devePreservarRoundTrip() {
        Medicamento med = new Medicamento("Aspirina, 500mg", 10f, "Analgésico",
                "Comprimido", "Tomar após refeições", false);
        ItemEstoque original = new ItemEstoque(med, 20);

        ItemEstoque recuperado = ItemEstoque.stringToItemEstoque(original.toString());

        assertEquals("Aspirina, 500mg", recuperado.getMedicamento().getNome());
        assertEquals(20, recuperado.getQntMedicamento());
    }
}

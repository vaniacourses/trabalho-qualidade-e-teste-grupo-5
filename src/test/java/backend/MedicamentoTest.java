package backend;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MedicamentoTest {

    private Medicamento medicamento1;
    private Medicamento medicamento2;
    private Medicamento medicamento3;

    @BeforeEach
    void inicializa() {
        medicamento1 = new Medicamento("Paracetamol", 15.50f, "Analgésico e antitérmico", "Comprimido", "Tomar de 6 em 6 horas", false);
        medicamento2 = new Medicamento("Amoxicilina", "Cápsula", "Tomar com água", true);
        medicamento3 = new Medicamento("Nimesulida");
    }

    @Test
    public void testConstrutorCompleto() {
        assertEquals("Paracetamol", medicamento1.getNome());
        assertEquals(15.50f, medicamento1.getPreco());
        assertEquals("Analgésico e antitérmico", medicamento1.getEspecificacoes());
        assertEquals("Comprimido", medicamento1.getTipoDoRemedio());
        assertEquals("Tomar de 6 em 6 horas", medicamento1.getCondicoesDeUso());
        assertFalse(medicamento1.isRestricao());
    }

    @Test
    public void testConstrutorParcial() {
        assertEquals("Amoxicilina", medicamento2.getNome());
        assertEquals("Cápsula", medicamento2.getTipoDoRemedio());
        assertEquals("Tomar com água", medicamento2.getCondicoesDeUso());
        assertTrue(medicamento2.isRestricao());
    }

    @Test
    public void testConstrutorApenasNome() {
        assertEquals("Nimesulida", medicamento3.getNome());
        assertEquals(0f, medicamento3.getPreco());
        assertNull(medicamento3.getEspecificacoes());
        assertNull(medicamento3.getTipoDoRemedio());
        assertNull(medicamento3.getCondicoesDeUso());
        assertFalse(medicamento3.isRestricao());
    }

    @Test
    public void testConstrutorNomePrecoEspecificacoes() {
        Medicamento meds = new Medicamento("Aspirina", 5.00f, "Anti-inflamatório");

        assertEquals("Aspirina", meds.getNome());
        assertEquals(5.00f, meds.getPreco());
        assertEquals("Anti-inflamatório", meds.getEspecificacoes());
    }

    @Test
    public void testConstrutorNomeTipoCondicoes() {
        Medicamento meds = new Medicamento("Xarope", "Xarope", "Tomar 2 vezes ao dia");

        assertEquals("Xarope", meds.getNome());
        assertEquals("Xarope", meds.getTipoDoRemedio());
        assertEquals("Tomar 2 vezes ao dia", meds.getCondicoesDeUso());
    }

    @Test
    public void testConstrutorNomeTipoCondicoesRestricao() {
        Medicamento meds = new Medicamento("Omeprazol", "Cápsula", "Tomar em jejum", true);

        assertEquals("Omeprazol", meds.getNome());
        assertEquals("Cápsula", meds.getTipoDoRemedio());
        assertEquals("Tomar em jejum", meds.getCondicoesDeUso());
        assertTrue(meds.isRestricao());
    }

    @Test
    public void testConstrutorNomePrecoTipoCondicoesRestricao() {
        Medicamento meds = new Medicamento("Dipirona", 8.90f, "Comprimido", "Tomar quando necessário", false);

        assertEquals("Dipirona", meds.getNome());
        assertEquals(8.90f, meds.getPreco());
        assertEquals("Comprimido", meds.getTipoDoRemedio());
        assertEquals("Tomar quando necessário", meds.getCondicoesDeUso());
        assertFalse(meds.isRestricao());
    }

    @Test
    public void testGetNome() {
        assertEquals("Paracetamol", medicamento1.getNome());
    }

    @Test
    public void testGetPreco() {
        assertEquals(15.50f, medicamento1.getPreco());
    }

    @Test
    public void testGetEspecificacoes() {
        assertEquals("Analgésico e antitérmico", medicamento1.getEspecificacoes());
    }

    @Test
    public void testGetTipoDoRemedio() {
        assertEquals("Comprimido", medicamento1.getTipoDoRemedio());
    }

    @Test
    public void testGetCondicoesDeUso() {
        assertEquals("Tomar de 6 em 6 horas", medicamento1.getCondicoesDeUso());
    }

    @Test
    public void testIsRestricaoFalse() {
        assertFalse(medicamento1.isRestricao());
    }

    @Test
    public void testIsRestricaoTrue() {
        assertTrue(medicamento2.isRestricao());
    }

    @Test
    public void testSetNome() {
        medicamento1.setNome("Ibuprofeno");
        assertEquals("Ibuprofeno", medicamento1.getNome());
    }

    @Test
    public void testSetNomeNull() {
        assertThrows(IllegalArgumentException.class, () -> medicamento1.setNome(null));
    }

    @Test
    public void testSetNomeVazio() {
        assertThrows(IllegalArgumentException.class, () -> medicamento1.setNome(""));
    }

    @Test
    public void testSetPreco() {
        medicamento1.setPreco(25.99f);
        assertEquals(25.99f, medicamento1.getPreco());
    }

    @Test
    public void testSetPrecoNegativo() {
        assertThrows(IllegalArgumentException.class, () -> medicamento1.setPreco(-10.00f));
    }

    @Test
    public void testSetEspecificacoes() {
        medicamento1.setEspecificacoes("Novo antibiótico");
        assertEquals("Novo antibiótico", medicamento1.getEspecificacoes());
    }

    @Test
    public void testSetTipoDoRemedio() {
        medicamento1.setTipoDoRemedio("Xarope");
        assertEquals("Xarope", medicamento1.getTipoDoRemedio());
    }

    @Test
    public void testSetCondicoesDeUso() {
        medicamento1.setCondicoesDeUso("Tomar após as refeições");
        assertEquals("Tomar após as refeições", medicamento1.getCondicoesDeUso());
    }

    @Test
    public void testSetRestricao() {
        medicamento1.setRestricao(true);
        assertTrue(medicamento1.isRestricao());

        medicamento1.setRestricao(false);
        assertFalse(medicamento1.isRestricao());
    }

    @Test
    public void testToString() {
        String resultado = medicamento1.toString();

        assertTrue(resultado.contains("Paracetamol"));
        assertTrue(resultado.contains("15.5"));
        assertTrue(resultado.contains("Comprimido"));
        assertTrue(resultado.contains("false"));
    }

    @Test
    public void testToStringComAtributosNull() {
        String resultado = medicamento3.toString();

        assertTrue(resultado.contains("Nimesulida"));
        assertTrue(resultado.contains("0.0"));
        assertTrue(resultado.contains("null"));
    }

    @Test
    public void testCompareToOrdenacao() {
        ArrayList<Medicamento> medicamentos = new ArrayList<>();
        medicamentos.add(medicamento1); // Paracetamol
        medicamentos.add(medicamento2); // Amoxicilina
        medicamentos.add(medicamento3); // Nimesulida

        java.util.Collections.sort(medicamentos);

        assertEquals("Amoxicilina", medicamentos.get(0).getNome());
        assertEquals("Nimesulida", medicamentos.get(1).getNome());
        assertEquals("Paracetamol", medicamentos.get(2).getNome());
    }

    @Test
    public void testCompareToMedicamentoIgual() {
        Medicamento paracetamol2 = new Medicamento("Paracetamol");

        assertEquals(0, medicamento1.compareTo(paracetamol2));
    }

    @Test
    public void testCompareToMedicamentoNull() {
        assertDoesNotThrow(() -> medicamento1.compareTo(null));
    }

    @Test
    public void testEqualsMesmaReferencia() {
        assertSame(medicamento1, medicamento1);
    }

    @Test
    public void testEqualsReferenciasDiferentes() {
        Medicamento paracetamol2 = new Medicamento("Paracetamol");

        assertNotSame(medicamento1, paracetamol2);
    }
}
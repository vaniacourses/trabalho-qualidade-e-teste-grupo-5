package backend;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class EnderecoTest {

    @Test
    void toString_deveSerializarEnderecoCompleto() {
        Endereco endereco = new Endereco("Rua Teste", "100", "Apto 2", "Centro", "Rio", "RJ", "Brasil", "20000-000");

        String serializado = endereco.toString();

        assertEquals("Rua Teste/100/Apto 2/Centro/Rio/RJ/Brasil/20000-000", serializado);
    }

    @Test
    void stringToEndereco_enderecoMinimo_deveParsearSemExcecao() {
        Endereco endereco = assertDoesNotThrow(() -> Endereco.stringToEndereco("Rua A/10"));

        assertEquals("Rua A", endereco.getNomeDaRua());
        assertEquals("10", endereco.getNumero());
    }

    @Test
    void stringToEndereco_campoNullLiteral_deveIgnorarCamposAusentes() {
        Endereco endereco = Endereco.stringToEndereco("Rua A/10/null/null/null/null/null/null");

        assertNull(endereco.getComplemento());
        assertNull(endereco.getNomeDoBairro());
    }

    @Test
    void stringToEndereco_ruaComBarra_devePreservarRoundTrip() {
        Endereco original = new Endereco("Rua A/B", "10");
        Endereco lido = Endereco.stringToEndereco(original.toString());

        assertEquals(original.getNomeDaRua(), lido.getNomeDaRua());
        assertEquals(original.getNumero(), lido.getNumero());
    }
}

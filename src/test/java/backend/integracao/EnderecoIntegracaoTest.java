package backend.integracao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import backend.Endereco;

class EnderecoIntegracaoTest {

    @Test
    void roundTrip_enderecoCompleto_devePreservarCamposNulos() {
        Endereco original = new Endereco("Rua B", "20");
        Endereco recuperado = Endereco.stringToEndereco(original.toString());

        assertNull(recuperado.getComplemento());
    }
}

package backend;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import backend.usuario.PessoaFisica;

class PessoaTest {

    @Test
    void compareTo_nomeNulo_naoDeveLancarExcecao() {
        PessoaFisica pessoaA = novaPessoa("Ana");
        PessoaFisica pessoaB = novaPessoa("Bruno");
        pessoaA.setNome(null);

        assertDoesNotThrow(() -> pessoaA.compareTo(pessoaB));
    }

    @Test
    void pessoaToString_nomeComVirgula_deveManterFormatoCsv() {
        PessoaFisica pessoa = novaPessoa("Silva, João");

        assertEquals(4, pessoa.PessoaToString().split(",", -1).length);
    }

    private PessoaFisica novaPessoa(String nome) {
        Endereco endereco = new Endereco("Rua A", "1");
        return new PessoaFisica(nome, "21999999999", "teste@email.com", "12345678900", "senha", endereco);
    }
}

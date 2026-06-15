package backend.integracao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import backend.Autenticacao;
import backend.Endereco;
import backend.FuncoesArquivos;
import backend.usuario.PessoaFisica;

class PessoaFisicaIntegracaoTest {

    @TempDir
    Path pastaTemporaria;

    @Test
    void doisUsuariosComMesmoNome_devemPermanecerNoArquivo() {
        String caminho = pastaTemporaria.resolve("usuarios.txt").toString();
        Endereco endereco = new Endereco("Rua A", "1");
        PessoaFisica usuario1 = new PessoaFisica("João", "111", "a@teste.com", "cpf1", "senha1", endereco);
        PessoaFisica usuario2 = new PessoaFisica("João", "222", "b@teste.com", "cpf2", "senha2", endereco);

        FuncoesArquivos.salvarListaEmArquivo(caminho, List.of(usuario1.toString()), false);
        FuncoesArquivos.alterarLinhaArquivo(caminho, "João", usuario2.toString());

        List<String> linhas = FuncoesArquivos.obterListaLinhas(caminho);

        assertEquals(2, linhas.size());
        assertTrue(linhas.stream().anyMatch(l -> l.contains("a@teste.com")));
        assertTrue(linhas.stream().anyMatch(l -> l.contains("b@teste.com")));
    }

    @Test
    void loginComSenhaCriptografadaNoArquivo_deveUsarAutenticacao() throws Exception {
        String email = "user@teste.com";
        String senhaPlana = "senha123";
        String senhaHash = Autenticacao.encriptarSenha(email, senhaPlana);

        assertTrue(Autenticacao.autenticar(email, senhaPlana, senhaHash));
        assertTrue(loginComoResgatarUsuarioArquivo(senhaHash, senhaPlana));
    }

    private boolean loginComoResgatarUsuarioArquivo(String senhaArquivo, String senhaFornecida) {
        return senhaArquivo.equals(senhaFornecida);
    }
}

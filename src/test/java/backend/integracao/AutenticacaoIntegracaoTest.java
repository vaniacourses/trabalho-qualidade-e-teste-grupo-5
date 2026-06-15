package backend.integracao;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import backend.Autenticacao;
import backend.FuncoesArquivos;

/**
 * Integração entre Autenticacao e persistência em arquivo:
 * simula o fluxo de login com senha criptografada salva em CSV.
 */
class AutenticacaoIntegracaoTest {

    @TempDir
    Path pastaTemporaria;

    @Test
    void deveAutenticarUsuarioComSenhaSalvaNoArquivo() throws Exception {
        String email = "usuario@teste.com";
        String senha = "minhasenha123";
        String senhaCriptografada = Autenticacao.encriptarSenha(email, senha);

        String caminho = pastaTemporaria.resolve("usuarios.csv").toString();
        String linhaUsuario = "Fulano,21999999999," + email + "," + senhaCriptografada + ",12345678900";
        FuncoesArquivos.escreverArquivo(caminho, linhaUsuario);

        String senhaDoArquivo = extrairSenhaDoArquivo(caminho, email);

        assertTrue(Autenticacao.autenticar(email, senha, senhaDoArquivo));
    }

    @Test
    void deveRejeitarSenhaIncorretaMesmoComEmailNoArquivo() throws Exception {
        String email = "usuario@teste.com";
        String senhaCorreta = "senhaCorreta";
        String senhaCriptografada = Autenticacao.encriptarSenha(email, senhaCorreta);

        String caminho = pastaTemporaria.resolve("usuarios.csv").toString();
        FuncoesArquivos.escreverArquivo(caminho,
                "Fulano,21999999999," + email + "," + senhaCriptografada + ",12345678900");

        String senhaDoArquivo = extrairSenhaDoArquivo(caminho, email);

        assertFalse(Autenticacao.autenticar(email, "senhaErrada", senhaDoArquivo));
    }

    @Test
    void hashGeradoDeveSerConsistenteEntreChamadas() throws Exception {
        String email = "teste@email.com";
        String senha = "abc123";

        String hash1 = Autenticacao.encriptarSenha(email, senha);
        String hash2 = Autenticacao.encriptarSenha(email, senha);

        assertTrue(Autenticacao.autenticar(email, senha, hash1));
        assertTrue(Autenticacao.autenticar(email, senha, hash2));
    }

    private String extrairSenhaDoArquivo(String caminho, String email) {
        List<String> linhas = FuncoesArquivos.obterListaLinhas(caminho);
        for (String linha : linhas) {
            String[] campos = linha.split(",");
            if (campos[2].equals(email)) {
                return campos[3];
            }
        }
        return "";
    }
}

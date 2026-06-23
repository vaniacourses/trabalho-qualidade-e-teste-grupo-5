package backend;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import backend.usuario.PessoaFisica;

class EstresseMultiUsuarioTest {

    private static final int NUMERO_DE_USUARIOS = 50;

    private static final int THREADS_CONCORRENTES = 10;

    private static final long TEMPO_MAXIMO_TOTAL_MS = 10_000;

    private static Path arquivoUsuarios;
    private static List<String[]> dadosUsuarios;

    @BeforeAll
    static void prepararCenarioDeEstresse() throws Exception {
        arquivoUsuarios = Path.of(PessoaFisica.nomeArquivoUsuarios);
        if (arquivoUsuarios.getParent() != null) {
            Files.createDirectories(arquivoUsuarios.getParent());
        }

        dadosUsuarios = new ArrayList<>();
        StringBuilder conteudo = new StringBuilder(
                "nome,telefone,email,senha,cpf,endereco,"
                        + "nomeArquivoUsosMedicamentos,agendaContatosMedicos,agendaContatosFarmacias\n");

        for (int i = 0; i < NUMERO_DE_USUARIOS; i++) {
            String email = "estresse.usuario" + i + "@test.com";
            String senha = "senhaEstresse" + i;
            String senhaHash = Autenticacao.encriptarSenha(email, senha);
            dadosUsuarios.add(new String[]{email, senha, senhaHash});

            conteudo.append("Usuario Estresse ").append(i)
                    .append(",1199999").append(String.format("%04d", i))
                    .append(",").append(email)
                    .append(",").append(senhaHash)
                    .append(",").append(String.format("1112223%04d", i))
                    .append(",Rua Estresse/").append(i)
                    .append("/null/null/null/null/null/null,null,null,null\n");
        }

        Files.writeString(arquivoUsuarios, conteudo.toString());
    }

    @AfterAll
    static void limparArquivoDeTeste() throws IOException {
        Files.deleteIfExists(arquivoUsuarios);
    }

    @Test
    void autenticacoesConcorrentes_devemCompletarTodasComSucesso() throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(THREADS_CONCORRENTES);
        CountDownLatch portaoDeLargada = new CountDownLatch(1);
        CountDownLatch finalizados = new CountDownLatch(NUMERO_DE_USUARIOS);
        AtomicInteger sucessos = new AtomicInteger(0);
        AtomicInteger erros = new AtomicInteger(0);

        for (int i = 0; i < NUMERO_DE_USUARIOS; i++) {
            final String[] dados = dadosUsuarios.get(i);
            executor.submit(() -> {
                try {
                    portaoDeLargada.await();
                    boolean autenticado = Autenticacao.autenticar(dados[0], dados[1], dados[2]);
                    if (autenticado) {
                        sucessos.incrementAndGet();
                    } else {
                        erros.incrementAndGet();
                    }
                } catch (Exception e) {
                    erros.incrementAndGet();
                } finally {
                    finalizados.countDown();
                }
            });
        }

        long inicio = System.nanoTime();
        portaoDeLargada.countDown();

        boolean completou = finalizados.await(TEMPO_MAXIMO_TOTAL_MS, TimeUnit.MILLISECONDS);
        long decorridoMs = (System.nanoTime() - inicio) / 1_000_000;
        executor.shutdown();

        assertTrue(completou,
                () -> "Nem todos os " + NUMERO_DE_USUARIOS
                        + " usuários completaram em " + TEMPO_MAXIMO_TOTAL_MS + " ms");
        assertEquals(0, erros.get(),
                () -> "Ocorreram " + erros.get() + " erros durante autenticações concorrentes");
        assertEquals(NUMERO_DE_USUARIOS, sucessos.get(),
                () -> "Esperadas " + NUMERO_DE_USUARIOS
                        + " autenticações bem-sucedidas; obtidas: " + sucessos.get());
        assertTrue(decorridoMs <= TEMPO_MAXIMO_TOTAL_MS,
                () -> "Estresse excedeu o limite: " + decorridoMs
                        + " ms (limite: " + TEMPO_MAXIMO_TOTAL_MS + " ms)");
    }

    @Test
    void leituraSimultaneaDeArquivo_deveManterIntegridade() throws InterruptedException {
        CountDownLatch portaoDeLargada = new CountDownLatch(1);
        CountDownLatch finalizados = new CountDownLatch(NUMERO_DE_USUARIOS);
        AtomicInteger leiturasBemSucedidas = new AtomicInteger(0);
        AtomicInteger errosLeitura = new AtomicInteger(0);

        for (int i = 0; i < NUMERO_DE_USUARIOS; i++) {
            Thread t = new Thread(() -> {
                try {
                    portaoDeLargada.await();
                    List<String> linhas = Files.readAllLines(arquivoUsuarios);
                    if (linhas.size() > 1) {
                        leiturasBemSucedidas.incrementAndGet();
                    } else {
                        errosLeitura.incrementAndGet();
                    }
                } catch (Exception e) {
                    errosLeitura.incrementAndGet();
                } finally {
                    finalizados.countDown();
                }
            });
            t.setDaemon(true);
            t.start();
        }

        portaoDeLargada.countDown();
        boolean completou = finalizados.await(TEMPO_MAXIMO_TOTAL_MS, TimeUnit.MILLISECONDS);

        assertTrue(completou,
                "Leituras concorrentes não completaram dentro do limite de tempo");
        assertEquals(0, errosLeitura.get(),
                () -> "Ocorreram " + errosLeitura.get()
                        + " erros durante leituras simultâneas do arquivo de usuários");
        assertEquals(NUMERO_DE_USUARIOS, leiturasBemSucedidas.get(),
                () -> "Esperadas " + NUMERO_DE_USUARIOS
                        + " leituras válidas; obtidas: " + leiturasBemSucedidas.get());
    }
}

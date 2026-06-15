package backend.integracao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import backend.FuncoesArquivos;
import backend.Medicamento;
import backend.usuario.PessoaFisica;
import backend.usuario.Uso;

/**
 * Integração entre Uso, FuncoesArquivos e PessoaFisica:
 * persiste usos em arquivo e recarrega a lista.
 */
class UsoPersistenciaIntegracaoTest {

    @TempDir
    Path pastaTemporaria;

    @Test
    void devePersistirERecuperarListaDeUsos() {
        Uso usoSalvo = novoUso("Dipirona", 8, 12);
        String caminho = pastaTemporaria.resolve("usos.txt").toString();

        FuncoesArquivos.salvarListaEmArquivo(caminho, List.of(usoSalvo.toString()), false);

        List<Uso> usosRecuperados = PessoaFisica.resgatarListaUsoMedicamentosArquivo(caminho);

        assertEquals(1, usosRecuperados.size());
        Uso usoLido = usosRecuperados.get(0);
        assertEquals("Dipirona", usoLido.getRemedio().getNome());
        assertEquals(1, usoLido.getDose());
        assertEquals(7, usoLido.getDuracaoDoTratamento());
        assertEquals(30, usoLido.getQtdDisponivel());
        assertEquals(8, usoLido.getHorarioDeInicio());
        assertEquals(12, usoLido.getIntervalo());
    }

    @Test
    void deveManterVariosUsosAposRoundTrip() {
        Uso uso1 = novoUso("Paracetamol", 8, 8);
        Uso uso2 = novoUso("Ibuprofeno", 14, 6);
        String caminho = pastaTemporaria.resolve("multiplos-usos.txt").toString();

        FuncoesArquivos.salvarListaEmArquivo(
                caminho,
                List.of(uso1.toString(), uso2.toString()),
                false);

        List<Uso> usos = PessoaFisica.resgatarListaUsoMedicamentosArquivo(caminho);

        assertEquals(2, usos.size());
        assertNotNull(usos.get(0).getRemedio());
        assertEquals("Ibuprofeno", usos.get(1).getRemedio().getNome());
    }

    @Test
    void calcularHorariosDeUsoDeveFuncionarAposRecuperarDoArquivo() {
        Uso uso = novoUso("Losartana", 6, 12);
        String caminho = pastaTemporaria.resolve("horarios.txt").toString();

        FuncoesArquivos.salvarListaEmArquivo(caminho, List.of(uso.toString()), false);
        Uso usoRecuperado = PessoaFisica.resgatarListaUsoMedicamentosArquivo(caminho).get(0);

        usoRecuperado.calcularHorariosDeUso();

        assertEquals(2, usoRecuperado.getHorariosDeUso().size());
        assertEquals(6, usoRecuperado.getHorariosDeUso().get(0));
        assertEquals(18, usoRecuperado.getHorariosDeUso().get(1));
    }

    @Test
    void nomeMedicamentoComVirgula_devePreservarRoundTrip() {
        Uso uso = novoUso("Dipirona,500mg", 6, 12);
        String caminho = pastaTemporaria.resolve("nome-virgula.txt").toString();

        FuncoesArquivos.salvarListaEmArquivo(caminho, List.of(uso.toString()), false);

        Uso recuperado = PessoaFisica.resgatarListaUsoMedicamentosArquivo(caminho).get(0);
        assertEquals("Dipirona,500mg", recuperado.getRemedio().getNome());
    }

    private Uso novoUso(String nome, int horaInicio, int intervalo) {
        return new Uso(
                new Medicamento(nome),
                1,
                new ArrayList<>(Arrays.asList("seg", "qua", "sex")),
                7,
                30,
                horaInicio,
                intervalo);
    }
}

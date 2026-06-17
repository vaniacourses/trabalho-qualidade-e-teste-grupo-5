package backend.usuario;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import backend.Medicamento;

/**
 * Testes unitários da classe Uso (alta complexidade, não-CRUD).
 * Cobre ramificações para critério todas-arestas e mutação.
 */
class UsoTest {

    private Uso uso;

    @BeforeEach
    void setUp() {
        uso = new Uso(
                new Medicamento("Dipirona"),
                2,
                new ArrayList<>(Arrays.asList("seg", "ter")),
                10,
                20,
                8,
                12);
    }

    // --- calcularHorariosDeUso (estrutural) ---

    @Test
    void calcularHorariosDeUso_comIntervaloZero_deveAdicionarApenasHoraInicial() {
        Uso usoSemIntervalo = new Uso(
                new Medicamento("Vitamina C"),
                1,
                new ArrayList<>(Arrays.asList("dom")),
                5,
                10,
                9,
                0);

        usoSemIntervalo.calcularHorariosDeUso();

        assertEquals(1, usoSemIntervalo.getHorariosDeUso().size());
        assertEquals(9, usoSemIntervalo.getHorariosDeUso().get(0));
    }

    @Test
    void calcularHorariosDeUso_comIntervaloDoze_deveGerarDoisHorarios() {
        uso.calcularHorariosDeUso();

        assertEquals(2, uso.getHorariosDeUso().size());
        assertEquals(8, uso.getHorariosDeUso().get(0));
        assertEquals(20, uso.getHorariosDeUso().get(1));
    }

    @Test
    void calcularHorariosDeUso_comIntervaloSeis_deveGerarQuatroHorarios() {
        Uso usoSeisHoras = new Uso(
                new Medicamento("Omeprazol"),
                1,
                new ArrayList<>(Arrays.asList("seg")),
                7,
                28,
                6,
                6);

        usoSeisHoras.calcularHorariosDeUso();

        assertEquals(4, usoSeisHoras.getHorariosDeUso().size());
        assertEquals(0, usoSeisHoras.getHorariosDeUso().get(0));
        assertEquals(18, usoSeisHoras.getHorariosDeUso().get(3));
    }

    @Test
    void calcularHorariosDeUso_chamadoDuasVezes_naoDeveDuplicarHorarios() {
        uso.calcularHorariosDeUso();
        int tamanhoPrimeiraVez = uso.getHorariosDeUso().size();

        uso.calcularHorariosDeUso();

        assertEquals(tamanhoPrimeiraVez, uso.getHorariosDeUso().size());
    }

    @Test
    void calcularHorariosDeUso_naoDeveGerarHoraVinteQuatro() {
        Uso usoSeisHoras = new Uso(
                new Medicamento("Losartana"),
                1,
                new ArrayList<>(Arrays.asList("seg")),
                7,
                28,
                6,
                6);

        usoSeisHoras.calcularHorariosDeUso();

        assertFalse(usoSeisHoras.getHorariosDeUso().contains(24));
    }

    @Test
    void stringToUso_linhaInvalida_deveLancarExcecaoDeValidacao() {
        assertThrows(IllegalArgumentException.class, () -> Uso.stringToUso("remedio,abc,seg,7,10,8,12"));
        assertThrows(IllegalArgumentException.class, () -> Uso.stringToUso(""));
        assertThrows(IllegalArgumentException.class, () -> Uso.stringToUso("remedio,1"));
    }

    // --- validações nos setters (baseado em defeitos) ---

    @Test
    void setDose_negativa_deveLancarExcecao() {
        assertThrows(IllegalArgumentException.class, () -> uso.setDose(-1));
    }

    @Test
    void setDose_zero_deveSerAceita() {
        uso.setDose(0);
        assertEquals(0, uso.getDose());
    }

    @Test
    void setDuracaoDoTratamento_negativa_deveLancarExcecao() {
        assertThrows(IllegalArgumentException.class, () -> uso.setDuracaoDoTratamento(-1));
    }

    @Test
    void setQtdDisponivel_negativa_deveLancarExcecao() {
        assertThrows(IllegalArgumentException.class, () -> uso.setQtdDisponivel(-5));
    }

    @Test
    void setTipoDoRemedio_vazio_deveLancarExcecao() {
        assertThrows(IllegalArgumentException.class, () -> uso.setTipoDoRemedio(""));
    }

    @Test
    void setTipoDoRemedio_stringVaziaNaoInternada_deveLancarExcecao() {
        assertThrows(IllegalArgumentException.class, () -> uso.setTipoDoRemedio(new String("")));
    }

    @Test
    void setTipoDoRemedio_valido_deveAtualizar() {
        uso.setTipoDoRemedio("comprimido");
        assertEquals("comprimido", uso.getTipoDoRemedio());
    }

    // --- serialização (funcional) ---

    @Test
    void toString_deveConterTodosOsCampos() {
        String linha = uso.toString();

        assertTrue(linha.contains("Dipirona"));
        assertTrue(linha.contains("2"));
        assertTrue(linha.contains("seg/ter"));
        assertTrue(linha.contains("10"));
        assertTrue(linha.contains("20"));
        assertTrue(linha.contains("8"));
        assertTrue(linha.contains("12"));
    }

    @Test
    void stringToUso_deveReconstruirObjetoCorretamente() {
        String linha = uso.toString();

        Uso reconstruido = Uso.stringToUso(linha);

        assertEquals("Dipirona", reconstruido.getRemedio().getNome());
        assertEquals(2, reconstruido.getDose());
        assertEquals(10, reconstruido.getDuracaoDoTratamento());
        assertEquals(20, reconstruido.getQtdDisponivel());
        assertEquals(8, reconstruido.getHorarioDeInicio());
        assertEquals(12, reconstruido.getIntervalo());
        assertEquals(2, reconstruido.getHorarios().size());
    }

    @Test
    void horariosStringToList_deveSepararHorarios() {
        ArrayList<String> horarios = Uso.horariosStringToList("seg/qua/sex");

        assertEquals(3, horarios.size());
        assertEquals("seg", horarios.get(0));
        assertEquals("qua", horarios.get(1));
        assertEquals("sex", horarios.get(2));
    }

    @Test
    void horariosToString_deveJuntarComBarra() {
        assertEquals("seg/ter", uso.horariosToString());
    }

    // --- getters/setters simples ---

    @Test
    void setRemedio_deveAtualizarMedicamento() {
        Medicamento novo = new Medicamento("Paracetamol");
        uso.setRemedio(novo);
        assertEquals("Paracetamol", uso.getRemedio().getNome());
    }

    @Test
    void setHorarioDeInicio_eSetIntervalo_devemAtualizar() {
        uso.setHorarioDeInicio(14);
        uso.setIntervalo(8);
        assertEquals(14, uso.getHorarioDeInicio());
        assertEquals(8, uso.getIntervalo());
    }

    @Test
    void calcularHorariosDeUso_intervaloNegativo_deveLancarExcecao() {
        assertThrows(IllegalArgumentException.class, () -> uso.setIntervalo(-6));
    }

    @Test
    void construtorSimples_deveInicializarCamposBasicos() {
        Uso usoSimples = new Uso(
                new Medicamento("Aspirina"),
                1,
                new ArrayList<>(Arrays.asList("dom")),
                3,
                15);

        assertEquals("Aspirina", usoSimples.getRemedio().getNome());
        assertEquals(1, usoSimples.getDose());
        assertEquals(3, usoSimples.getDuracaoDoTratamento());
        assertEquals(15, usoSimples.getQtdDisponivel());
    }
}

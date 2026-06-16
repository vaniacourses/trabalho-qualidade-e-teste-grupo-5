package backend.gerenciamento;

import backend.usuario.Uso;
import backend.Medicamento;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.lang.reflect.Field;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DataTest {

    @BeforeEach
    void resetarEstado() throws Exception {
        resetarUltimaVerificacao(0);
    }

    // ----------------------------
    // horaDoRemedio()
    // ----------------------------

    @Test
    void horaDoRemedioDeveRetornarTrueQuandoHoraEDiaCoincidem() throws Exception {
        resetarUltimaVerificacao(-1);

        int horaAtual = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        Uso uso = novoUsoComDia(diaDaSemanaAtual());

        assertTrue(Data.horaDoRemedio(uso, horaAtual));
    }

    @Test
    void horaDoRemedioDeveRetornarFalseQuandoHoraNaoBate() {
        int horaAtual = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        int horaErrada = (horaAtual + 1) % 24;
        Uso uso = novoUsoComDia(diaDaSemanaAtual());

        assertFalse(Data.horaDoRemedio(uso, horaErrada));
    }

    @Test
    void horaDoRemedioDeveRetornarFalseQuandoDiaNaoBate() {
        int horaAtual = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        Uso uso = novoUsoComDia(diaDiferenteDeHoje());

        assertFalse(Data.horaDoRemedio(uso, horaAtual));
    }

    @Test
    void horaDoRemedio_deveRetornarFalseQuandoHoraJaFoiVerificada() throws Exception {
        resetarUltimaVerificacao(Calendar.getInstance().get(Calendar.HOUR_OF_DAY));

        Uso uso = novoUsoComDia(diaDaSemanaAtual());
        int horaAtual = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);

        assertFalse(Data.horaDoRemedio(uso, horaAtual));
    }

    // ----------------------------
    // ehMeiaNoite()
    // ----------------------------

    @Test
    void ehMeiaNoiteDeveRefletirHorarioSistema() {
        boolean resultado = Data.ehMeiaNoite();
        int horaAtual = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);

        assertEquals(horaAtual == 0, resultado);
    }

    // ----------------------------
    // verificarHora()
    // ----------------------------

    @Test
    void deveRetornarTrueQuandoHoraAtualCorresponde() throws Exception {
        resetarUltimaVerificacao(-1);
        int horaAtual = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        assertTrue(Data.verificarHora(horaAtual));
    }

    @Test
    void deveRetornarFalseQuandoHoraNaoCorresponde() {
        int horaAtual = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        int horaDiferente = (horaAtual + 1) % 24;

        assertFalse(Data.verificarHora(horaDiferente));
    }

    @Test
    void verificarHora_deveRetornarFalseQuandoHoraJaVerificada() throws Exception {
        int horaAtual = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        resetarUltimaVerificacao(horaAtual);
        assertFalse(Data.verificarHora(horaAtual));
    }

    @Test
    void verificarHora_mesmaHoraNaSegundaChamada_deveRetornarFalse() throws Exception {
        resetarUltimaVerificacao(-1);
        int horaAtual = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);

        assertTrue(Data.verificarHora(horaAtual));
        assertFalse(Data.verificarHora(horaAtual));
    }

    // ----------------------------
    // verificarUltimaVerificacao()
    // ----------------------------

    @Test
    void devePermitirHorarioAindaNaoVerificado() throws Exception {
        resetarUltimaVerificacao(-1);
        assertTrue(Data.verificarUltimaVerificacao(10));
    }

    @Test
    void deveBloquearHorarioJaVerificado() throws Exception {
        resetarUltimaVerificacao(10);
        assertFalse(Data.verificarUltimaVerificacao(10));
    }

    @Test
    void devePermitirHorarioDiferenteDoUltimoVerificado() throws Exception {
        resetarUltimaVerificacao(10);
        assertTrue(Data.verificarUltimaVerificacao(11));
    }

    // ----------------------------
    // formatarDia()
    // ----------------------------

    @Test
    void deveFormatarSegundaCorretamente() {
        assertEquals(2, Data.formatarDia("seg"));
    }

    @Test
    void deveFormatarTercaCorretamente() {
        assertEquals(3, Data.formatarDia("ter"));
    }

    @Test
    void deveFormatarQuartaCorretamente() {
        assertEquals(4, Data.formatarDia("qua"));
    }

    @Test
    void deveFormatarQuintaCorretamente() {
        assertEquals(5, Data.formatarDia("qui"));
    }

    @Test
    void deveFormatarSextaCorretamente() {
        assertEquals(6, Data.formatarDia("sex"));
    }

    @Test
    void deveFormatarSabadoCorretamente() {
        assertEquals(7, Data.formatarDia("sab"));
    }

    @Test
    void deveFormatarDomingoCorretamente() {
        assertEquals(1, Data.formatarDia("dom"));
    }

    @Test
    void entradaInvalida_deveSerRejeitadaEmFormatarDia() {
        assertEquals(0, Data.formatarDia("segunda"));
        assertEquals(0, Data.formatarDia(""));
        assertEquals(0, Data.formatarDia("invalido"));
    }

    // ----------------------------
    // verificarDia()
    // ----------------------------

    @Test
    void deveRetornarTrueQuandoHojeEstaNaLista() {
        ArrayList<String> dias = new ArrayList<>(Arrays.asList(diaDaSemanaAtual()));
        assertTrue(Data.verificarDia(dias));
    }

    @Test
    void deveRetornarFalseQuandoHojeNaoEstaNaLista() {
        ArrayList<String> dias = new ArrayList<>(Arrays.asList(diaDiferenteDeHoje()));
        assertFalse(Data.verificarDia(dias));
    }

    @Test
    void verificarDia_listaNula_deveRetornarFalse() {
        assertFalse(Data.verificarDia(null));
    }

    @Test
    void verificarDia_listaVazia_deveRetornarFalse() {
        assertFalse(Data.verificarDia(new ArrayList<>()));
    }

    @Test
    void verificarDia_diaInvalido_deveRetornarFalse() {
        ArrayList<String> dias = new ArrayList<>(Arrays.asList("segunda"));
        assertFalse(Data.verificarDia(dias));
    }

    @Test
    void verificarDia_multiplosDias_deveRetornarTrueQuandoUmCoincide() {
        ArrayList<String> dias = new ArrayList<>(Arrays.asList(
                diaDiferenteDeHoje(),
                diaDaSemanaAtual()));
        assertTrue(Data.verificarDia(dias));
    }

    private void resetarUltimaVerificacao(int valor) throws Exception {
        Field field = Data.class.getDeclaredField("ultimaVerficacaoHorario");
        field.setAccessible(true);
        field.setInt(null, valor);
    }

    private String diaDaSemanaAtual() {
        return switch (Calendar.getInstance().get(Calendar.DAY_OF_WEEK)) {
            case 2 -> "seg";
            case 3 -> "ter";
            case 4 -> "qua";
            case 5 -> "qui";
            case 6 -> "sex";
            case 7 -> "sab";
            default -> "dom";
        };
    }

    private String diaDiferenteDeHoje() {
        String hoje = diaDaSemanaAtual();
        for (String dia : List.of("seg", "ter", "qua", "qui", "sex", "sab", "dom")) {
            if (!dia.equals(hoje)) {
                return dia;
            }
        }
        return "seg";
    }

    private Uso novoUsoComDia(String dia) {
        return new Uso(
                new Medicamento("Dipirona"),
                1,
                new ArrayList<>(Arrays.asList(dia)),
                5,
                10,
                8,
                12);
    }
}

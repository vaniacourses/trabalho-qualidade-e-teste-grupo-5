package backend.gerenciamento;

import backend.usuario.Uso;
import backend.Medicamento;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.lang.reflect.Field;

import org.junit.jupiter.api.Test;

class DataTest {

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

        int horaAtual =
            Calendar.getInstance().get(Calendar.HOUR_OF_DAY);

        int horaErrada = (horaAtual + 1) % 24;
        Uso uso = novoUsoComDia(diaDaSemanaAtual());

        assertFalse(Data.horaDoRemedio(uso, horaErrada));
    }

    @Test
    void horaDoRemedioDeveRetornarFalseQuandoDiaNaoBate() {
        int horaAtual = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);

        if (Calendar.getInstance().get(Calendar.DAY_OF_WEEK) != 2) {
            Uso uso = novoUsoComDia("seg");
            assertFalse(Data.horaDoRemedio(uso, horaAtual));
        }
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
    
    // ----------------------------
    // ehMeiaNoite()
    // ----------------------------

    @Test
    void ehMeiaNoiteDeveRefletirHorarioSistema() {

        boolean resultado = Data.ehMeiaNoite();

        int horaAtual =
                Calendar.getInstance().get(Calendar.HOUR_OF_DAY);

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

        int horaAtual =
                Calendar.getInstance().get(Calendar.HOUR_OF_DAY);

        int horaDiferente = (horaAtual + 1) % 24;

        assertFalse(Data.verificarHora(horaDiferente));
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
    void horaDoRemedio_deveRetornarFalseQuandoHoraJaFoiVerificada() throws Exception {
        resetarUltimaVerificacao(Calendar.getInstance().get(Calendar.HOUR_OF_DAY));

        String hoje = diaDaSemanaAtual();
        Uso uso = novoUsoComDia(hoje);

        int horaAtual = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        assertFalse(Data.horaDoRemedio(uso, horaAtual));
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
    void deveFormatarDomingoComoDefault() {
        assertEquals(1, Data.formatarDia("dom"));
    }

    @Test
    void entradaInvalida_deveSerRejeitadaEmFormatarDia() {
        assertNotEquals(1, Data.formatarDia("segunda"));
    }

    // ----------------------------
    // verificarDia()
    // ----------------------------

    @Test
    void deveRetornarTrueQuandoHojeEstaNaLista() {

        Calendar c = Calendar.getInstance();
        int hoje = c.get(Calendar.DAY_OF_WEEK);

        String diaHoje;

        switch (hoje) {
            case 2: diaHoje = "seg"; break;
            case 3: diaHoje = "ter"; break;
            case 4: diaHoje = "qua"; break;
            case 5: diaHoje = "qui"; break;
            case 6: diaHoje = "sex"; break;
            case 7: diaHoje = "sab"; break;
            default: diaHoje = "dom";
        }

        ArrayList<String> dias =
                new ArrayList<>(Arrays.asList(diaHoje));

        assertTrue(Data.verificarDia(dias));
    }

    @Test
    void deveRetornarFalseQuandoHojeNaoEstaNaLista() {

        ArrayList<String> dias =
                new ArrayList<>(Arrays.asList("seg"));

        Calendar c = Calendar.getInstance();

        if (c.get(Calendar.DAY_OF_WEEK) != 2) {
            assertFalse(Data.verificarDia(dias));
        }
    }

    @Test
    void verificarDia_listaNula_deveRetornarFalse() {
        assertFalse(Data.verificarDia(null));
    }

    @Test
    void verificarDia_diaInvalidoNoDomingo_deveRetornarFalse() {
        if (Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == 1) {
            ArrayList<String> dias = new ArrayList<>(Arrays.asList("segunda"));
            assertFalse(Data.verificarDia(dias));
        }
    }

}
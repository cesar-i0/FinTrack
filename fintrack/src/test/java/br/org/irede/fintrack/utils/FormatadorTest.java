package br.org.irede.fintrack.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class FormatadorTest {

    @Test
    @DisplayName("05 - Converter data entre String e LocalDate")
    void deveConverterDataEntreStringELocalDate() {
        LocalDate data = LocalDate.of(2026, 9, 12);

        assertEquals(data, Formatador.conversorData("12/09/2026"));
        assertEquals("12/09/2026", Formatador.conversorString(data));
    }

    @Test
    @DisplayName("06 - Rejeitar data vazia ou invalida")
    void deveRetornarNuloParaDataVaziaOuInvalida() {
        assertNull(Formatador.conversorData(null));
        assertNull(Formatador.conversorData(""));
        assertNull(Formatador.conversorData("32/02/2026"));
        assertNull(Formatador.conversorString(null));
    }

    @Test
    @DisplayName("07 - Converter valores com virgula ou ponto")
    void deveConverterValoresComVirgulaOuPonto() {
        assertEquals(1234.56, Formatador.conversorDouble("1234,56"));
        assertEquals(1234.56, Formatador.conversorDouble(" 1234.56 "));
    }

    @Test
    @DisplayName("08 - Retornar zero para valor vazio ou invalido")
    void deveRetornarZeroParaValorVazioOuInvalido() {
        assertEquals(0.0, Formatador.conversorDouble(null));
        assertEquals(0.0, Formatador.conversorDouble(""));
        assertEquals(0.0, Formatador.conversorDouble("abc"));
    }
}
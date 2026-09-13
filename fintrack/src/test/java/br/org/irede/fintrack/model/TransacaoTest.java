package br.org.irede.fintrack.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class TransacaoTest {

    @Test
    @DisplayName("01 - Criar transacao com os dados informados")
    void deveCriarTransacaoComOsDadosInformados() {
        LocalDate data = LocalDate.of(2026, 9, 12);
        Transacao transacao = new Transacao("Salário", 3500.0, data, true, "Renda");

        assertEquals("Salário", transacao.getDescricao());
        assertEquals(3500.0, transacao.getValor());
        assertEquals(data, transacao.getDate());
        assertEquals(true, transacao.getReceita());
        assertEquals("Renda", transacao.getCategoria());
        assertEquals("Receita", transacao.getTipo());
    }

    @Test
    @DisplayName("02 - Alterar dados pelos setters")
    void deveAlterarDadosPelosSetters() {
        Transacao transacao = new Transacao();
        LocalDate data = LocalDate.of(2026, 10, 1);

        transacao.setId(7);
        transacao.setDescricao("Aluguel");
        transacao.setValor(1200.0);
        transacao.setDate(data);
        transacao.setReceita(false);
        transacao.setCategoria("Moradia");

        assertEquals(7, transacao.getId());
        assertEquals("Aluguel", transacao.getDescricao());
        assertEquals(1200.0, transacao.getValor());
        assertEquals(data, transacao.getDate());
        assertEquals(false, transacao.getReceita());
        assertEquals("Moradia", transacao.getCategoria());
        assertEquals("Despesa", transacao.getTipo());
        assertEquals("Aluguel | R$ 1200.0 | Despesa", transacao.toString());
    }

    @Test
    @DisplayName("03 - Retornar tipo vazio sem receita informada")
    void deveRetornarTipoVazioQuandoReceitaNaoFoiInformada() {
        Transacao transacao = new Transacao();

        assertNull(transacao.getReceita());
        assertEquals("", transacao.getTipo());
    }

    @Test
    @DisplayName("04 - Armazenar data final da transacao mensal")
    void deveArmazenarDataFinalDaTransacaoMensal() {
        LocalDate inicio = LocalDate.of(2026, 9, 1);
        LocalDate fim = LocalDate.of(2026, 12, 1);
        TransacaoMensal transacao = new TransacaoMensal(
                "Curso", 250.0, inicio, false, "Educação", fim);

        assertEquals(inicio, transacao.getDate());
        assertEquals(fim, transacao.getDateEnd());
        assertEquals("Curso | R$ 250.0 | Despesa |  | 2026-12-01", transacao.toString());
    }
}
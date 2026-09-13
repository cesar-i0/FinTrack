package br.org.irede.fintrack.dao;

import br.org.irede.fintrack.model.Transacao;
import br.org.irede.fintrack.model.TransacaoMensal;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TransacaoDAOTest {

    private Connection connection;
    private TransacaoDAO dao;

    @BeforeEach
    void configurarBanco() throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite::memory:");

        try (Statement statement = connection.createStatement()) {
            statement.execute("""
                    CREATE TABLE transactions(
                        t_id INTEGER PRIMARY KEY AUTOINCREMENT,
                        description TEXT NOT NULL,
                        t_value DECIMAL(10,2) NOT NULL,
                        t_type TEXT NOT NULL,
                        t_date TEXT NOT NULL,
                        category TEXT NOT NULL,
                        end_date TEXT
                    )
                    """);
        }

        dao = new TransacaoDAO(connection);
    }

    @AfterEach
    void fecharBanco() throws SQLException {
        connection.close();
    }

    @Test
        @DisplayName("09 - Salvar, buscar, atualizar e excluir transacao")
    void deveSalvarBuscarAtualizarEExcluirTransacao() throws SQLException {
        String categoria = "JUnit-CRUD";
        Transacao transacao = new Transacao(
                "JUnit Transacao CRUD", 125.50,
                LocalDate.of(2026, 9, 12), false, categoria);

        dao.save(transacao);

        assertNotNull(transacao.getId());
        assertEquals(transacao.getId(), dao.findById(transacao.getId()).getId());
        List<Transacao> porDescricao = dao.findByDescription("JUnit Transacao CRUD");
        assertEquals(1, porDescricao.size());
        assertEquals(125.50, porDescricao.get(0).getValor());
        assertEquals("Despesa", porDescricao.get(0).getTipo());

        transacao.setDescricao("JUnit Transacao Atualizada");
        transacao.setValor(200.0);
        transacao.setReceita(true);
        dao.update(transacao);

        List<Transacao> porData = dao.findByData(LocalDate.of(2026, 9, 12));
        assertEquals(1, porData.stream()
                .filter(item -> transacao.getId().equals(item.getId()))
                .count());
        assertEquals(200.0, porData.stream()
                .filter(item -> transacao.getId().equals(item.getId()))
                .findFirst()
                .orElseThrow()
                .getValor());

        Map<String, Double> totaisPorCategoria = dao.getTotalByCategory(
                LocalDate.of(2026, 9, 1), LocalDate.of(2026, 9, 30));
        assertEquals(200.0, totaisPorCategoria.get(categoria));

        dao.delete(transacao.getId());
        assertFalse(dao.findByDescription("JUnit Transacao Atualizada").stream()
                .anyMatch(item -> transacao.getId().equals(item.getId())));
    }

    @Test
        @DisplayName("10 - Calcular saldo com receitas e despesas")
    void deveCalcularSaldoComReceitasEDespesas() throws SQLException {
        dao.save(new Transacao("Receita JUnit", 500.0,
                LocalDate.of(2026, 9, 1), true, "Saldo"));
        dao.save(new Transacao("Despesa JUnit", 175.0,
                LocalDate.of(2026, 9, 2), false, "Saldo"));

        assertEquals(500.0, dao.getTotalPorTipo(true));
        assertEquals(175.0, dao.getTotalPorTipo(false));
        assertEquals(325.0, dao.getSaldo());
    }

    @Test
        @DisplayName("11 - Salvar e buscar transacao mensal")
    void deveSalvarEBuscarTransacaoMensal() throws SQLException {
        TransacaoMensal transacao = new TransacaoMensal(
                "JUnit Transacao Mensal", 80.0,
                LocalDate.of(2026, 9, 1), true, "JUnit Mensal",
                LocalDate.of(2026, 12, 1));

        dao.saveMensal(transacao);

        assertNotNull(transacao.getId());
        List<Transacao> resultado = dao.findByPeriod(
                LocalDate.of(2026, 9, 1), LocalDate.of(2026, 9, 30));
        TransacaoMensal encontrada = resultado.stream()
                .filter(item -> transacao.getId().equals(item.getId()))
                .map(item -> (TransacaoMensal) item)
                .findFirst()
                .orElseThrow();

        assertEquals("JUnit Mensal", encontrada.getCategoria());
        assertEquals(LocalDate.of(2026, 12, 1), encontrada.getDateEnd());
    }

    @Test
        @DisplayName("12 - Rejeitar transacao ou id nulos")
    void deveRejeitarTransacaoOuIdNulos() {
        assertThrows(IllegalArgumentException.class, () -> dao.save(null));
        assertThrows(IllegalArgumentException.class, () -> dao.update(null));
        assertThrows(IllegalArgumentException.class, () -> dao.delete(null));
        assertThrows(IllegalArgumentException.class, () -> dao.findById(null));
        assertThrows(IllegalArgumentException.class, () -> dao.getTotalPorTipo(null));
    }
}
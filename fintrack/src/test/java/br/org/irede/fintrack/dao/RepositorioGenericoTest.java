package br.org.irede.fintrack.dao;

import br.org.irede.fintrack.model.Transacao;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.sql.DriverManager;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class RepositorioGenericoTest {

    @Test
    @DisplayName("13 - Usar TransacaoDAO pelo contrato generico")
    void devePermitirUsarOTransacaoDAOPeloContratoGenerico() throws Exception {
        try (var connection = DriverManager.getConnection("jdbc:sqlite::memory:");
             var statement = connection.createStatement()) {
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

            RepositorioGenerico<Transacao, Integer> repositorio = new TransacaoDAO(connection);
            Transacao transacao = new Transacao(
                    "Teste contrato generico", 50.0,
                    LocalDate.of(2026, 9, 12), true, "Teste");

            repositorio.save(transacao);

            assertNotNull(transacao.getId());
            assertEquals(transacao.getId(), repositorio.findById(transacao.getId()).getId());
            repositorio.delete(transacao.getId());
            assertNull(repositorio.findById(transacao.getId()));
        }
    }
}
package br.org.irede.fintrack.dao;
import br.org.irede.fintrack.model.Transacao;
import br.org.irede.fintrack.utils.DataBaseConnection;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import br.org.irede.fintrack.utils.Formatador;

public class TransacaoDAO {

    private final Connection connection;

    public TransacaoDAO() {
        try{
            this.connection = DataBaseConnection.makeConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Error to create connection at the database", e);
        }
    }

    public TransacaoDAO(Connection connection) {
        this.connection = connection;
    }

    // CREATE
    public void save(Transacao t) throws SQLException {
        String sql = "INSERT INTO transactions (transaction_description, transaction_value, transaction_type, transaction_data) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, t.getDescricao());
            stmt.setDouble(2, t.getValor());
            stmt.setString(3, (t.getReceita() ? "Receita" : "Despesa"));
            stmt.setString(4, (Formatador.conversorString(t.getData())));
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    t.setId(rs.getInt(1));
                }
            }
        }
    }

    // READ
    public List<Transacao> findAll() throws SQLException {
        String sql = "SELECT * FROM transactions ORDER BY transation_id DESC";
        List<Transacao> lista_transacoes = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                lista_transacoes.add(instanciarTransacao(rs));
            }
        }
        return lista_transacoes;
    }

    public Transacao findById(Integer id) throws SQLException {
        String sql = "SELECT * FROM transactions WHERE transation_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return instanciarTransacao(rs);
                }
            }
        }
        return null;
    }

    // UPDATE
    public void update(Transacao t) throws SQLException {
        String sql = "UPDATE transactions SET transaction_description = ?, transaction_value = ?, transaction_type = ?, transaction_data = ? WHERE id_transaction = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, t.getDescricao());
            stmt.setDouble(2, t.getValor());
            stmt.setString(3, (t.getReceita() ? "Receita" : "Despesa"));
            stmt.setString(4, (Formatador.conversorString(t.getData())));
            stmt.setInt(5, t.getId());
            stmt.executeUpdate();
        }
    }

    // DELETE
    public void delete(Long id) throws SQLException {
        String sql = "DELETE FROM transactions WHERE id_transaction = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }


    public Double getTotalPorTipo(Boolean isReceita) throws SQLException {
        String tipo = isReceita ? "Receita" : "Despesa";
        String sql = "SELECT SUM(transaction_value) FROM transactions WHERE transaction_type = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, tipo);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble(1);
                }
            }
        }
        return 0.0;
    }

    public Transacao instanciarTransacao(ResultSet rs) throws SQLException {
        Integer id = rs.getInt("transation_id");
        String desc =  rs.getString("transaction_description");
        Double val = rs.getDouble("transaction_value");
        Boolean isR = "Receita".equalsIgnoreCase(rs.getString("transaction_type"));
        LocalDate data = Formatador.conversorData(rs.getString("transaction_data"));
        Transacao t = new Transacao(desc, val, data, isR);
        t.setId(id);
        return t;
    }

    public List<Transacao> findByData(LocalDate data) throws SQLException {
        String sql = "SELECT * FROM transactions WHERE transaction_data = ? ORDER BY transation_id DESC";
        List<Transacao> lista = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, Formatador.conversorString(data));
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    lista.add(instanciarTransacao(rs));
                }
            }
        }
        return lista;
    }

}

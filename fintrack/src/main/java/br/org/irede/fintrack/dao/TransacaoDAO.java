package br.org.irede.fintrack.dao;
import br.org.irede.fintrack.model.Transacao;
import br.org.irede.fintrack.model.TransacaoMensal;
import br.org.irede.fintrack.utils.DataBaseConnection;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import br.org.irede.fintrack.utils.Formatador;

public class TransacaoDAO implements RepositorioGenerico<Transacao, Integer> {

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
    @Override
    public void save(Transacao t) throws SQLException {
        validarTransacao(t);
        String sql = "INSERT INTO transactions (description, t_value, t_type, t_date, category) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, t.getDescricao());
            stmt.setDouble(2, t.getValor());
            stmt.setString(3, (t.getReceita() ? "Receita" : "Despesa"));
            stmt.setString(4, (Formatador.conversorString(t.getDate())));
            stmt.setString(5, t.getCategoria());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    t.setId(rs.getInt(1));
                }
            }
        }
    }
    public void saveMensal(TransacaoMensal t) throws SQLException {
        validarTransacao(t);
        String sql = "INSERT INTO transactions (description, t_value, t_type, t_date, category, end_date) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, t.getDescricao());
            stmt.setDouble(2, t.getValor());
            stmt.setString(3, (t.getReceita() ? "Receita" : "Despesa"));
            stmt.setString(4, (Formatador.conversorString(t.getDate())));
            stmt.setString(5, t.getCategoria());
            stmt.setString(6, (Formatador.conversorString(t.getDateEnd())));
            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    t.setId(rs.getInt(1));
                }
            }
        }
    }

    // READ
    public List<Transacao> findByPeriod(LocalDate ini, LocalDate end) throws SQLException {
        String sql = "SELECT * FROM transactions WHERE t_date >= ? AND t_date <= ? ORDER BY t_date DESC";
        List<Transacao> lista_transacoes = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, Formatador.conversorString(ini));
            stmt.setString(2, Formatador.conversorString(end));
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    lista_transacoes.add(instanciarTransacaoMesal(rs));
                }
            }
        }
        return lista_transacoes;
    }

    public List<Transacao> findByDescription(String d) throws SQLException {
        String sql = "SELECT * FROM transactions WHERE description LIKE ? ";
        List<Transacao> lista_transacoes = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, "%" + d + "%");
            try(ResultSet rs = stmt.executeQuery()){
                while(rs.next()) {
                    lista_transacoes.add(instanciarTransacaoMesal(rs));
                }
            }
        }
        return lista_transacoes;
    }

    // UPDATE
    public void update(Transacao t) throws SQLException {
        validarTransacao(t);
        String sql = "UPDATE transactions SET description = ?, t_value = ?, t_type = ?, t_date = ?, category = ? WHERE t_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, t.getDescricao());
            stmt.setDouble(2, t.getValor());
            stmt.setString(3, (t.getReceita() ? "Receita" : "Despesa"));
            stmt.setString(4, (Formatador.conversorString(t.getDate())));
            stmt.setString(5, (t.getCategoria()));
            stmt.setInt(6, t.getId());
            stmt.executeUpdate();
            if (!connection.getAutoCommit()) {
                connection.commit();
            }
        }
    }

    public void updateMensal(TransacaoMensal t) throws SQLException {
        validarTransacao(t);
        String sql = "UPDATE transactions SET description = ?, t_value = ?, t_type = ?, t_date = ?, category = ?, end_date = ? WHERE t_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setString(1, t.getDescricao());
            stmt.setDouble(2, t.getValor());
            stmt.setString(3, (t.getReceita() ? "Receita" : "Despesa"));
            stmt.setString(4, (Formatador.conversorString(t.getDate())));
            stmt.setString(5, (t.getCategoria()));
            stmt.setString(6, (Formatador.conversorString(t.getDateEnd())));
            stmt.setInt(7, t.getId());
            stmt.executeUpdate();
            if (!connection.getAutoCommit()) {
                connection.commit();
            }
        }
    }

    // DELETE
    @Override
    public void delete(Integer id) throws SQLException {
        validarId(id);
        String sql = "DELETE FROM transactions WHERE t_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public Transacao findById(Integer id) throws SQLException {
        validarId(id);
        String sql = "SELECT * FROM transactions WHERE t_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    if (rs.getString("end_date") == null) {
                        return instanciarTransacao(rs);
                    }
                    return instanciarTransacaoMesal(rs);
                }
            }
        }
        return null;
    }

    public Double getTotalPorTipo(Boolean isReceita) throws SQLException {
        if (isReceita == null) {
            throw new IllegalArgumentException("O tipo da transação deve ser informado");
        }
        String tipo = isReceita ? "Receita" : "Despesa";
        String sql = "SELECT SUM(t_value) FROM transactions WHERE t_type = ?";
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

    public Double getSaldo() throws SQLException {
        return getTotalPorTipo(true) - getTotalPorTipo(false);
    }

    private static void validarTransacao(Transacao transacao) {
        if (transacao == null) {
            throw new IllegalArgumentException("A transação não pode ser nula");
        }
    }

    private static void validarId(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("O id da transação não pode ser nulo");
        }
    }

    public Transacao instanciarTransacao(ResultSet rs) throws SQLException {
        Integer id = rs.getInt("t_id");
        String desc =  rs.getString("description");
        Double val = rs.getDouble("t_value");
        Boolean isR = "Receita".equalsIgnoreCase(rs.getString("t_type"));
        LocalDate data = Formatador.conversorData(rs.getString("t_date"));
        String cat = rs.getString("category");
        Transacao t = new Transacao(desc, val, data, isR, cat);
        t.setId(id);
        return t;
    }

    public TransacaoMensal instanciarTransacaoMesal(ResultSet rs) throws SQLException {
        Integer id = rs.getInt("t_id");
        String desc =  rs.getString("description");
        Double val = rs.getDouble("t_value");
        Boolean isR = "Receita".equalsIgnoreCase(rs.getString("t_type"));
        LocalDate data = Formatador.conversorData(rs.getString("t_date"));
        String cat = rs.getString("category");
        LocalDate dateF = Formatador.conversorData(rs.getString("end_date"));
        TransacaoMensal t = new TransacaoMensal(desc, val, data, isR, cat, dateF);
        t.setId(id);
        return t;
    }

    public List<Transacao> findByData(LocalDate data) throws SQLException {
        String sql = "SELECT * FROM transactions WHERE t_date = ? ORDER BY t_id DESC";
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

    public Map<String, Double> getTotalByCategory(LocalDate ini, LocalDate end) throws SQLException {
        Map<String, Double> map = new HashMap<>();
        String sql = "SELECT category, SUM(t_value) AS total FROM transactions WHERE t_date >= ? AND t_date <= ? GROUP BY category";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, Formatador.conversorString(ini));
            stmt.setString(2, Formatador.conversorString(end));
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    map.put(rs.getString("category"), rs.getDouble("total"));
                }
            }
        }
        return map;
    }

}

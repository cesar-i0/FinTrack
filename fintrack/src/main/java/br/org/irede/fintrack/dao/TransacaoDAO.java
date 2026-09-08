package br.org.irede.fintrack.dao;
import br.org.irede.fintrack.model.Transacao;
import br.org.irede.fintrack.model.TransacaoMensal;
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
        String sql = "INSERT INTO monthly_transactions (t_id, ini_date, end_date) VALUES (?, ?, ?) ";
        connection.setAutoCommit(false);
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            save(t);
            stmt.setInt(1, t.getId());
            stmt.setString(2,Formatador.conversorString(t.getDataInicial()));
            stmt.setString(3,Formatador.conversorString(t.getDataFinal()));
            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    t.setId(rs.getInt(1));
                }
            }
            connection.commit();
        }catch (SQLException e){
            connection.rollback();
            throw e;
        }finally {
            connection.setAutoCommit(true);
        }
    }

    // READ
    public List<Transacao> findByPeriod(LocalDate ini, LocalDate end) throws SQLException {
        String sql = "SELECT * FROM transactions t LEFT JOIN monthly_transactions m ON t.t_id = m.t_id WHERE t.t_date >= ? AND t.t_date <= ? ORDER BY t.t_date DESC";
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
        String sql = "SELECT * FROM transactions t LEFT JOIN monthly_transactions m ON t.t_id = m.t_id WHERE description LIKE ? ";
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
        String sql = "UPDATE transactions SET description = ?, t_value = ?, t_type = ?, t_date = ?, category = ? WHERE t_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, t.getDescricao());
            stmt.setDouble(2, t.getValor());
            stmt.setString(3, (t.getReceita() ? "Receita" : "Despesa"));
            stmt.setString(4, (Formatador.conversorString(t.getDate())));
            stmt.setString(5, (t.getCategoria()));
            stmt.setInt(6, t.getId());
            stmt.executeUpdate();
        }
    }

    public void updateMensal(TransacaoMensal t) throws SQLException {
        String sql = "UPDATE monthly_transactions SET ini_date = ?, end_date = ? WHERE t_id = ?";
        try {
            connection.setAutoCommit(false);
            update(t);
            try (PreparedStatement stmt = connection.prepareStatement(sql)){
                stmt.setString(1, Formatador.conversorString(t.getDataInicial()));
                stmt.setString(2, Formatador.conversorString(t.getDataFinal()));
                stmt.setInt(3, t.getId());
                stmt.executeUpdate();
            }
            connection.commit();
        }catch (SQLException e){
            connection.rollback();
        }finally {
            connection.setAutoCommit(true);
        }
    }

    // DELETE
    public void delete(Integer id) throws SQLException {
        String sql = "DELETE FROM transactions WHERE t_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public void deleteMensal(Integer id) throws SQLException {
        String sql = "DELETE FROM monthly_transactions WHERE t_id = ?";
        connection.setAutoCommit(false);
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            delete(id);
            stmt.setInt(1, id);
            stmt.executeUpdate();
            connection.commit();
        }catch (SQLException e){
            connection.rollback();
        }finally {
            connection.setAutoCommit(true);
        }
    }


    public Double getTotalPorTipo(Boolean isReceita) throws SQLException {
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
        LocalDate dateI = Formatador.conversorData(rs.getString("ini_date"));
        LocalDate dateF = Formatador.conversorData(rs.getString("end_date"));
        TransacaoMensal t = new TransacaoMensal(desc, val, data, isR, cat, dateI, dateF);
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

}

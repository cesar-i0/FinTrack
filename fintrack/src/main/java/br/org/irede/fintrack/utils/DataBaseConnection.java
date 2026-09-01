package br.org.irede.fintrack.utils;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;

public class DataBaseConnection {

    private static final String URL = "jdbc:sqlite:fintrack.db";

    public static Connection makeConnection() throws SQLException{
        return DriverManager.getConnection(URL);
    }

    public static void initDB(){
        try(Connection conn = makeConnection(); Statement stmt = conn.createStatement()){
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS transactions(
                    t_id INTEGER PRIMARY KEY AUTOINCREMENT,
                    description TEXT NOT NULL,
                    t_value DECIMAL(10,2) NOT NULL,
                    t_type TEXT NOT NULL,
                    t_date TEXT NOT NULL,
                    category TEXT NOT NULL
                )
            """);
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS monthly_transactions(
                    t_id INTEGER PRIMARY KEY,
                    ini_date TEXT NOT NULL,
                    end_date TEXT NOT NULL,
                    FOREIGN KEY (t_id) REFERENCES transactions(t_id)
                )
            """);
        }catch(SQLException e){
            throw new RuntimeException("Error to iniciate at the database",e);
        }
    }

}

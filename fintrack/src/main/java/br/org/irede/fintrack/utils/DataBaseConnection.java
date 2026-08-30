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
                    transation_id INTEGER PRIMARY KEY AUTOINCREMENT,
                    transaction_description TEXT NOT NULL,
                    transaction_value DECIMAL(10,2) NOT NULL,
                    transaction_type TEXT NOT NULL,
                    transaction_data TEXT NOT NULL
                )
            """);
        }catch(SQLException e){
            throw new RuntimeException("Error to iniciate at the database",e);
        }
    }

}

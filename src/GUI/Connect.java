/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

/**
 *
 * @author BUI QUANG HIEU
 */
import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.sql.*;

public class Connect {

   public static Connection ketNoi(String database) {
        try {
            String user = "admin";
            String pass = "123";
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url = "jdbc:sqlserver://127.0.0.1;databaseName=" + database;
            Connection connection = DriverManager.getConnection(url, user, pass);
            return connection;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}

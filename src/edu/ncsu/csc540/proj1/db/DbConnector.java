package edu.ncsu.csc540.proj1.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnector {

    public Connection conn = null;

    /**
     * JDBC URL for NCSU's Oracle server
     */
    public static final String jdbcURL = "jdbc:oracle:thin:@ora.csc.ncsu.edu:1521:orcl";

    /**
     * Username for NCSU Oracle Server (unity ID)
     */
    public static String user;

    /**
     * Password for NCSU Oracle Server (ID#)
     */
    public static String password;

    public Connection getConnection() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection(jdbcURL, user, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public void closeConnection(){
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

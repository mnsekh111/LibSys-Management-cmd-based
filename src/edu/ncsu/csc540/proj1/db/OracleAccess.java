package edu.ncsu.csc540.proj1.db;

import java.sql.*;

/**
 * Simple class to connect to the Oracle database
 * @author Dixon Crews
 */
public class OracleAccess {

    /**
     * Connection object
     */
    private Connection connect = null;

    /**
     * JDBC URL for NCSU's Oracle server
     */
    public static final String jdbcURL = "jdbc:oracle:thin:@ora.csc.ncsu.edu:1521:orcl";

    /**
     * Username for NCSU Oracle Server (unity ID)
     */
    private String user = "dccrews";

    /**
     * Password for NCSU Oracle Server (ID#)
     */
    private String password = "001068830";

    /**
     * Constructor
     * @throws Exception
     */
    public OracleAccess() {
        this.connectToDB();
    }

    /**
     * Connects to the Oracle database
     */
    public void connectToDB() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            connect = DriverManager.getConnection(jdbcURL, user, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads from the Oracle database and prints out the results
     */
    public void readDB() {
        PreparedStatement ps;
        ResultSet rs;
        try {
            ps = connect.prepareStatement("SELECT * FROM TABLE1");
            rs = ps.executeQuery();

            while (rs.next()) {
                System.out.println("==========================================");
                System.out.println(rs.getString("TEST"));
                System.out.println("==========================================");
            }

            ps.close();
            rs.close();
            close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Closes the Connection object
     */
    private void close() {
        if (connect != null) {
            try {
                connect.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
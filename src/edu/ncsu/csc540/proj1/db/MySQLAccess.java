package edu.ncsu.csc540.proj1.db;

import java.sql.*;

public class MySQLAccess {
    private Connection connect = null;

    public MySQLAccess() throws Exception {
        this.connectToDB();
    }

    public void connectToDB() throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/csc540", "root", "");
    }

    public void readDB() throws Exception {
        //Prepare and execute SELECT statement
        PreparedStatement ps = connect.prepareStatement("SELECT * FROM csc540.Scores");
        ResultSet rs = ps.executeQuery();

        //Print out results
        while (rs.next()) {
            System.out.println("==========================================");
            System.out.println("(" + rs.getString("Team") + "," + rs.getString("Day") + "," + rs.getString("Opponent") + "," + rs.getString("Runs") + "," + rs.getString("idx") + ")");
            System.out.println("==========================================");
        }

        ps.close();
        rs.close();

        //Close
        close();
    }

    private void close() throws Exception {
        if (connect != null) {
            connect.close();
        }
    }
}
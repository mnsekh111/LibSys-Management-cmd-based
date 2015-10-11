package edu.ncsu.csc540.proj1.db;

import java.sql.*;

public class MySQLAccess {
    private Connection connect = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    public void readDB() throws Exception {
        try {
            //Connect to DB
            Class.forName("com.mysql.jdbc.Driver");
            connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/csc540?user=root&password=");

            //Prepare and execute SELECT statement
            preparedStatement = connect.prepareStatement("SELECT * FROM csc540.Scores");
            resultSet = preparedStatement.executeQuery();

            //Print out results
            while (resultSet.next()) {
                System.out.println("==========================================");
                System.out.println("(" + resultSet.getString("Team") + "," + resultSet.getString("Day") + "," + resultSet.getString("Opponent") + "," + resultSet.getString("Runs") + "," + resultSet.getString("idx") + ")");
                System.out.println("==========================================");
            }
        } catch (Exception e) {
            throw e;
        } finally {
            close();
        }
    }

    private void close() throws Exception {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (connect != null) {
                connect.close();
            }
        } catch (Exception e) {
            throw e;
        }
    }
}
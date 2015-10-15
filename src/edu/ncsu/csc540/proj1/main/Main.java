package edu.ncsu.csc540.proj1.main;

import edu.ncsu.csc540.proj1.db.*;

public class Main {
    public static void main(String[] args) throws Exception {
        //Set up JDBC
        Class.forName("com.mysql.jdbc.Driver");

        //Build the DB
        DBBuilder builder = new DBBuilder();
        builder.createTables();

        //Read the DB
        MySQLAccess dao = new MySQLAccess();
        dao.readDB();
    }
}

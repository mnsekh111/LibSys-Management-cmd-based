package edu.ncsu.csc540.proj1.main;

import edu.ncsu.csc540.proj1.db.*;

/**
 * Simple Main class
 * @author Dixon Crews
 */
public class Main {

    /**
     * Main method that builds a database and reads from it
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        DBBuilder builder = new DBBuilder();
        builder.createTables();

        OracleAccess oAccess = new OracleAccess();
        oAccess.readDB();
    }
}

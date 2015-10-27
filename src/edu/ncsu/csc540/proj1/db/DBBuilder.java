package edu.ncsu.csc540.proj1.db;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Builds the database for the project
 * @author Dixon Crews
 */
public class DBBuilder {

    /**
     * Path to the createTables.sql file
     */
    private static String createTablesPath = "sql/createTables.sql";

    /**
     * Path to the dropTables.sql file
     */
    private static String dropTablesPath = "sql/dropTables.sql";

    /**
     * Path to the test_data.sql file
     */
    private static String testDataPath = "sql/test_data.sql";

    /**
     * Path to the sample_data.sql file
     */
    private static String sampleDataPath = "sql/sample_data.sql";

    /**
     * DbConnector object to get a database connection
     */
    private static DbConnector conn;

    /**
     * Connection object
     */
    private static Connection connect;

    /**
     * Main method to build database
     * @param args
     */
    public static void main(String[] args) {
        conn = new DbConnector();
        connect = conn.getConnection();

        System.out.println("Building database...");
        long startTime = System.currentTimeMillis();
        System.out.println("Dropping tables...");
        executeSQL(dropTablesPath);
        System.out.println("Creating tables...");
        executeSQL(createTablesPath);
        System.out.println("Inserting test data...");
        executeSQL(testDataPath);
        //System.out.println("Inserting sample data...");
        //executeSQL(sampleDataPath);
        long endTime = System.currentTimeMillis();
        System.out.println("Operation completed in " + (endTime - startTime) + "ms.");

        conn.closeConnection();
    }

    public static void executeSQL(String path) {
        ArrayList<String> queryList = parseSQLFile(path);

        try {
            for(String sql : queryList) {
                System.out.println(sql);
                java.sql.Statement stmt = connect.createStatement();
                stmt.execute(sql);
                stmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads in an SQL file and parses it to split up queries. Returns the list
     * of queries as an ArrayList<String>
     * @param path path to the SQL file
     * @return an ArrayList<String> with one query per entry
     */
    public static ArrayList<String> parseSQLFile(String path) {
        ArrayList<String> queryList = new ArrayList<String>();

        //Using the delimeter of --- in createTables.sql
        boolean useDashDelimeter = path.equals(createTablesPath);

        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File(path)));
            String line = "";
            String currentQuery = "";
            while((line = reader.readLine()) != null) {
                if(useDashDelimeter) {
                    if(line.endsWith("end;---")) {
                        currentQuery += line.substring(0, line.length() - 3);
                        queryList.add(currentQuery);
                        currentQuery = "";
                    } else if(line.endsWith("---")) {
                        currentQuery += line.substring(0, line.length() - 4);
                        queryList.add(currentQuery);
                        currentQuery = "";
                    } else {
                        currentQuery += line;
                    }
                } else {
                    for(int i = 0; i < line.length(); i++) {
                        if(line.charAt(i) == ';') {
                            queryList.add(currentQuery);
                            currentQuery = "";
                        } else {
                            currentQuery += line.charAt(i);
                        }
                    }
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return queryList;
    }

}
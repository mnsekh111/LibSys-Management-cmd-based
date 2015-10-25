package edu.ncsu.csc540.proj1.db;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
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
     * JDBC URL for NCSU's Oracle server
     */
    private static final String jdbcURL = "jdbc:oracle:thin:@ora.csc.ncsu.edu:1521:orcl";

    /**
     * Username for NCSU Oracle Server (unity ID)
     */
    private static String user = "dccrews";

    /**
     * Password for NCSU Oracle Server (ID#)
     */
    private static String password = "001068830";

    /**
     * Main method to build database
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("Building database...");
        long startTime = System.currentTimeMillis();
        System.out.println("Dropping tables...");
        dropTables();
        System.out.println("Creating tables...");
        createTables();
        System.out.println("Inserting test data...");
        generateTestData();
        long endTime = System.currentTimeMillis();
        System.out.println("Operation completed in " + (endTime - startTime) + "ms.");
    }

    /**
     * Inserts all of the test data in test_data.sql
     */
    public static void generateTestData() {
        ArrayList<String> queryList = parseSQLFile(testDataPath);

        try {
            Connection connect = DriverManager.getConnection(jdbcURL, user, password);
            for(String sql : queryList) {
                System.out.println(sql);
                java.sql.Statement stmt = connect.createStatement();
                stmt.execute(sql);
                stmt.close();
            }
            connect.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Drops the tables defined in dropTables file.
     */
    public static void dropTables() {
        ArrayList<String> queryList = parseSQLFile(dropTablesPath);

        try {
            Connection connect = DriverManager.getConnection(jdbcURL, user, password);
            for(String sql : queryList) {
                System.out.println(sql);
                java.sql.Statement stmt = connect.createStatement();
                stmt.execute(sql);
                stmt.close();
            }
            connect.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates the tables defined in createTables file.
     */
    public static void createTables() {
        ArrayList<String> queryList = parseSQLFile(createTablesPath);

        try {
            Connection connect = DriverManager.getConnection(jdbcURL, user, password);
            for(String sql : queryList) {
                System.out.println(sql);
                java.sql.Statement stmt = connect.createStatement();
                stmt.execute(sql);
                stmt.close();
            }
            connect.close();
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
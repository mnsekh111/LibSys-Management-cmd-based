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
    private String createTablesPath = "sql/createTables.sql";

    /**
     * JDBC URL for NCSU's Oracle server
     */
    private static final String jdbcURL = "jdbc:oracle:thin:@ora.csc.ncsu.edu:1521:orcl";

    /**
     * Username for NCSU Oracle Server (unity ID)
     */
    private String user = "dccrews";

    /**
     * Password for NCSU Oracle Server (ID#)
     */
    private String password = "001068830";

    /**
     * Creates the tables defined in sql/createTables.sql.
     */
    public void createTables() {
        ArrayList<String> queryList = parseSQLFile(createTablesPath);

        try {
            Connection connect = DriverManager.getConnection(jdbcURL, user, password);
            for(String sql : queryList) {
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
    public ArrayList<String> parseSQLFile(String path) {
        ArrayList<String> queryList = new ArrayList<String>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File(path)));
            String line = "";
            String currentQuery = "";
            while((line = reader.readLine()) != null) {
                for(int i = 0; i < line.length(); i++) {
                    if(line.charAt(i) == ';') {
                        queryList.add(currentQuery);
                        currentQuery = "";
                    } else {
                        currentQuery += line.charAt(i);
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
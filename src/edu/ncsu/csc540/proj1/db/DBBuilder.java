package edu.ncsu.csc540.proj1.db;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

public class DBBuilder {

    private String createTablesPath = "sql/createTables.sql";
    private String dropTablesPath = "sql/dropTables.sql";

    public DBBuilder() {

    }

    public void createTables() {
        ArrayList<String> queryList = parseSQLFile(createTablesPath);

        try {
            Connection connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/csc540?createDatabaseIfNotExist=true", "root", "");
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

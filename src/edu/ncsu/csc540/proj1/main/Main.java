package edu.ncsu.csc540.proj1.main;

import edu.ncsu.csc540.proj1.db.MySQLAccess;

public class Main {
    public static void main(String[] args) throws Exception {
        MySQLAccess dao = new MySQLAccess();
        dao.readDB();
    }
}

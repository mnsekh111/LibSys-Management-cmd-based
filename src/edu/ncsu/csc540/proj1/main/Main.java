package edu.ncsu.csc540.proj1.main;

import java.util.Scanner;

import edu.ncsu.csc540.proj1.db.OracleAccess;
import edu.ncsu.csc540.proj1.ui.MenuPage;

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
        /*
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        //DBBuilder builder = new DBBuilder();
        //builder.createTables();
        //oAccess.readDB();
        */

        OracleAccess dao = new OracleAccess();

        //New MenuPage object
        MenuPage menu = new MenuPage();

        //Welcome message
        System.out.println("Welcome to the library system.\n");

        //Display login option
        Scanner in = new Scanner(System.in);
        int patron_id = 0;
        while(patron_id == 0) {
            patron_id = menu.loginMenu(in);
        }

        System.out.println("\nPatron " + patron_id + " has successfully logged in.\n");

        //Find out if a patron is a student or faculty to display menu
        boolean isStudent = dao.isStudent(patron_id);

        if(isStudent) {
            menu.studentMenu(in);
        } else {
            menu.facultyMenu(in);
        }
    }
}

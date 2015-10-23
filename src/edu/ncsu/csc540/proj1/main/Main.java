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
        OracleAccess dao = new OracleAccess();

        //New MenuPage object
        MenuPage menu = new MenuPage();
        do {
            //Welcome message
            System.out.println("Welcome to the library system.\n");

            //Display login option
            Scanner in = new Scanner(System.in);
            int patron_id = 0;
            while(patron_id == 0) {
                patron_id = menu.loginMenu(in);
            }

            //Find out if a patron is a student or faculty to display menu
            int userType = dao.getUserType(patron_id);

            if(userType == 1) {
                System.out.println("\nStudent " + patron_id + " has successfully logged in.\n");
                menu.studentMenu(in, patron_id);
            } else if(userType == 2) {
                System.out.println("\nFaculty " + patron_id + " has successfully logged in.\n");
                menu.facultyMenu(in, patron_id);
            } else {
                System.out.println("\n **** User Not Found **** \n");
            }
        } while(true);
    }
}

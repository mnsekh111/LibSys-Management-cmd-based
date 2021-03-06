package edu.ncsu.csc540.proj1.main;

import java.util.Scanner;

import edu.ncsu.csc540.proj1.db.DBBuilder;
import edu.ncsu.csc540.proj1.db.DbConnector;
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
        	Scanner in = new Scanner(System.in);
        	System.out.println("Setting the DB: \n\t Please provide Unity ID and password to setup the DB.");
        	
        	System.out.print("\t Please enter the Unity ID:");
        	String userName = in.next();
        	DbConnector.user = userName;
        	
        	System.out.print("\t Please enter the password:");
        	String password = in.next();
        	DbConnector.password = password;
        	
        	System.out.println("\t Enter 1 to load fresh data. (All the old data will be lost!!)");
        	System.out.println("\t Enter 2 to continue with existing data.");
        	
        	int option = in.nextInt();
        	if(option == 1){
        		System.out.print("Please enter the absolute path of unziped folder: ");
        		String stPath = in.next();
        		DBBuilder db = new DBBuilder(stPath);
            	db.refreshDb();
        	}
        	
        	
            //Welcome message
            System.out.println(" \n\n\n\n\n\n--------------------------------- DB setup completed ---------------------------- \nWelcome to the library system.\n");

            //Display login option
            
            int patron_id = 0;
            while(patron_id == 0) {
                patron_id = menu.loginMenu(in);
            }

            //Find out if a patron is a student or faculty to display menu
            int userType = dao.getUserType(patron_id);

            if(userType == 1) {
                System.out.println("\nStudent " + patron_id + " has successfully logged in.\n");
                dao.setPatronStatus(patron_id, menu);
                menu.setStudent(true);
                menu.studentMenu(in, patron_id);
            } else if(userType == 2) {
                System.out.println("\nFaculty " + patron_id + " has successfully logged in.\n");
                dao.setPatronStatus(patron_id, menu);
                menu.setStudent(false);
                menu.facultyMenu(in, patron_id);
            } else {
                System.out.println("\n **** User Not Found **** \n");
            }
        } while(true);
    }
}

package edu.ncsu.csc540.proj1.main;

import java.util.Scanner;

import edu.ncsu.csc540.proj1.db.*;
import edu.ncsu.csc540.proj1.ui.MenuPage;
import javafx.animation.ParallelTransition;

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

//        DBBuilder builder = new DBBuilder();
//        builder.createTables();
//
//        OracleAccess oAccess = new OracleAccess();
//        oAccess.readDB();
        
        Scanner in = new Scanner(System.in);
        int patron_id = 0;
        MenuPage menu = new MenuPage();
        while(patron_id==0){
        	patron_id = menu.loginMenu(in);
        }
        System.out.println("User "+patron_id+" has successfully logged in.");
        
        menu.mainMenu(in);;
        
        
    }
}

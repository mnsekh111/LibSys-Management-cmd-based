package edu.ncsu.csc540.proj1.ui;

import java.util.Scanner;

public class MenuPage {

	public MenuPage(){
		
	}
	
	public int loginMenu(Scanner in){
		System.out.println("Please select an option : ");
		System.out.println("1. Login ");
		int option = in.nextInt();
		int user_id = 0;
		if(option == 1){
			System.out.println("Please enter your Login ID");
			user_id = in.nextInt();
			return user_id;
		}else{
			System.out.println("Please select a valid option");
			return 0;
		}
		
	}
	
	public void mainMenu(Scanner in){
		
	}
}


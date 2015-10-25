package edu.ncsu.csc540.proj1.ui;

import java.util.Scanner;

import edu.ncsu.csc540.proj1.models.Student;

public class MenuPage {

    Student student = new Student();

    public int loginMenu(Scanner in) {
        int user_id = 0;
        System.out.print("Please enter your ID number to login: ");
        user_id = in.nextInt();
        return user_id;
    }

    public void studentMenu(Scanner in, int patronId) {
        int selectedOption = 0;
        do {
            System.out.println("Please select an option:");
            System.out.println("\t1. Profile");
            System.out.println("\t2. Resources");
            System.out.println("\t3. Checked-Out Resources");
            System.out.println("\t4. Resource Requests");
            System.out.println("\t5. Notifications");
            System.out.println("\t6. Balance Due");
            System.out.println("\t7. Logout\n");
            selectedOption = in.nextInt();

            switch(selectedOption) {
            case 1:
                studentProfileMenu(in, patronId);
            }
        } while(selectedOption != 7);
    }

    public void studentProfileMenu(Scanner in, int patronID) {
        System.out.println("==========\nMy Profile:\n");
        student.printStudentProfile(patronID);
        System.out.println("\n==========\n");
    }

    public void facultyMenu(Scanner in, int patronId) {
        int selectedOption = 0;
        do {
            System.out.println("Please select an option:");
            System.out.println("\t1. Profile");
            System.out.println("\t2. Resources");
            System.out.println("\t3. Checked-Out Resources");
            System.out.println("\t4. Resource Requests");
            System.out.println("\t5. Notifications");
            System.out.println("\t6. Balance Due");
            System.out.println("\t7. Logout\n");
            selectedOption = in.nextInt();
        } while(selectedOption != 7);
    }
}


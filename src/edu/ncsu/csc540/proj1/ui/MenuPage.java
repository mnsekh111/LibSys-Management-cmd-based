package edu.ncsu.csc540.proj1.ui;

import java.util.Scanner;

import edu.ncsu.csc540.proj1.models.Faculty;
import edu.ncsu.csc540.proj1.models.Student;

public class MenuPage {

    Student student = new Student();
    Faculty faculty = new Faculty();

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
                break;
            case 2:
                studentResourcesMenu(in, patronId);
                break;
            case 3:
                studentConfStudyMenu(in, patronId);
                break;
            }
        } while(selectedOption != 7);
    }

    public void studentProfileMenu(Scanner in, int patronID) {
        System.out.println("==========\nMy Profile:\n");
        student.printStudentProfile(patronID);
        System.out.println("\n==========\n");
    }

    public void studentResourcesMenu(Scanner in, int patronID) {
        int selectedOption = 0;
        do {
             System.out.println("Please select an option:");
             System.out.println("\t1. Publications");
             System.out.println("\t2. Conference/Study Rooms");
             System.out.println("\t3. Cameras");
             System.out.println("\t4. Back\n");
             selectedOption = in.nextInt();

             switch(selectedOption) {
             case 1:
                 break;
             case 2:
                 studentConfStudyMenu(in, patronID);
                 break;
             case 3:
                 break;
             }
        } while(selectedOption != 4);
    }

    public void studentConfStudyMenu(Scanner in, int patronID) {
        int num_occupants = 0;
        int library = 0; //0 = Hunt, 1 = DH Hill
        System.out.print("Number of occupants? ");
        num_occupants = in.nextInt();
        System.out.print("Library (0 for Hunt, 1 for Hill)? ");
        library = in.nextInt();

        System.out.println(num_occupants);
        System.out.println(library);
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

            switch(selectedOption) {
            case 1:
                facultyProfileMenu(in, patronId);
                break;
            case 2:
                facultyResourcesMenu(in, patronId);
                break;
            case 3:
                facultyConfStudyMenu(in, patronId);
                break;
            }
        } while(selectedOption != 7);
    }

    public void facultyProfileMenu(Scanner in, int patronID) {
        System.out.println("==========\nMy Profile:\n");
        faculty.printFacultyProfile(patronID);
        System.out.println("\n==========\n");
    }

    public void facultyResourcesMenu(Scanner in, int patronID) {
        int selectedOption = 0;
        do {
             System.out.println("Please select an option:");
             System.out.println("\t1. Publications");
             System.out.println("\t2. Conference/Study Rooms");
             System.out.println("\t3. Cameras");
             System.out.println("\t4. Back\n");
             selectedOption = in.nextInt();

             switch(selectedOption) {
             case 1:
                 break;
             case 2:
                 facultyConfStudyMenu(in, patronID);
                 break;
             case 3:
                 break;
             }
        } while(selectedOption != 4);
    }

    public void facultyConfStudyMenu(Scanner in, int patronID) {
        int num_occupants = 0;
        int library = 0; //0 = Hunt, 1 = DH Hill
        System.out.print("Number of occupants? ");
        num_occupants = in.nextInt();
        System.out.print("Library (0 for Hunt, 1 for Hill)? ");
        library = in.nextInt();
    }
}


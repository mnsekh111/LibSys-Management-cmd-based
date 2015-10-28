package edu.ncsu.csc540.proj1.ui;

import java.util.Scanner;

import edu.ncsu.csc540.proj1.models.Faculty;
import edu.ncsu.csc540.proj1.models.Student;

public class MenuPage {

    Student student = new Student();
    Faculty faculty = new Faculty();

    public int loginMenu(Scanner in) {
        int user_id = 0;
        System.out.print("Please enter your ID number to login (-1 to exit): ");
        user_id = in.nextInt();
        if(user_id == -1) {
            System.exit(1);
        }
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
        int selectedOption = 0;
        do {
            System.out.println("==========\nMy Profile:\n");
            student.printStudentProfile(patronID);
            System.out.println("\n==========\n");
            System.out.println("Please select an option:");
            System.out.println("\t1. Edit First Name");
            System.out.println("\t2. Edit Last Name");
            System.out.println("\t3. Edit Phone");
            System.out.println("\t4. Edit Alternate Phone");
            System.out.println("\t5. Edit Sex");
            System.out.println("\t6. Edit Street");
            System.out.println("\t7. Edit City");
            System.out.println("\t8. Edit Postcode");
            System.out.println("\t9. Edit Program");
            System.out.println("\t10. Edit Year");
            System.out.println("\t11. Edit Department");
            System.out.println("\t12. Go back");
            selectedOption = in.nextInt();

            in.nextLine(); //eat the newline

            String enteredData = null;
            switch(selectedOption) {
            case 1:
                System.out.print("Enter new first name (<= 25 chars): ");
                enteredData = in.nextLine();
                student.updateStudentProfile(patronID, enteredData, 1);
                break;
            case 2:
                System.out.print("Enter new last name (<= 25 chars): ");
                enteredData = in.nextLine();
                student.updateStudentProfile(patronID, enteredData, 2);
                break;
            case 3:
                System.out.print("Enter new phone (<= 25 chars): ");
                enteredData = in.nextLine();
                student.updateStudentProfile(patronID, enteredData, 3);
                break;
            case 4:
                System.out.print("Enter new alternate phone (<= 25 chars): ");
                enteredData = in.nextLine();
                student.updateStudentProfile(patronID, enteredData, 4);
                break;
            case 5:
                System.out.print("Enter new sex (Male or Female): ");
                enteredData = in.nextLine();
                student.updateStudentProfile(patronID, enteredData, 5);
                break;
            case 6:
                System.out.print("Enter new street (<= 25 chars): ");
                enteredData = in.nextLine();
                student.updateStudentProfile(patronID, enteredData, 6);
                break;
            case 7:
                System.out.print("Enter new city (<= 25 chars): ");
                enteredData = in.nextLine();
                student.updateStudentProfile(patronID, enteredData, 7);
                break;
            case 8:
                System.out.print("Enter new postcode (<= 25 chars): ");
                enteredData = in.nextLine();
                student.updateStudentProfile(patronID, enteredData, 8);
                break;
            case 9:
                System.out.print("Enter new program (B.S., M.A., M.S., Ph.D.): ");
                enteredData = in.nextLine();
                student.updateStudentProfile(patronID, enteredData, 9);
                break;
            case 10:
                System.out.print("Enter new year (max 2 digits): ");
                enteredData = in.nextLine();
                student.updateStudentProfile(patronID, enteredData, 10);
                break;
            case 11:
                System.out.print("Enter new department (CSC or CH): ");
                enteredData = in.nextLine();
                student.updateStudentProfile(patronID, enteredData, 11);
                break;
            }
        } while(selectedOption != 12);
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
        String date = "";
        int early_hour = 0, late_hour = 0;
        int num_occupants = 0;
        int library = 0; //0 = Hunt, 1 = DH Hill

        in.nextLine(); //eat newline

        System.out.println("Day to search for? (Enter in format dd-mmm-yyyy like 01-Nov-2015) ");
        date += in.nextLine();
        System.out.println("Earliest hour? (24 hour clock) ");
        early_hour = in.nextInt();
        System.out.println("Latest hour? (24 hour clock, no more than 3 hours after earliest hour) ");
        late_hour = in.nextInt();
        System.out.println("Number of occupants? ");
        num_occupants = in.nextInt();
        System.out.println("Library (0 for Hunt, 1 for Hill)? ");
        library = in.nextInt();

        student.printAvailableRooms(in, patronID, date, early_hour, late_hour, num_occupants, library);
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
        int selectedOption = 0;
        do {
            System.out.println("==========\nMy Profile:\n");
            faculty.printFacultyProfile(patronID);
            System.out.println("\n==========\n");
            System.out.println("Please select an option:");
            System.out.println("\t1. Edit First Name");
            System.out.println("\t2. Edit Last Name");
            System.out.println("\t3. Edit Category");
            System.out.println("\t4. Edit Department & Course");
            System.out.println("\t5. Go back");
            selectedOption = in.nextInt();

            in.nextLine(); //eat the newline

            String enteredData = null;
            switch(selectedOption) {
            case 1:
                System.out.print("Enter new first name (<= 25 chars): ");
                enteredData = in.nextLine();
                faculty.updateFacultyProfile(patronID, enteredData, 1);
                break;
            case 2:
                System.out.print("Enter new last name (<= 25 chars): ");
                enteredData = in.nextLine();
                faculty.updateFacultyProfile(patronID, enteredData, 2);
                break;
            case 3:
                System.out.print("Enter new category (Assistant Professor, Associate Professor, Lecturer, Professor): ");
                enteredData = in.nextLine();
                faculty.updateFacultyProfile(patronID, enteredData, 3);
                break;
            case 4:
                System.out.print("Enter new department (CSC or CH): ");
                enteredData = in.nextLine();
                enteredData += "-";
                System.out.print("Enter new course (valid number): ");
                enteredData += in.nextLine();
                faculty.updateFacultyProfile(patronID, enteredData, 4);
                break;
            }
        } while(selectedOption != 5);
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
        String date = null;
        int early_hour = 0, late_hour = 0;
        int num_occupants = 0;
        int library = 0; //0 = Hunt, 1 = DH Hill
        System.out.print("Day to search for? (Enter in format dd-mmm-yyyy like 01-Nov-2015) ");
        date = in.nextLine();
        System.out.println("Earliest hour? (24 hour clock) ");
        early_hour = in.nextInt();
        System.out.println("Latest hour? (24 hour clock, no more than 3 hours after earliest hour) ");
        late_hour = in.nextInt();
        System.out.print("Number of occupants? ");
        num_occupants = in.nextInt();
        System.out.print("Library (0 for Hunt, 1 for Hill)? ");
        library = in.nextInt();

        System.out.println(date);
        System.out.println(early_hour + " to " + late_hour);
        System.out.println(num_occupants);
        System.out.println(library);
    }
}


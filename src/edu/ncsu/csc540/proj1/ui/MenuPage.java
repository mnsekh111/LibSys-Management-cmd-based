package edu.ncsu.csc540.proj1.ui;

import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

import edu.ncsu.csc540.proj1.models.Faculty;
import edu.ncsu.csc540.proj1.models.Notifications;
import edu.ncsu.csc540.proj1.models.Publication;
import edu.ncsu.csc540.proj1.models.Student;
import edu.ncsu.csc540.proj1.models.Camera;
import edu.ncsu.csc540.proj1.models.Due;

public class MenuPage {

    private int cameraCheckoutDay = 6;
    private int cameraCheckoutStartTime = 9;
    private int cameraCheckoutEndTime = 12;

    private boolean isStudent;

    public boolean isPatronGood;

    public boolean isPatronGood() {
        return isPatronGood;
    }

    public void setPatronGood(boolean isPatronGood) {
        this.isPatronGood = isPatronGood;
    }

    public boolean isStudent() {
        return isStudent;
    }

    public void setStudent(boolean isStudent) {
        this.isStudent = isStudent;
    }

    Student student = new Student();
    Faculty faculty = new Faculty();

    public int loginMenu(Scanner in) {
        int user_id = 0;
        System.out.print("Please enter your ID number to login (-1 to exit): ");
        user_id = in.nextInt();
        if (user_id == -1) {
            System.exit(1);
        }
        return user_id;
    }

    public void studentMenu(Scanner in, int patronId) {
        int selectedOption = 0;
        do {
            System.out.println("Please select an option:");
            if(isPatronGood){
                System.out.println("\t1. Profile");
                System.out.println("\t2. Resources");
                System.out.println("\t3. Checked-Out Resources");
                System.out.println("\t4. Resource Requests");
                System.out.println("\t5. Notifications");
            }
            System.out.println("\t6. Balance Due");
            System.out.println("\t7. Logout\n");
            selectedOption = in.nextInt();

            switch (selectedOption) {
            case 1:
                if(isPatronGood)
                    studentProfileMenu(in, patronId);
                break;
            case 2:
                if(isPatronGood)
                    studentResourcesMenu(in, patronId);
                break;
            case 3:
                if(isPatronGood)
                    checkedOutResources(in, patronId);
                break;
            case 4:
                if(isPatronGood)
                    showPendingRequests(patronId);
                break;
            case 5:
                if(isPatronGood)
                    notifications(in, patronId);
                break;
            case 6:
                handleBalanceDue(in, patronId);
                break;
            }
        } while (selectedOption != 7);
    }

    public void showPendingRequests(int patronId){
        Publication pub = new Publication();
        pub.getPubRequests(patronId);
        pub.cleanUp();
        Camera cam = new Camera();
        cam.getCamInQueue(patronId);
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

            in.nextLine(); // eat the newline

            String enteredData = null;
            switch (selectedOption) {
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
        } while (selectedOption != 12);
    }

    public void studentResourcesMenu(Scanner in, int patronID) {
        int selectedOption = 0;
        do {
            System.out.println("Please select an option:");
            System.out.println("\t1. Publications");
            System.out.println("\t2. Study Rooms");
            System.out.println("\t3. Cameras");
            System.out.println("\t4. Back\n");
            selectedOption = in.nextInt();

            switch (selectedOption) {
            case 1:
                studentPublicationMenu(in, patronID);
                break;
            case 2:
                studentConfStudyMenu(in, patronID);
                break;
            case 3:
                handleCameras(in, patronID);
                break;
            }
        } while (selectedOption != 4);
    }

    public void studentConfStudyMenu(Scanner in, int patronID) {
        in.nextLine();

        // Find out if they want to reserve a new room or check in
        System.out.println("Please select an option:");
        System.out.println("\t1. Check out (take hold of room)");
        System.out.println("\t2. Check in (release room)");
        System.out.println("\t3. Reserve a new room");

        int selection = in.nextInt();
        if (selection == 1) {
            // check out
            student.checkOutRoom(in, patronID);
        } else if (selection == 2) {
            // check in
            student.checkInRoom(in, patronID);
        } else if (selection == 3) {
            // reserve
            String date = "";
            int early_hour = 0, late_hour = 0;
            int num_occupants = 0;
            int library = 0; // 0 = Hunt, 1 = DH Hill

            in.nextLine(); // eat newline

            System.out.println("Day to search for? (Enter in format dd-mmm-yyyy like 01-Nov-2015) ");
            date += in.nextLine();
            System.out.println("Earliest hour to search? (24 hour clock, hour only) ");
            early_hour = in.nextInt();
            System.out.println("Latest hour to search? (24 hour clock, hour only, <= 3 hours after earliest hour) ");
            late_hour = in.nextInt();
            System.out.println("Number of occupants? ");
            num_occupants = in.nextInt();
            System.out.println("Library (0 for Hunt, 1 for Hill)? ");
            library = in.nextInt();

            student.printAvailableRooms(in, patronID, date, early_hour, late_hour, num_occupants, library);
        }
    }

    public void facultyMenu(Scanner in, int patronId) {
        int selectedOption = 0;
        do {
            System.out.println("Please select an option:");
            if(isPatronGood){
                System.out.println("\t1. Profile");
                System.out.println("\t2. Resources");
                System.out.println("\t3. Checked-Out Resources");
                System.out.println("\t4. Resource Requests");
                System.out.println("\t5. Notifications");
            }
            System.out.println("\t6. Balance Due");
            System.out.println("\t7. Logout\n");
            selectedOption = in.nextInt();

            switch (selectedOption) {
            case 1:
                if(isPatronGood)
                    facultyProfileMenu(in, patronId);
                break;
            case 2:
                if(isPatronGood)
                    facultyResourcesMenu(in, patronId);
                break;
            case 3:
                break;
            case 5:
                if(isPatronGood)
                    notifications(in, patronId);
                break;
            case 6:
                handleBalanceDue(in, patronId);
                break;
            }
        } while (selectedOption != 7);
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

            in.nextLine(); // eat the newline

            String enteredData = null;
            switch (selectedOption) {
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
                System.out
                        .print("Enter new category (Assistant Professor, Associate Professor, Lecturer, Professor): ");
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
        } while (selectedOption != 5);
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

            switch (selectedOption) {
            case 1:
                break;
            case 2:
                facultyConfStudyMenu(in, patronID);
                break;
            case 3:
                handleCameras(in, patronID);
                break;
            }
        } while (selectedOption != 4);
    }

    public void facultyConfStudyMenu(Scanner in, int patronID) {
        in.nextLine();

        // Find out if they want to reserve a new room or check in
        System.out.println("Please select an option:");
        System.out.println("\t1. Check out (take hold of room)");
        System.out.println("\t2. Check in (release room)");
        System.out.println("\t3. Reserve a new room");

        int selection = in.nextInt();
        if (selection == 1) {
            // check out
            faculty.checkOutRoom(in, patronID);
        } else if (selection == 2) {
            // check in
            faculty.checkInRoom(in, patronID);
        } else if (selection == 3) {
            // reserve
            String date = "";
            int early_hour = 0, late_hour = 0;
            int num_occupants = 0;
            int library = 0; // 0 = Hunt, 1 = DH Hill
            int type = 0; // 0 = conf, 1 = study

            in.nextLine(); // eat newline

            System.out.println("Day to search for? (Enter in format dd-mmm-yyyy like 01-Nov-2015) ");
            date += in.nextLine();
            System.out.println("Earliest hour to search? (24 hour clock, hour only) ");
            early_hour = in.nextInt();
            System.out.println("Latest hour to search? (24 hour clock, hour only, <= 3 hours after earliest hour) ");
            late_hour = in.nextInt();
            System.out.println("Number of occupants? ");
            num_occupants = in.nextInt();
            System.out.println("Library (0 for Hunt, 1 for Hill)? ");
            library = in.nextInt();
            System.out.println("Type of room (0 for Conference, 1 for Study)? ");
            type = in.nextInt();

            faculty.printAvailableRooms(in, patronID, date, early_hour, late_hour, num_occupants, library, type);
        }
    }

    public void handleCameras(Scanner in, int patronId) {
        Camera cam = new Camera();
        int selectedOption = 0;
        do {
            System.out.println("\t1. Show available cameras");
            System.out.println("\t2. My Checked Out Cameras");
            System.out.println("\t3. New Checkout Request");
            System.out.println("\t0. Back");
            selectedOption = in.nextInt();
            if (selectedOption == 1) {
                System.out.println("Select the week.");
                System.out.println("\t1. This week");
                System.out.println("\t2. Next week");
                System.out.println("\t3. Third week");
                System.out.println("\t4. Fourth week");
                int selectedWeek = in.nextInt();
                cam.getAvailableCameras(selectedWeek);
                System.out.println("\tX. Select CamId to reserve ");
                System.out.println("\t0. Cancel");
                int camId = in.nextInt();
                if (selectedWeek != 0) {
                    cam.insertIntoCamQueue(camId, selectedWeek, patronId);
                }
            } else if (selectedOption == 2) {
                int innerOption = 0;
                do {
                    cam.getCheckedOut(patronId);
                    System.out.println("\t1. Return Camera");
                    System.out.println("\t0. Back");
                    innerOption = in.nextInt();
                    if (innerOption != 0) {
                        System.out.println("Enter ID");
                        int idToReturn = in.nextInt();
                        cam.returnCamera(idToReturn);
                    }
                } while (innerOption != 0);
            } else if (selectedOption == 3) {
                Date dt = new Date();
                Calendar cal = Calendar.getInstance();

                if (dt.getHours() > cameraCheckoutStartTime && dt.getHours() < cameraCheckoutEndTime
                        && cal.get(Calendar.DAY_OF_WEEK) == cameraCheckoutDay) {
                    int innerOption = 0;
                    do {
                        cam.getQueuedList(patronId);
                        System.out.println("\t0. Back.");

                        innerOption = in.nextInt();
                        cam.book_camera(innerOption, patronId);

                    } while (innerOption != 0);
                } else {
                    System.out.println(" -------------- You cannot checkout camera at this time. --------------");
                }

            }
        } while (selectedOption != 0);
    }

    public void notifications(Scanner in, int patronID) {
        Notifications notify = new Notifications();
        int backoption = 0;
        do {
            notify.getnotifications(patronID);
            System.out.println("\t0.Back.");
            backoption = in.nextInt();
        } while (backoption != 0);
    }

    public void handleBalanceDue(Scanner in, int patronId) {
        Due due = new Due();
        String innerOption = "0";
        do {
            int total = due.getFines(patronId);
            if (total == 0) {
                System.out.println("---------- No outstanding due. ----------");
                break;
            } else {
                System.out.println("Total Outstading Amount : " + total);
                System.out.println("\tX. Enter an ID to pay the fine");
                System.out.println("\tX. Enter 'All' to pay all the fines");
                System.out.println("\t0. Back");
                innerOption = in.next();
                if (innerOption.equalsIgnoreCase("All")) {
                    due.payAllFines(patronId);
                } else if (!innerOption.equalsIgnoreCase("0")) {
                    due.payFine(innerOption, patronId);
                }
            }
        } while (!innerOption.equalsIgnoreCase("0"));
    }

    private void studentPublicationMenu(Scanner in, int patronID) {
        int selectedOption = 0;
        int copyId;
        Publication publication = new Publication();

        do {
            System.out.println("\t1. Books");
            System.out.println("\t2. Conference Papers");
            System.out.println("\t3. Journal");
            System.out.println("\t4. All");
            System.out.println("\t5. Back");

            selectedOption = in.nextInt();
            switch (selectedOption) {
            case 1:
                publication.getAllBooks();
                break;
            case 2:
                publication.getAllConferencePapers();
                break;
            case 3:
                publication.getAllJournals();
                break;
            case 4:
                publication.getAll();
            default:
                break;
            }

            if (selectedOption >= 1 && selectedOption <= 4) {
                choosePublication(in, publication, patronID);
            }

        } while (selectedOption != 5);

        publication.cleanUp();
    }

    public void choosePublication(Scanner in, Publication publication, int patronID) {
        String publicationId;
        int copyId;
        int option;
        do {
            System.out.println("\n\t1.Checkout");
            if (!isStudent()) {
                System.out.println("\t2.Reserve");
            }
            System.out.println("\t0.Back");
            option = in.nextInt();

            switch (option) {
            case 1:
                System.out.println("Enter Publication ID ");
                publicationId = in.next();
                publication.getCopies(publicationId);
                System.out.println("Enter Copy ID ");
                copyId = in.nextInt();
                publication.checkOutCopy(patronID, copyId);;
                break;
            case 2:
                System.out.println("Enter Publication ID ");
                if (!isStudent()) {
                    System.out.println("Enter Publication ID ");
                    publicationId = in.next();
                    publication.getCopies(publicationId);
                    System.out.println("Enter Copy ID ");
                    copyId = in.nextInt();
                    publication.checkOutCopy(patronID, copyId);
                }
                break;
            default:
                break;
            }

        } while (option != 0);
    }

    public void checkedOutResources(Scanner in, int patronId) {
        Camera cam = new Camera();
        Publication pub = new Publication();
        int selectedOption = 0;
        do {
            System.out.println("Please select an option:");
            System.out.println("\t1. Publications");
            System.out.println("\t2. Cameras");
            System.out.println("\t3. Back\n");
            selectedOption = in.nextInt();
            if (selectedOption == 1) {
                int innerOption = 0;
                do {
                    pub.getCheckedout(patronId);
                    System.out.println("\t1. Return");
                    System.out.println("\t0. Back");
                    innerOption = in.nextInt();
                    if (innerOption != 0) {
                        System.out.println("Enter ID");
                        int idToReturn = in.nextInt();
                        pub.returnCopy(idToReturn);
                    }
                } while (innerOption != 0);
            } else if (selectedOption == 2) {
                int innerOption = 0;
                do {
                    cam.getCheckedOut(patronId);
                    System.out.println("\t1. Return");
                    System.out.println("\t0. Back");
                    innerOption = in.nextInt();
                    if (innerOption != 0) {
                        System.out.println("Enter ID");
                        int idToReturn = in.nextInt();
                        cam.returnCamera(idToReturn);
                    }
                } while (innerOption != 0);
            }

        } while (selectedOption != 3);
        pub.cleanUp();
    }
}

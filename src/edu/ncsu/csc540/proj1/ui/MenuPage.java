package edu.ncsu.csc540.proj1.ui;

import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

import edu.ncsu.csc540.proj1.models.Camera;

public class MenuPage {
	
	private int cameraCheckoutDay = 6;
	private int cameraCheckoutStartTime = 9;
	private int cameraCheckoutEndTime = 12;
	

    public MenuPage() {

    }

    public int loginMenu(Scanner in) {
        int user_id = 0;
        System.out.print("Please enter your ID number to login: ");
        user_id = in.nextInt();
        return user_id;
    }

    public void studentMenu(Scanner in, int patronId) {
    	int selectedOption = 0;
    	do{
    		System.out.println("Please select an option:");
    		System.out.println("\t1. Profile");
    		System.out.println("\t2. Resources");
    		System.out.println("\t3. Checked-Out Resources");
    		System.out.println("\t4. Resource Requests");
    		System.out.println("\t5. Notifications");
    		System.out.println("\t6. Balance Due");
    		System.out.println("\t7. Logout");
    		selectedOption = in.nextInt();
    		if(selectedOption == 4){
    			resourceRequest(in, patronId);
    		}
    	}while(selectedOption != 7);
    }

    public void facultyMenu(Scanner in, int patronId) {
    	int selectedOption = 0;
    	do{
    		System.out.println("Please select an option:");
    		System.out.println("\t1. Profile");
    		System.out.println("\t2. Resources");
    		System.out.println("\t3. Checked-Out Resources");
    		System.out.println("\t4. Resource Requests");
    		System.out.println("\t5. Notifications");
    		System.out.println("\t6. Balance Due");
    		System.out.println("\t7. Logout");
    		selectedOption = in.nextInt();
    		if(selectedOption == 4){
    			resourceRequest(in, patronId);
    		}
    	}while(selectedOption != 7);
    }
    
    public void resourceRequest(Scanner in, int patronId){
    	int selectedOption = 0;
    	do{
    		System.out.println("\t1. Cameras");
    		System.out.println("\t2. Books");
    		System.out.println("\t3. Rooms");
    		System.out.println("\t4. Resource Requests");
    		System.out.println("\t0. Back");
    		selectedOption = in.nextInt();
    		if(selectedOption == 1){
    			handleCameras(in, patronId);
    		}
    	}while(selectedOption != 0);
    }
    
    public void handleCameras(Scanner in, int patronId){
    	Camera cam = new Camera();
    	int selectedOption = 0;
    	do{
    		System.out.println("\t1. Request Cameras");
    		System.out.println("\t2. Checked Out Cameras");
    		System.out.println("\t3. Check Out New Request");
    		System.out.println("\t0. Back");
    		selectedOption = in.nextInt();
    		if(selectedOption == 1){
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
    			if(selectedWeek != 0){
    				cam.insertIntoCamQueue(camId, selectedWeek, patronId);
    			}
    		}
    		else if(selectedOption == 2){
    			int innerOption = 0;
    			do{
    				cam.getCheckedOut(patronId);
    				System.out.println("\t1. Return");
    				System.out.println("\t0. Back");
    				innerOption = in.nextInt();
    				if(innerOption != 0){
    					System.out.println("Enter ID");
    					int idToReturn = in.nextInt();
    					cam.returnCamera(idToReturn);
    				}
    			}while(innerOption!=0);
    		}else if(selectedOption ==3){
    			Date dt = new Date();
    			Calendar cal = Calendar.getInstance();
    			
    			if(true){//dt.getHours()> cameraCheckoutStartTime && dt.getHours() < cameraCheckoutEndTime &&  cal.get(Calendar.DAY_OF_WEEK) == cameraCheckoutDay){
    				int innerOption = 0;
    				do{
    				cam.getQueuedList(patronId);       			
        			System.out.println("\t0. Back.");
        			
        			innerOption = in.nextInt();
        			cam.book_camera(innerOption, patronId);
        			
    				}while(innerOption != 0 );
    			}else{
    				System.out.println(" -------------- You cannot checkout camera at this time. --------------");
    			}
    			
    		}
    	}while(selectedOption != 0);
    }
}


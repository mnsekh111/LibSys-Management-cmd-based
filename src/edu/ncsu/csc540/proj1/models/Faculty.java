package edu.ncsu.csc540.proj1.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;

import edu.ncsu.csc540.proj1.db.DbConnector;

public class Faculty {
    /**
     * Connection object
     */
    private DbConnector db = null;

    public Faculty(){
        this.db = new DbConnector();
    }
    
    public boolean getPatronStatus(int patron_id){
    	
    	PreparedStatement ps = null;
        ResultSet rs = null;
        Connection conn = db.getConnection();
        boolean isGood = false;
        try {
            ps = conn.prepareStatement("SELECT * FROM PATRON WHERE ID=?");
            ps.setInt(1, patron_id);
            rs = ps.executeQuery();
            if (rs.next()) {
            	isGood = (rs.getString("STATUS").equals("GOOD"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                ps.close();
                rs.close();
                db.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return isGood;
    }

    public boolean isFaculty(int id) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection conn = db.getConnection();
        boolean isFaculty = false;
        try {
            ps = conn.prepareStatement("SELECT * FROM FACULTY WHERE ID=?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                isFaculty = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                ps.close();
                rs.close();
                db.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return isFaculty;
    }

    public void printFacultyProfile(int id) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection conn = db.getConnection();

        try {
            ps = conn.prepareStatement("SELECT F.category, F.id, F.dept, F.course_id, "
                    + "P.fname, P.lname, P.country_name FROM FACULTY F, "
                    + "PATRON P WHERE F.id = P.id AND F.id = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();

            while(rs.next()) {
                System.out.println("First Name: " + rs.getString("fname"));
                System.out.println("Last Name: " + rs.getString("lname"));
                System.out.println("Nationality: " + rs.getString("country_name"));
                System.out.println("ID#: " + rs.getString("id"));
                System.out.println("Category: " + rs.getString("category"));
                System.out.println("Department: " + rs.getString("dept"));
                System.out.println("Course: " + rs.getString("course_id"));
            }

            ps.close();
            rs.close();
            db.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateFacultyProfile(int patronID, String enteredData, int fieldNo) {
        Connection conn = db.getConnection();
        PreparedStatement ps = null;
        try {
            switch(fieldNo) {
            case 1:
                ps = conn.prepareStatement("UPDATE Patron SET fname = ? WHERE id = ?");
                break;
            case 2:
                ps = conn.prepareStatement("UPDATE Patron SET lname = ? WHERE id = ?");
                break;
            case 3:
                ps = conn.prepareStatement("UPDATE Faculty SET category = ? WHERE id = ?");
                break;
            case 4:
                String data[] = enteredData.split("-");
                data[0] = data[0].trim();
                data[1] = data[1].trim();
                ps = conn.prepareStatement("UPDATE Faculty SET dept = ?, course_id = ? WHERE id = ?");
                ps.setString(1, data[0]);
                ps.setInt(2, Integer.parseInt(data[1]));
                ps.setInt(3, patronID);
                break;
            }

            if(fieldNo != 4) {
                ps.setString(1, enteredData);
                ps.setInt(2, patronID);
            }

            ps.executeUpdate();

            ps.close();

            db.closeConnection();
        } catch (SQLException e) {
            System.out.println("\nThere was an error with your input. Please try again.\n");
        }
    }

    public void checkOutRoom(Scanner in, int patronID) {
        Connection conn = db.getConnection();
        PreparedStatement ps1 = null, ps2 = null;
        ResultSet rs1 = null;

        Timestamp today = new Timestamp(System.currentTimeMillis());

        try {
            ps1 = conn.prepareStatement("SELECT * FROM BOOKED B WHERE "
                    + "B.PATRON_ID = ? AND B.STATUS = ? AND B.START_TIME < "
                    + "? AND B.END_TIME > ? ORDER BY B.ROOM_NUMBER");
            ps1.setInt(1, patronID);
            ps1.setString(2, "VALID");
            ps1.setTimestamp(3, today);
            ps1.setTimestamp(4, today);

            rs1 = ps1.executeQuery();

            ArrayList<Integer> avail_rooms = new ArrayList<Integer>();

            while(rs1.next()) {
                if(rs1.getString("CHECKED_OUT") == null && rs1.getString("CHECKED_IN") == null) {
                    avail_rooms.add(Integer.parseInt(rs1.getString("ROOM_NUMBER")));
                }
            }

            if(avail_rooms.size() == 0) {
                System.out.println("No reservations to check out.\n");
                return;
            }

            System.out.println("Rooms available for check out:");
            for(int room : avail_rooms) {
                System.out.println("\t" + room);
            }
            System.out.println();

            int rm;
            in.nextLine();
            System.out.print("Enter room number to check out: ");
            rm = in.nextInt();
            if(avail_rooms.indexOf(rm) == -1) {
                System.out.println("Room not available!\n");
                return;
            }

            ps2 = conn.prepareStatement("UPDATE BOOKED SET CHECKED_OUT = ?"
                    + " WHERE PATRON_ID = ? AND ROOM_NUMBER = ? AND STATUS = ? AND "
                    + "START_TIME < ? AND END_TIME > ?");
            ps2.setTimestamp(1, today);
            ps2.setInt(2, patronID);
            ps2.setInt(3, rm);
            ps2.setString(4, "VALID");
            ps2.setTimestamp(5, today);
            ps2.setTimestamp(6, today);

            ps2.executeUpdate();

            System.out.println("\nChecked out.\n");

            ps1.close();
            ps2.close();
            rs1.close();
            db.closeConnection();
        } catch (SQLException e) {
            System.out.println("\nThere was an error with your request. Please try again.\n");
        }
    }

    public void checkInRoom(Scanner in, int patronID) {
        Connection conn = db.getConnection();
        PreparedStatement ps1 = null, ps2 = null;
        ResultSet rs1 = null;

        Timestamp today = new Timestamp(System.currentTimeMillis());

        try {
            ps1 = conn.prepareStatement("SELECT * FROM BOOKED B WHERE "
                    + "B.PATRON_ID = ? AND B.STATUS = ? AND B.START_TIME < "
                    + "? AND B.END_TIME > ? ORDER BY B.ROOM_NUMBER");
            ps1.setInt(1, patronID);
            ps1.setString(2, "VALID");
            ps1.setTimestamp(3, today);
            ps1.setTimestamp(4, today);

            rs1 = ps1.executeQuery();

            ArrayList<Integer> avail_rooms = new ArrayList<Integer>();

            while(rs1.next()) {
                if(rs1.getString("CHECKED_OUT") != null && rs1.getString("CHECKED_IN") == null) {
                    avail_rooms.add(Integer.parseInt(rs1.getString("ROOM_NUMBER")));
                }
            }

            if(avail_rooms.size() == 0) {
                System.out.println("No reservations to check in.\n");
                return;
            }

            System.out.println("Rooms available for check in:");
            for(int room : avail_rooms) {
                System.out.println("\t" + room);
            }
            System.out.println();

            int rm;
            in.nextLine();
            System.out.print("Enter room number to check in: ");
            rm = in.nextInt();
            if(avail_rooms.indexOf(rm) == -1) {
                System.out.println("Room not available!\n");
                return;
            }

            ps2 = conn.prepareStatement("UPDATE BOOKED SET CHECKED_IN = ?"
                    + " WHERE PATRON_ID = ? AND ROOM_NUMBER = ? AND STATUS = ? AND "
                    + "START_TIME < ? AND END_TIME > ?");
            ps2.setTimestamp(1, today);
            ps2.setInt(2, patronID);
            ps2.setInt(3, rm);
            ps2.setString(4, "VALID");
            ps2.setTimestamp(5, today);
            ps2.setTimestamp(6, today);

            ps2.executeUpdate();

            System.out.println("\nChecked in.\n");

            ps1.close();
            ps2.close();
            rs1.close();
            db.closeConnection();
        } catch (SQLException e) {
            System.out.println("\nThere was an error with your request. Please try again.\n");
        }
    }

    @SuppressWarnings("deprecation")
    public void printAvailableRooms(Scanner in, int patronID, String date,
            int early_hour, int late_hour, int num_occupants, int library, int type) {
        Connection conn = db.getConnection();
        PreparedStatement ps1 = null, ps2 = null, ps3 = null;
        ResultSet rs1 = null, rs2 = null;

        //Check inputs
        if(late_hour <= early_hour) {
            System.out.println("\nHour range must be non-zero.\n");
            return;
        }

        if(late_hour - early_hour > 3) {
            System.out.println("\nHour range must be no more than three hours.\n");
            return;
        }

        if(library < 0 || library > 1) {
            System.out.println("\nInvalid library ID (0 for Hunt, 1 for Hill).\n");
            return;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
        Timestamp today = new Timestamp(System.currentTimeMillis() - 86400000);
        try {
            if(today.compareTo(sdf.parse(date)) > 0) {
                System.out.println("\nEnter a date today or in the future.\n");
                return;
            }
        } catch (ParseException e1) {
            System.out.println("\nThere was an error with your date input.\n");
            return;
        }

        //Deal with date
        String date_split[] = date.split("-"); //01-Nov-2015 0 1 2
        date_split[1] = convertMonthtoNumber(date_split[1]);

        String rm_type = null;
        //Type
        if(type == 0) {
            rm_type = "CONF";
        } else {
            rm_type = "STUDY";
        }

        //Continue
        try {
            //Get the list of rooms that match the input
            ps2 = conn.prepareStatement("SELECT R.ROOM_NUMBER FROM ROOMS R "
                    + "WHERE R.CAPACITY = ? AND R.LIBRARY_ID = ? AND R.ROOM_TYPE = ? ORDER BY "
                    + "R.ROOM_NUMBER");
            ps2.setInt(1, num_occupants);
            ps2.setInt(2, library);
            ps2.setString(3, rm_type);
            rs2 = ps2.executeQuery();

            ArrayList<Integer> avail_room_nums = new ArrayList<Integer>();
            while(rs2.next()) {
                avail_room_nums.add(rs2.getInt("ROOM_NUMBER"));
            }

            if(avail_room_nums.size() == 0) {
                System.out.println("\nThere are no rooms matching your criteria.\n");
                return;
            }

            //Now get the reservations for those rooms on this day
            ps1 = conn.prepareStatement("SELECT * FROM BOOKED B WHERE "
                    + "TO_CHAR(B.START_TIME,'DD-Mon-YYYY') = ?"
                    + "AND B.ROOM_NUMBER IN (SELECT R.ROOM_NUMBER FROM ROOMS "
                    + "R WHERE R.CAPACITY = ? AND R.LIBRARY_ID = ?) ORDER BY "
                    + "B.ROOM_NUMBER, B.START_TIME ASC");
            ps1.setString(1, date);
            ps1.setInt(2, num_occupants);
            ps1.setInt(3, library);

            rs1 = ps1.executeQuery();

            ArrayList<Reservation> reservations = new ArrayList<Reservation>();
            //Put all the reservations into the reservations list
            while(rs1.next()) {
                reservations.add(new Reservation(Integer.parseInt(rs1.getString("ROOM_NUMBER")),rs1.getTimestamp("START_TIME").getHours(),rs1.getTimestamp("END_TIME").getHours()));
            }

            System.out.println();

            boolean valid_times = false;
            int rm, from, to;
            do {
                //Print out available/reserved status of rooms
                boolean flag;
                for(int room : avail_room_nums) {
                    flag = false;
                    for(Reservation r : reservations) {
                        if(r.rm_no == room) {
                            flag = true;
                            System.out.println("Room " + room + " is RESERVED from " + r.start_time + " to " + r.end_time + ".");
                        }
                    }
                    if(!flag) {
                        System.out.println("Room " + room + " is AVAILABLE from " + early_hour + " to " + late_hour + ".");
                    }
                }

                //Accept user input
                System.out.print("\nWhat room number would you like to reserve? ");
                rm = Integer.parseInt(in.next());
                System.out.print("From (24 hour clock, hour number)? ");
                from = Integer.parseInt(in.next());
                System.out.print("To (24 hour clock, hour number)? ");
                to = Integer.parseInt(in.next());

                if(reservations.size() == 0) {
                    valid_times = true;
                }


                //error checking
                //Go through list of reservations for that room and see if it violates any of them
                boolean flag1 = false;
                for(Reservation r : reservations) {
                    if(r.rm_no == rm) {
                        flag1 = true;
                        if(from >= r.end_time || to <= r.start_time) {
                            //starts after reservation OR ends before reservation
                            valid_times = true;
                        } else if((from <= r.start_time && to >= r.start_time) ||
                                (from >= r.start_time && to <= r.end_time) ||
                                (from >= r.start_time && from <= r.end_time)) {
                            valid_times = false;
                            break;
                        } else {
                            valid_times = true;
                        }
                    }
                }

                if(!flag1) {
                    valid_times = true;
                }

                if(!valid_times) {
                    System.out.println("Invalid selection. Please try again.\n");
                }
            } while (!valid_times);


            //Insert booking
            Timestamp fromTS = Timestamp.valueOf(date_split[2] + "-" +
                    date_split[1] + "-" + date_split[0] + " " + from + ":00:00");
            Timestamp toTS = Timestamp.valueOf(date_split[2] + "-" +
                    date_split[1] + "-" + date_split[0] + " " + to + ":00:00");
            ps3 = conn.prepareStatement("INSERT INTO BOOKED "
                    + "(PATRON_ID,ROOM_NUMBER,START_TIME,END_TIME,"
                    + "CHECKED_OUT,CHECKED_IN,STATUS) VALUES (?,?,?,?,"
                    + "?,?,'VALID')");
            ps3.setInt(1, patronID);
            ps3.setInt(2, rm);
            ps3.setTimestamp(3, fromTS);
            ps3.setTimestamp(4, toTS);
            ps3.setNull(5, java.sql.Types.NULL);
            ps3.setNull(6, java.sql.Types.NULL);

            ps3.executeUpdate();
            System.out.println("\nReserved!\n");

            ps1.close();
            ps2.close();
            ps3.close();
            rs1.close();
            rs2.close();
            db.closeConnection();
        } catch (SQLException e) {
            System.out.println("\nThere was an error processing your room reservation. Please check your selection and try again.\n");
        }
    }

    private String convertMonthtoNumber(String month) {
        switch(month) {
        case "Jan":
            return "1";
        case "Feb":
            return "2";
        case "Mar":
            return "3";
        case "Apr":
            return "4";
        case "May":
            return "5";
        case "Jun":
            return "6";
        case "Jul":
            return "7";
        case "Aug":
            return "8";
        case "Sep":
            return "9";
        case "Oct":
            return "10";
        case "Nov":
            return "11";
        case "Dec":
            return "12";
        }
        return "0";
    }

    private class Reservation {
        public int rm_no;
        public int start_time;
        public int end_time;

        public Reservation(int rm_no, int start_time, int end_time) {
            this.rm_no = rm_no;
            this.start_time = start_time;
            this.end_time = end_time;
        }

        @Override
        public String toString() {
            return "Reservation [rm_no=" + rm_no + ", start_time=" + start_time
                    + ", end_time=" + end_time + "]";
        }
    }
}

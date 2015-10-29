package edu.ncsu.csc540.proj1.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import edu.ncsu.csc540.proj1.db.DbConnector;

public class Student {

    /**
     * Connection object
     */
    private DbConnector db = null;

    public Student() {
        this.db = new DbConnector();
    }

    public boolean isStudent(int id) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection conn = db.getConnection();
        boolean isStudent = false;

        try {
            ps = conn.prepareStatement("SELECT * FROM STUDENT WHERE ID=?");
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                isStudent = true;
            }

            ps.close();
            rs.close();
            db.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return isStudent;
    }

    public void printStudentProfile(int id) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection conn = db.getConnection();

        try {
            ps = conn.prepareStatement("SELECT S.id, S.phone, S.alt_phone, "
                    + "S.dob, S.sex, S.street, S.city, S.postcode, S.program, "
                    + "S.year, S.dept, P.fname, P.lname, P.country_name FROM STUDENT S, "
                    + "PATRON P WHERE S.id = P.id AND S.id = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();

            while(rs.next()) {
                System.out.println("First Name: " + rs.getString("fname"));
                System.out.println("Last Name: " + rs.getString("lname"));
                System.out.println("Nationality: " + rs.getString("country_name"));
                System.out.println("ID#: " + rs.getString("id"));
                System.out.println("Phone: " + rs.getString("phone"));
                System.out.println("Alternate Phone: " + rs.getString("alt_phone"));
                System.out.println("DOB: " + rs.getString("dob"));
                System.out.println("Sex: " + rs.getString("sex"));
                System.out.println("Street: " + rs.getString("street"));
                System.out.println("City: " + rs.getString("city"));
                System.out.println("Postcode: " + rs.getString("postcode"));
                System.out.println("Program: " + rs.getString("program"));
                System.out.println("Year: " + rs.getString("year"));
                System.out.println("Dept: " + rs.getString("dept"));
            }

            ps.close();
            rs.close();
            db.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateStudentProfile(int id, String data, int fieldNo) {
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
                ps = conn.prepareStatement("UPDATE Student SET phone = ? WHERE id = ?");
                break;
            case 4:
                ps = conn.prepareStatement("UPDATE Student SET alt_phone = ? WHERE id = ?");
                break;
            case 5:
                ps = conn.prepareStatement("UPDATE Student SET sex = ? WHERE id = ?");
                break;
            case 6:
                ps = conn.prepareStatement("UPDATE Student SET street = ? WHERE id = ?");
                break;
            case 7:
                ps = conn.prepareStatement("UPDATE Student SET city = ? WHERE id = ?");
                break;
            case 8:
                ps = conn.prepareStatement("UPDATE Student SET postcode = ? WHERE id = ?");
                break;
            case 9:
                ps = conn.prepareStatement("UPDATE Student SET program = ? WHERE id = ?");
                break;
            case 10:
                ps = conn.prepareStatement("UPDATE Student SET year = ? WHERE id = ?");
                break;
            case 11:
                ps = conn.prepareStatement("UPDATE Student SET dept = ? WHERE id = ?");
                break;
            }

            if(fieldNo == 10) {
                ps.setInt(1, Integer.parseInt(data));
            } else {
                ps.setString(1, data);
            }

            ps.setInt(2, id);

            ps.executeUpdate();

            ps.close();

            db.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void checkInToRoom(Scanner in, int patronID) {
        //select reserved rooms for this patron on today's date, allow check in
        //if STATUS == 'VALID'
        //Update table with start_time
    }

    public void checkOutRoom(Scanner in, int patronID) {
        //select reserved rooms for this patron on today's date that have been checked out (NOT NULL)
        //update table with end_time
    }

    @SuppressWarnings("deprecation")
    public void printAvailableRooms(Scanner in, int patronID, String date, int early_hour, int late_hour, int occupants, int lib) {
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

        if(lib < 0 || lib > 1) {
            System.out.println("\nInvalid library ID (0 for Hunt, 1 for Hill).\n");
            return;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
        Date today = new Date();
        try {
            Date input = sdf.parse(date);
            if(today.compareTo(input) > 0) {
                System.out.println("\nEnter a past date.\n");
                return;
            }
        } catch (ParseException e1) {
            System.out.println("\nThere was an error with your date input.\n");
            return;
        }

        //Deal with date
        String date_split[] = date.split("-"); //01-Nov-2015 0 1 2
        date_split[1] = convertMonthtoNumber(date_split[1]);

        //Continue
        try {
            //Get the list of rooms that match the input
            ps2 = conn.prepareStatement("SELECT R.ROOM_NUMBER FROM ROOMS R "
                    + "WHERE R.CAPACITY = ? AND R.LIBRARY_ID = ? AND "
                    + "R.ROOM_TYPE = 'STUDY' ORDER BY R.ROOM_NUMBER");
            ps2.setInt(1, occupants);
            ps2.setInt(2, lib);
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
                    + "R WHERE R.CAPACITY = ? AND R.LIBRARY_ID = ? AND "
                    + "R.ROOM_TYPE = 'STUDY') ORDER BY B.ROOM_NUMBER, B.START_TIME ASC");
            ps1.setString(1, date);
            ps1.setInt(2, occupants);
            ps1.setInt(3, lib);

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
                for(Reservation r : reservations) {
                    if(r.rm_no == rm) {
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
                    + "CHECKED_OUT,CHECKED_IN) VALUES (?,?,?,?,"
                    + "?,?)");
            ps3.setInt(1, patronID);
            ps3.setInt(2, rm);
            ps3.setTimestamp(3, fromTS);
            ps3.setTimestamp(4, toTS);
            ps3.setNull(5, java.sql.Types.NULL);
            ps3.setNull(6, java.sql.Types.NULL);

            ps3.executeUpdate();
            System.out.println("Reserved!\n");
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

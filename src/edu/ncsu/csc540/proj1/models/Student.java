package edu.ncsu.csc540.proj1.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
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

    @SuppressWarnings("deprecation")
    public void printAvailableRooms(Scanner in, int patronID, String date, int early_hour, int late_hour, int occupants, int lib) {
        Connection conn = db.getConnection();
        PreparedStatement ps1 = null, ps2 = null, ps3 = null;
        ResultSet rs1 = null, rs2 = null;

        if(late_hour <= early_hour) {
            System.out.println("Hour range must be non-zero.");
            return;
        }

        //Check inputs
        if(late_hour - early_hour > 3) {
            System.out.println("Hour range must be no more than three hours.");
            return;
        }

        if(lib < 0 || lib > 1) {
            System.out.println("Invalid library ID (0 for Hunt, 1 for Hill).");
            return;
        }

        //Continue
        try {
            ps2 = conn.prepareStatement("SELECT R.ROOM_NUMBER FROM ROOMS R WHERE R.CAPACITY = ? AND R.LIBRARY_ID = ? AND R.ROOM_TYPE = 'STUDY'");
            ps2.setInt(1, occupants);
            ps2.setInt(2, lib);
            rs2 = ps2.executeQuery();

            ArrayList<Integer> avail_room_nums = new ArrayList<Integer>();
            while(rs2.next()) {
                avail_room_nums.add(rs2.getInt("ROOM_NUMBER"));
            }

            ps1 = conn.prepareStatement("SELECT * FROM BOOKED B WHERE "
                    + "TO_CHAR(B.START_TIME,'DD-Mon-YYYY') = ?"
                    + "AND B.ROOM_NUMBER IN (SELECT R.ROOM_NUMBER FROM ROOMS "
                    + "R WHERE R.CAPACITY = ? AND R.LIBRARY_ID = ? AND "
                    + "R.ROOM_TYPE = 'STUDY') ORDER BY B.ROOM_NUMBER");
            ps1.setString(1, date);
            ps1.setInt(2, occupants);
            ps1.setInt(3, lib);

            rs1 = ps1.executeQuery();

            int search_hr_start = early_hour, search_hr_end = late_hour;
            int reserve_start, reserve_end;
            String room_number = null;
            ArrayList<Reservation> reservations = new ArrayList<Reservation>();

            while(rs1.next()) {
                reserve_start = rs1.getTimestamp("START_TIME").getHours();
                reserve_end = rs1.getTimestamp("END_TIME").getHours();
                room_number = rs1.getString("ROOM_NUMBER");
                reservations.add(new Reservation(Integer.parseInt(room_number),reserve_start,reserve_end));
            }

            int valid_start_time = search_hr_start, valid_end_time = search_hr_end;
            Reservation r;

            for(int room : avail_room_nums) {
                for(int i = 0; i < reservations.size(); i++) {
                    r = reservations.get(i);
                    if(r.rm_no == room) {
                        if((search_hr_start <= r.start_time && search_hr_end <= r.start_time) || (search_hr_start >= r.end_time && search_hr_end >= r.end_time)) {
                            valid_start_time = search_hr_start;
                            valid_end_time = search_hr_end;
                        } else if(search_hr_start <= r.start_time && search_hr_end >= r.start_time) {
                            valid_start_time = search_hr_start;
                            valid_end_time = r.start_time;
                        } else if(search_hr_start >= r.start_time && search_hr_end > r.start_time) {
                            valid_start_time = r.end_time;
                            valid_end_time = search_hr_end;
                        }
                        if(valid_start_time != valid_end_time) {
                            System.out.print("Room " + room + " can be reserved from " + valid_start_time + " to " + valid_end_time + ". Reserve now? (y/n) ");
                            String resp = in.next();
                            if(resp.equalsIgnoreCase("y")) {
                                //insert
                                System.out.println("Reserved!");
                                return;
                            }
                        }
                    }
                }

                if(reservations.size() == 0) {
                    System.out.print("Room " + room + " can be reserved from " + valid_start_time + " to " + valid_end_time + ". Reserve now? (y/n) ");
                    String resp = in.next();
                    if(resp.equalsIgnoreCase("y")) {
                        //insert
                        System.out.println("Reserved!");
                        return;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
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


        /* (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return "Reservation [rm_no=" + rm_no + ", start_time=" + start_time
                    + ", end_time=" + end_time + "]";
        }
    }
}

package edu.ncsu.csc540.proj1.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
                    + "S.year, P.fname, P.lname, P.country_name FROM STUDENT S, "
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
            }

            ps.close();
            rs.close();
            db.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

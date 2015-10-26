package edu.ncsu.csc540.proj1.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import edu.ncsu.csc540.proj1.db.DbConnector;

public class Faculty {
    /**
     * Connection object
     */
    private DbConnector db = null;

    public Faculty(){
        this.db = new DbConnector();
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
            e.printStackTrace();
        }
    }
}

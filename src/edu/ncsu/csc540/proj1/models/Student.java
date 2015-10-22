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
    
    public Student(){
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
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
        	try {
				ps.close();
				rs.close();
				db.closeConnection();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
        return isStudent;
    }
}

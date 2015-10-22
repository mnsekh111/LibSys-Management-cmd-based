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
        return isFaculty;
    }
}

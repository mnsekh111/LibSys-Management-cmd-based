package edu.ncsu.csc540.proj1.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import edu.ncsu.csc540.proj1.db.DbConnector;

public class Notifications {
    /**
     * Connection object
     */
    private DbConnector db = null;

    public Notifications(){
        this.db = new DbConnector();
    }
    
    public void getnotifications(int patronID){
    	Connection conn = db.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
			ps = conn.prepareStatement("SELECT * FROM REMINDERS WHERE PATRON_ID = ?");
			ps.setInt(1, patronID);
	        rs = ps.executeQuery();
	        while(rs.next()){
	        	System.out.print(rs.getString("MESSAGE"));
	        	System.out.println(" | "+rs.getDate("TIME_SENT"));
	        }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
            db.closeConnection();
        }
        
    }
    
}

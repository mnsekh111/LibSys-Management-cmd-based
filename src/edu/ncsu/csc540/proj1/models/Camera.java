package edu.ncsu.csc540.proj1.models;

import java.sql.CallableStatement;
import oracle.jdbc.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import edu.ncsu.csc540.proj1.db.DbConnector;
import oracle.jdbc.internal.OracleTypes;
import oracle.jdbc.oracore.OracleType;

public class Camera {

	 /**
     * Connection object
     */
    private DbConnector db = null;

    public Camera(){
        this.db = new DbConnector();
    }
    
    public void getAvailableCameras(int selectedWeek){
    	Connection conn = db.getConnection();
    	CallableStatement csmt = null;
    	int counter = 1;
    	
    	try {
			csmt =  conn.prepareCall("{call CAMERA_AVAILABLE(?,?)}");
			csmt.setInt(counter++, selectedWeek);
			csmt.registerOutParameter(counter, OracleTypes.CURSOR);
			csmt.execute();
			ResultSet rs = ((OracleCallableStatement)csmt).getCursor(counter);
			while(rs.next()){
				System.out.print(rs.getInt("id"));
				System.out.print(" | "+rs.getString("make"));
				System.out.print(" | "+rs.getString("model"));
				System.out.print(" | "+rs.getString("config"));
				System.out.print(" | "+rs.getString("lid"));
				System.out.println(" | "+rs.getString("memory"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			db.closeConnection();
		}
    }
    
    public boolean book_camera(int patron_id, int camera_id){
    	
    	Connection conn = db.getConnection();
    	CallableStatement csmt = null;
    	int counter = 1;
    	try {
			csmt =  conn.prepareCall("{call CAMERA_BOOK(?,?,?,?)}");
			csmt.setInt(counter++, patron_id);
			csmt.setInt(counter++, camera_id);
			csmt.setDate(counter++, new Date(System.currentTimeMillis()));
			//csmt.setDate(counter++, new Date(System.currentTimeMillis()+604800000));
			csmt.registerOutParameter(counter, Types.INTEGER);
			
			csmt.execute();
			System.out.println(csmt.getInt(counter));
			if(csmt.getInt(counter) == 1){
				return true;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			db.closeConnection();
		}
    	
    	
    	return false;
    }
    
    public void getCheckedOut(int patronId){
    	Connection conn = db.getConnection();
    	CallableStatement csmt = null;
    	int counter = 1;
    	
    	try {
			csmt =  conn.prepareCall("{call CAMERA_CHECKED_OUT(?,?)}");
			csmt.setInt(counter++, patronId);
			csmt.registerOutParameter(counter, OracleTypes.CURSOR);
			csmt.execute();
			ResultSet rs = ((OracleCallableStatement)csmt).getCursor(counter);
			while(rs.next()){
				System.out.print(rs.getInt("id"));
				System.out.print(" | "+rs.getString("make"));
				System.out.print(" | "+rs.getString("model"));
				System.out.print(" | "+rs.getString("config"));
				System.out.print(" | "+rs.getString("lid"));
				System.out.println(" | "+rs.getString("memory"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			db.closeConnection();
		}
    }
    
    public void returnCamera(int idToReturn){
    	Connection conn = db.getConnection();
    	CallableStatement csmt = null;
    	int counter = 1;
    	int status = 0;
			try {
				csmt =  conn.prepareCall("{call CAMERA_RETURN(?, ?)}");
				csmt.setInt(counter++, idToReturn);
				csmt.registerOutParameter(counter, Types.INTEGER);
				csmt.execute();
				status = csmt.getInt(counter);
				if(status==1){
					System.out.println("Return done successfully");
				}else{
					System.out.println("Return failed");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	
    }
    
    public void insertIntoCamQueue(int camid, int week, int patronId){
    	Connection conn = db.getConnection();
    	CallableStatement csmt = null;
    	int counter = 1;
    	int queue_number = 0;
    	try {
			csmt =  conn.prepareCall("{call CAMERA_REQ(?, ?, ?, ?)}");
			csmt.setInt(counter++, patronId);
			csmt.setInt(counter++, camid);
			csmt.setInt(counter++, week);
			csmt.registerOutParameter(counter, Types.INTEGER);
			csmt.execute();
			queue_number = csmt.getInt(counter);
			System.out.println("Queue Number: "+queue_number);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
    }
        
}

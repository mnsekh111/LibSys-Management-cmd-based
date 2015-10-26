package edu.ncsu.csc540.proj1.models;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.sql.Date;

import edu.ncsu.csc540.proj1.db.DbConnector;

public class Camera {

	 /**
     * Connection object
     */
    private DbConnector db = null;

    public Camera(){
        this.db = new DbConnector();
    }
    
    public boolean book_camera(int patron_id, int camera_id){
    	
    	Connection conn = db.getConnection();
    	CallableStatement csmt = null;
    	int counter = 1;
    	try {
			csmt =  conn.prepareCall("{call BOOK_CAMERA(?,?,?,?)}");
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
    
        
}

package edu.ncsu.csc540.proj1.models;

import java.sql.CallableStatement;
import oracle.jdbc.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.sql.Date;
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
    
    public void getAvailableCameras(Date date){
    	Connection conn = db.getConnection();
    	CallableStatement csmt = null;
    	int counter = 1;
    	
    	try {
			csmt =  conn.prepareCall("{call CAMERA_AVAILABLE(?,?)}");
			csmt.setDate(counter++, date);
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
    
        
}

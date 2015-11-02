package edu.ncsu.csc540.proj1.models;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import edu.ncsu.csc540.proj1.db.DbConnector;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.internal.OracleTypes;

public class Due {
     /**
     * Connection object
     */
    private DbConnector db = null;

    public Due(){
        this.db = new DbConnector();
    }

    public int getFines(int patronId){
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection conn = db.getConnection();
        int counter = 1;
        int sum = 0;
        try {
            ps = conn.prepareStatement("SELECT * FROM ALL_FINES WHERE PATRON_ID=?");
            ps.setInt(counter++, patronId);
            rs = ps.executeQuery();

            while(rs.next()){
                System.out.print(rs.getString("ID"));
                System.out.print(" | "+rs.getString("DESCRIPTION"));
                System.out.println(" | "+rs.getInt("AMOUNT"));
                sum += rs.getInt("AMOUNT");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            db.closeConnection();
        }
        return sum;
    }

    public void payAllFines(int patronId){
        Connection conn = db.getConnection();
        CallableStatement csmt = null;
        int counter = 1;
        int output = 0 ;
        try {
            csmt =  conn.prepareCall("{call PAY_ALL_FINES(?,?)}");
            csmt.setInt(counter++, patronId);
            csmt.registerOutParameter(counter, Types.INTEGER);
            csmt.execute();
            output = csmt.getInt(counter);
            if(output == 0){
                System.out.println("------- Payment failed ---------");
            }else{
                System.out.println("------- Fine successfully paid ---------");
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            db.closeConnection();
        }
    }

    public void payFine(String fineId, int patronId){
        Connection conn = db.getConnection();
        CallableStatement csmt = null;
        int counter = 1;
        int output = 0 ;
        try {
            csmt =  conn.prepareCall("{call PAY_FINE(?,?,?)}");
            csmt.setString(counter++, fineId);
            csmt.setInt(counter++, patronId);
            csmt.registerOutParameter(counter, Types.INTEGER);
            csmt.execute();
            output = csmt.getInt(counter);
            if(output == 0){
                System.out.println("------- Payment failed ---------");
            }else{
                System.out.println("------- Fine successfully paid ---------");
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            db.closeConnection();
        }
    }

}

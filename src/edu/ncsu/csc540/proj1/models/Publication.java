package edu.ncsu.csc540.proj1.models;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import edu.ncsu.csc540.proj1.db.DbConnector;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

public class Publication {
    private DbConnector db = null;
    private Statement stmt = null;
    private Connection conn = null;
    private CallableStatement csmt = null;

    public Publication() {
        this.db = new DbConnector();
        this.conn = db.getConnection();
        try {
            this.stmt = conn.createStatement();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    /**
     * Function to get All publications which are books
     */
    public void getAllBooks() {
        ResultSet rs = null;

        try {
            stmt = conn.createStatement();

            rs = stmt.executeQuery("SELECT p.title,p.id,p.year_of_pub,b.edition,b.publisher"
                    + " FROM PUB_BOOK b , Publications p where p.id = b.isbn");

            while (rs.next()) {
                System.out.println(
                        rs.getString("id") + "\t|" + rs.getString("title") + "\t|" + rs.getInt("year_of_pub")
                        +"\t|"+rs.getString("publisher"));
            }

            stmt.close();
            rs.close();

        } catch (SQLException ex) {
            System.err.println(ex.getLocalizedMessage());
        }
    }

    /**
     * Function to get All publications which are conference papers
     */
    public void getAllConferencePapers() {
        ResultSet rs = null;

        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM Pub_ConferencePapers c, Publications p where p.id = c.conf_num");

            if (rs != null) {
                while (rs.next()) {
                    System.out.println(
                            rs.getString("id") + "\t|" + rs.getString("title") + "\t|" + rs.getInt("year_of_pub")
                            +"\t|"+rs.getString("conf_num")+"\t|"+rs.getString("confName"));
                }
            }

            stmt.close();
            rs.close();

        } catch (SQLException ex) {
            System.err.println(ex.getLocalizedMessage());
        }
    }

    /**
     * Function to get All publications which are Journals
     */
    public void getAllJournals() {
        ResultSet rs = null;

        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM Pub_Journal j, Publications p where p.id = j.issn");

            if (rs != null) {
                while (rs.next()) {
                    System.out.println(rs.getString("id")+ "\t|" + rs.getString("title") + "\t|" + rs.getInt("year_of_pub"));
                }
            }

            stmt.close();
            rs.close();

        } catch (SQLException ex) {
            System.err.println(ex.getLocalizedMessage());
        }
    }

    /**
     * Function to get All publications
     */
    public void getAll(){
        getAllBooks();
        getAllConferencePapers();
        getAllJournals();
    }

    /**
     * Function to get copies of pubId
     * @param pubId Publication Id
     */
    public void getCopies(String pubId){

        int counter = 1;
        try {
            csmt =  conn.prepareCall("{call COPIES_AVAILABLE(?,?)}");
            csmt.setString(counter++, pubId);
            csmt.registerOutParameter(counter, OracleTypes.CURSOR);
            csmt.execute();
            ResultSet rs = ((OracleCallableStatement)csmt).getCursor(counter);
            while(rs.next()){
                System.out.print(rs.getInt("id"));
                System.out.print(" | "+rs.getString("pid"));
                System.out.print(" | "+rs.getString("title"));
                System.out.print(" | "+rs.getString("copy_type"));
                System.out.print(" | "+rs.getString("status"));
                System.out.println();
            }

            csmt.close();
            rs.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * This function tries to checkout copyId fo patronId
     * @param patronId Patron Id
     * @param copyId  Copy Id
     */
    public void checkOutCopy(int patronId, int copyId){
        int counter = 1;
        CallableStatement csmt;
        try {
            csmt =  conn.prepareCall("{call PUB_CHECK_OUT(?,?,?)}");
            csmt.setInt(counter++, patronId);
            csmt.setInt(counter++, copyId);
            csmt.registerOutParameter(counter, OracleTypes.VARCHAR);
            csmt.execute();
            String output = ((OracleCallableStatement)csmt).getString(counter);
            System.out.println("\n"+output + "\n");

            csmt.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * This function tries to return copy with id copyId
     * @param copyId Copy Id
     */
    public void returnCopy(int copyId){
        int counter = 1;
        try {
            csmt =  conn.prepareCall("{call PUB_RETURN(?,?)}");
            csmt.setInt(counter++, copyId);
            csmt.registerOutParameter(counter, OracleTypes.VARCHAR);
            csmt.execute();
            String output = ((OracleCallableStatement)csmt).getString(counter);
            System.out.println("\n"+output + "\n");

            csmt.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * This function tries to reserve a copy for faculty
     * @param copyId Copy Id
     * @param facultyId  Faculty Id
     */
    public void reserveCopy(int copyId,int facultyId){
        int counter = 1;
        try {
            csmt =  conn.prepareCall("{?=call reserve_copy(?,?)}");
            csmt.registerOutParameter(counter++, OracleTypes.VARCHAR);
            csmt.setInt(counter++, copyId);
            csmt.setInt(counter,facultyId);
            csmt.execute();
            String output = ((OracleCallableStatement)csmt).getString(1);
            System.out.println("\n"+output + "\n");

            csmt.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void getPubRequests(int patronId){
        int counter = 1;
        try {
            csmt =  conn.prepareCall("{? = call pub_get_res_requests(?)}");
            csmt.registerOutParameter(counter++, OracleTypes.CURSOR);
            csmt.setInt(counter, patronId);
            csmt.execute();
            ResultSet rs = ((OracleCallableStatement)csmt).getCursor(1);
            counter = 1;
            while(rs.next()){
                System.out.print(rs.getInt("cid"));
                System.out.print(" | "+rs.getString("title"));
                System.out.print(" | "+rs.getString("copy_type"));
                System.out.println();
            }

            csmt.close();
            rs.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Get list of checked out items
     * @param patronId
     */
    public void getCheckedout(int patronId){
        int counter = 1;
        try {
            csmt =  conn.prepareCall("{? = call pub_get_checked_out(?)}");
            csmt.registerOutParameter(counter++, OracleTypes.CURSOR);
            csmt.setInt(counter, patronId);
            csmt.execute();
            ResultSet rs = ((OracleCallableStatement)csmt).getCursor(1);
            while(rs.next()){
                counter = 1;
                System.out.print(rs.getInt(counter++));
                System.out.print(" | "+rs.getString(counter++));
                System.out.print(" | "+rs.getString(counter++));
                System.out.print(" | "+rs.getString(counter++));
                System.out.print(" | "+rs.getString(counter++));
                System.out.println(" | "+rs.getString(counter++));
            }

            csmt.close();
            rs.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Closes connections
     */
    public void cleanUp() {
        try {
            this.conn.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        this.db.closeConnection();
    }

}

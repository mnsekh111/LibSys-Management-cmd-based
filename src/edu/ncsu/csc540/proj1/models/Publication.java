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

    public Publication() {
        this.db = new DbConnector();
        this.conn = db.getConnection();
        try {
            this.stmt = conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

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

    public void getAll(){
        getAllBooks();
        getAllConferencePapers();
        getAllJournals();
    }

    public void getCopies(String pubId){
        CallableStatement csmt = null;
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

    public void checkOutCopy(int patronId, int copyId){
        CallableStatement csmt = null;
        int counter = 1;
        try {
            csmt =  conn.prepareCall("{call PUB_CHECK_OUT(?,?,?)}");
            csmt.setInt(counter++, patronId);
            csmt.setInt(counter++, copyId);
            csmt.registerOutParameter(counter, OracleTypes.VARCHAR);
            csmt.execute();
            String output = ((OracleCallableStatement)csmt).getString(counter);
            System.out.println(output + "\n");

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


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

package edu.ncsu.csc540.proj1.models;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import edu.ncsu.csc540.proj1.db.DbConnector;

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
			// TODO Auto-generated catch block
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

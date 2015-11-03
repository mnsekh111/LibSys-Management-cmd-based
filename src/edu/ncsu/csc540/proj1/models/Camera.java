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

public class Camera {

	/**
	 * Connection object
	 */
	private DbConnector db = null;

	public Camera() {
		this.db = new DbConnector();
	}

	public void getAvailableCameras(int selectedWeek) {
		Connection conn = db.getConnection();
		CallableStatement csmt = null;
		int counter = 1;

		try {
			csmt = conn.prepareCall("{call CAMERA_AVAILABLE(?,?)}");
			csmt.setInt(counter++, selectedWeek);
			csmt.registerOutParameter(counter, OracleTypes.CURSOR);
			csmt.execute();
			ResultSet rs = ((OracleCallableStatement) csmt).getCursor(counter);
			while (rs.next()) {
				System.out.print(rs.getInt("id"));
				System.out.print(" | " + rs.getString("make"));
				System.out.print(" | " + rs.getString("model"));
				System.out.print(" | " + rs.getString("config"));
				System.out.print(" | " + rs.getString("lid"));
				System.out.println(" | " + rs.getString("memory"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			db.closeConnection();
		}
	}

	public boolean book_camera(int patron_id, int queue_id) {

		Connection conn = db.getConnection();
		CallableStatement csmt = null;
		int counter = 1;
		try {
			csmt = conn.prepareCall("{call CAMERA_BOOK(?,?,?)}");
			csmt.setInt(counter++, patron_id);
			csmt.setInt(counter++, queue_id);
			csmt.registerOutParameter(counter, Types.INTEGER);

			csmt.execute();
			System.out.println(csmt.getInt(counter));
			if (csmt.getInt(counter) == 1) {
				System.out.println("------ The camera has been checked out successfully ------");
			} else {
				System.out.println("------ Check out failed ------");
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			db.closeConnection();
		}

		return false;
	}

	public void getCamInQueue(int patronId) {
		Connection conn = db.getConnection();
		ResultSet rs1 = null;
		PreparedStatement ps1;
		try {
			ps1 = conn
					.prepareStatement("SELECT * FROM cam_queue cq, Cameras c WHERE cq.cam_id = c.id and patron_id = ?");
			ps1.setInt(1, patronId);
			rs1 = ps1.executeQuery();

			while (rs1.next()) {
				System.out.print(rs1.getInt("cam_id")+" | ");
				System.out.print(rs1.getString("make")+" | ");
				System.out.print(rs1.getString("model")+" | \n");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void getCheckedOut(int patronId) {
		Connection conn = db.getConnection();
		CallableStatement csmt = null;
		int counter = 1;

		try {
			csmt = conn.prepareCall("{call CAMERA_CHECKED_OUT(?,?)}");
			csmt.setInt(counter++, patronId);
			csmt.registerOutParameter(counter, OracleTypes.CURSOR);
			csmt.execute();
			ResultSet rs = ((OracleCallableStatement) csmt).getCursor(counter);
			while (rs.next()) {
				System.out.print(rs.getInt("id"));
				System.out.print(" | " + rs.getString("make"));
				System.out.print(" | " + rs.getString("model"));
				System.out.print(" | " + rs.getString("config"));
				System.out.print(" | " + rs.getString("lid"));
				System.out.println(" | " + rs.getString("memory"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			db.closeConnection();
		}
	}

	public void returnCamera(int idToReturn) {
		Connection conn = db.getConnection();
		CallableStatement csmt = null;
		int counter = 1;
		int status = 0;
		try {
			csmt = conn.prepareCall("{call CAMERA_RETURN(?, ?)}");
			csmt.setInt(counter++, idToReturn);
			csmt.registerOutParameter(counter, Types.INTEGER);
			csmt.execute();
			status = csmt.getInt(counter);
			if (status == 1) {
				System.out.println("Return done successfully");
			} else {
				System.out.println("Return failed");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void insertIntoCamQueue(int camid, int week, int patronId) {
		Connection conn = db.getConnection();
		CallableStatement csmt = null;
		int counter = 1;
		int queue_number = 0;
		try {
			csmt = conn.prepareCall("{call CAMERA_REQ(?, ?, ?, ?)}");
			csmt.setInt(counter++, patronId);
			csmt.setInt(counter++, camid);
			csmt.setInt(counter++, week);
			csmt.registerOutParameter(counter, Types.INTEGER);
			csmt.execute();
			queue_number = csmt.getInt(counter);
			System.out.println("Queue Number: " + queue_number);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void getQueuedList(int patronId) {
		Connection conn = db.getConnection();
		CallableStatement csmt = null;
		int counter = 1;
		int rowCount = 0;
		try {
			csmt = conn.prepareCall("{call CAMERA_4_CHKOUT(?,?)}");
			csmt.setInt(counter++, patronId);
			csmt.registerOutParameter(counter, OracleTypes.CURSOR);
			csmt.execute();
			ResultSet rs = ((OracleCallableStatement) csmt).getCursor(counter);
			while (rs.next()) {
				System.out.print(rs.getInt("id"));
				System.out.print(" | " + rs.getString("make"));
				System.out.print(" | " + rs.getString("model"));
				System.out.print(" | " + rs.getString("config"));
				System.out.print(" | " + rs.getString("lid"));
				System.out.println(" | " + rs.getString("memory"));
				rowCount = 1;
			}
			if (rowCount == 0) {
				System.out.println("----------- You don't have any active request to be checked out. --------");
			} else {
				System.out.println("\tX. Select Id to checkout.");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			db.closeConnection();
		}
	}

}

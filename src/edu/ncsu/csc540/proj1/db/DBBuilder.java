package edu.ncsu.csc540.proj1.db;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Builds the database for the project
 * @author Dixon Crews
 */
public class DBBuilder {

    /**
     * Path to the createTables.sql file
     */
    private static String[] createTablesPath = {
            "sql"+File.separator+"Sequences"+File.separator+"checks_out_id.sql",
            "sql"+File.separator+"Sequences"+File.separator+"fines_id.sql",
            "sql"+File.separator+"createTables.sql",
            "sql"+File.separator+"bharathi.sql",
            "sql"+File.separator+"new_table_and_edits.sql",
            "sql"+File.separator+"Camera"+File.separator+"CAMERA_4_CHKOUT.sql",
            "sql"+File.separator+"Camera"+File.separator+"CAMERA_AVAILABLE.sql",
            "sql"+File.separator+"Camera"+File.separator+"CAMERA_BOOK.sql",
            "sql"+File.separator+"Camera"+File.separator+"CAMERA_CHECKED_OUT.sql",
            "sql"+File.separator+"Camera"+File.separator+"CAMERA_FINES.sql",
            "sql"+File.separator+"Camera"+File.separator+"CAMERA_REQ.sql",
            "sql"+File.separator+"Camera"+File.separator+"CAMERA_RETURN.sql",
            "sql"+File.separator+"Camera"+File.separator+"CAMERA_SEND_ALERT_1.sql",
            "sql"+File.separator+"Camera"+File.separator+"CAMERA_SEND_ALERT_2.sql",
            "sql"+File.separator+"Fines"+File.separator+"PAY_ALL_FINES.sql",
            "sql"+File.separator+"Fines"+File.separator+"PAY_FINE.sql",
            "sql"+File.separator+"monthly_outstanding"+File.separator+"FINES_MONTHLY_OUTSTANDING.sql",
            "sql"+File.separator+"Rooms"+File.separator+"ROOMS_INVALIDATE.sql",

            "sql"+File.separator+"Patron"+File.separator+"has_student_taken_course.sql",
            "sql"+File.separator+"Patron"+File.separator+"patron_type.sql",

            "sql"+File.separator+"Publications"+File.separator+"add_to_pub_queue.sql",
            "sql"+File.separator+"Publications"+File.separator+"can_renew.sql",
            "sql"+File.separator+"Publications"+File.separator+"check_out_next_in_queue.sql",
            "sql"+File.separator+"Publications"+File.separator+"compute_fines.sql",
            "sql"+File.separator+"Publications"+File.separator+"copies_available.sql",
            "sql"+File.separator+"Publications"+File.separator+"is_copy_reserved.sql",
            "sql"+File.separator+"Publications"+File.separator+"pub_check_out.sql",
            "sql"+File.separator+"Publications"+File.separator+"pub_get_checked_out_history.sql",
            "sql"+File.separator+"Publications"+File.separator+"pub_get_checked_out.sql",
            "sql"+File.separator+"Publications"+File.separator+"pub_get_res_requests.sql",
            "sql"+File.separator+"Publications"+File.separator+"pub_return.sql",
            "sql"+File.separator+"Publications"+File.separator+"pub_send_reminders.sql",
            "sql"+File.separator+"Publications"+File.separator+"publication_type.sql",
            "sql"+File.separator+"Publications"+File.separator+"renew.sql",
            "sql"+File.separator+"Publications"+File.separator+"reserve_copy.sql",

            "sql"+File.separator+"Scheduler - Jobs"+File.separator+"job_pub_fines_incrementer.sql",
            "sql"+File.separator+"Scheduler - Jobs"+File.separator+"job_pub_reminder.sql",

            "sql"+File.separator+"View"+File.separator+"copies_pub_screen_view.sql",
            "sql"+File.separator+"View"+File.separator+"pub_check_out_screen_view_hist.sql",
            "sql"+File.separator+"View"+File.separator+"pub_check_out_screen_view.sql",
            "sql"+File.separator+"View"+File.separator+"pub_res_req_screen_view.sql",
            "sql"+File.separator+"bharathi_schedulers.sql",
            "sql"+File.separator+"Final_Queries"+File.separator+"Queries.sql",
            };

    /**
     * Path to the dropTables.sql file
     */
    private static String dropTablesPath = "sql/dropTables.sql";

    /**
     * Path to the test_data.sql file
     */
    private static String testDataPath = "sql/test_data.sql";

    /**
     * Path to the sample_data.sql file
     */
    private static String sampleDataPath = "sql/sample_data.sql";
    
    /**
     * Path to the startJobs.sql file
     */
    private static String startJobsPath = "sql/startJobs.sql";

    /**
     * DbConnector object to get a database connection
     */
    private static DbConnector conn;

    /**
     * Connection object
     */
    private static Connection connect;

    /**
     * Main method to build database
     * @param args
     */
    public static void main(String[] args) {
        conn = new DbConnector();
        connect = conn.getConnection();

        System.out.println("Building database...");
        long startTime = System.currentTimeMillis();

        System.out.println("Dropping tables...");
        executeSQL(dropTablesPath);

        System.out.println("Creating tables...");
        for(int i = 0; i < createTablesPath.length; i++) {
            executeSQL(createTablesPath[i]);
        }

        //System.out.println("Inserting test data...");
        //executeSQL(testDataPath);

        System.out.println("Inserting sample data...");
        executeSQL(sampleDataPath);
        
        System.out.println("Run Procedures data...");
        executeJobsOnce(startJobsPath);

        long endTime = System.currentTimeMillis();
        System.out.println("Operation completed in " + (endTime - startTime) + "ms.");

        conn.closeConnection();
    }

    public static void executeSQL(String path) {
        ArrayList<String> queryList = parseSQLFile(path);


        for(String sql : queryList) {
            System.out.println(sql);
            try {
                java.sql.Statement stmt = connect.createStatement();
                //System.out.println(sql);
                stmt.execute(sql);
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
    
    public static void executeJobsOnce(String path){
    	ArrayList<String> queryList = parseSQLFile(path);


        for(String sql : queryList) {
            System.out.println(sql);
            try {
            	CallableStatement csmt  = connect.prepareCall(sql);
                //System.out.println(sql);
            	csmt.execute();
            	csmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Reads in an SQL file and parses it to split up queries. Returns the list
     * of queries as an ArrayList<String>
     * @param path path to the SQL file
     * @return an ArrayList<String> with one query per entry
     */
    public static ArrayList<String> parseSQLFile(String path) {
        ArrayList<String> queryList = new ArrayList<String>();

        //Using the delimeter of --- in createTables.sql
        boolean useDashDelimeter = Arrays.asList(createTablesPath).contains(path);
        boolean useDashDelimeter1 = startJobsPath.equals(path);

        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File(path)));
            String line = "";
            String currentQuery = "";
            while((line = reader.readLine()) != null) {
                if(useDashDelimeter || useDashDelimeter1) {
                    if(line.endsWith("end;---")) {
                        currentQuery +="\r\n"+ line.substring(0, line.length() - 3);
                        queryList.add(currentQuery);
                        currentQuery = "";
                    } else if(line.endsWith("---")) {
                        currentQuery +="\r\n"+ line.substring(0, line.length() - 4);
                        queryList.add(currentQuery);
                        currentQuery = "";
                    } else {
                        currentQuery +="\r\n"+ line;
                    }
                } else {
                    for(int i = 0; i < line.length(); i++) {
                        if(line.charAt(i) == ';') {
                            queryList.add(currentQuery);
                            currentQuery = "";
                        } else {
                            currentQuery += line.charAt(i);
                        }
                    }
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return queryList;
    }

}
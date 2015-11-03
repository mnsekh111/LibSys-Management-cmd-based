package edu.ncsu.csc540.proj1.db;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
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
    private static String[] createTablesPath = {"sql/createTables.sql",
            "sql/bharathi.sql",
            "sql/Camera/CAMERA_4_CHKOUT.sql",
            "sql/Camera/CAMERA_AVAILABLE.sql",
            "sql/Camera/CAMERA_BOOK.sql",
            "sql/Camera/CAMERA_CHECKED_OUT.sql",
            "sql/Camera/CAMERA_FINES.sql",
            "sql/Camera/CAMERA_REQ.sql",
            "sql/Camera/CAMERA_RETURN.sql",
            "sql/Camera/CAMERA_SEND_ALERT_1.sql",
            "sql/Camera/CAMERA_SEND_ALERT_2.sql",
            "sql/Fines/PAY_ALL_FINES.sql",
            "sql/Fines/PAY_FINE.sql",
            "sql/monthly_outstanding/FINES_MONTHLY_OUTSTANDING.sql",
            "sql/Rooms/ROOMS_INVALIDATE.sql",
            
            "sql/Patron/has_student_taken_course.sql",
            "sql/Patron/patron_type.sql",
            
            "sql/Publications/add_to_pub_queue.sql",
            "sql/Publications/can_renew.sql",
            "sql/Publications/check_out_next_in_queue.sql",
            "sql/Publications/compute_fines.sql",
            "sql/Publications/copies_available.sql",
            "sql/Publications/is_copy_reserved.sql",
            "sql/Publications/pub_check_out.sql",
            "sql/Publications/pub_get_checked_out_history.sql",
            "sql/Publications/pub_get_checked_out.sql",
            "sql/Publications/pub_get_res_requests.sql",
            "sql/Publications/pub_return.sql",
            "sql/Publications/pub_send_reminders.sql",
            "sql/Publications/publication_type.sql",
            "sql/Publications/renew.sql",
            "sql/Publications/reserve_copy.sql",
            
            "sql/Scheduler - Jobs/job_pub_fines_incrementer.sql",
            "sql/Scheduler - Jobs/job_pub_reminder.sql",
            
            "sql/Sequences/checks_out_id.sql",
            "sql/Sequences/fines_id.sql",
            
            "sql/View/copies_pub_screen_view.sql",
            "sql/View/pub_check_out_screen_view_hist.sql",
            "sql/View/pub_check_out_screen_view.sql",
            "sql/View/pub_res_req_screen_view.sql",
            "sql/new_table_and_edits.sql",
            "sql/bharathi_schedulers.sql"
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

        long endTime = System.currentTimeMillis();
        System.out.println("Operation completed in " + (endTime - startTime) + "ms.");

        conn.closeConnection();
    }

    public static void executeSQL(String path) {
        ArrayList<String> queryList = parseSQLFile(path);


        for(String sql : queryList) {
            //System.out.println(sql);
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

        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File(path)));
            String line = "";
            String currentQuery = "";
            while((line = reader.readLine()) != null) {
                if(useDashDelimeter) {
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
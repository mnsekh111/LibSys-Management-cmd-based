package edu.ncsu.csc540.proj1.db;

import java.sql.*;

import edu.ncsu.csc540.proj1.models.Faculty;
import edu.ncsu.csc540.proj1.models.Student;

/**
 * Simple class to connect to the Oracle database
 * @author Dixon Crews
 */
public class OracleAccess {

    public int getUserType(int id) {
        Student st = new Student();
        Faculty fa = new Faculty();
        if (st.isStudent(id))
            return 1;
        else if(fa.isFaculty(id))
            return 2;
        else
            return 3;
    }

}
package edu.ncsu.csc540.proj1.db;

import edu.ncsu.csc540.proj1.models.Faculty;
import edu.ncsu.csc540.proj1.models.Student;
import edu.ncsu.csc540.proj1.ui.MenuPage;

/**
 * Simple class to connect to the Oracle database
 * @author Dixon Crews
 */
public class OracleAccess {

    public int getUserType(int id) {
        Student st = new Student();
        Faculty fa = new Faculty();
        if (st.isStudent(id)) {
            return 1;
        } else if(fa.isFaculty(id)) {
            return 2;
        } else {
            return 3;
        }
    }
    
    public void setPatronStatus(int patron_id, MenuPage menu){
    	Faculty fa = new Faculty();
    	menu.setPatronGood(fa.getPatronStatus(patron_id));
    }

}
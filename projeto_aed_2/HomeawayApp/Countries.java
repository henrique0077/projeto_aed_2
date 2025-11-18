package HomeawayApp;

import Students.Student;
import dataStructures.Iterator;

import java.io.Serializable;

public interface Countries extends Serializable {

    Iterator<Student> getStudents();
}

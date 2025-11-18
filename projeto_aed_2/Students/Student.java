/**
 * @autor Henrique 71122 hv.pereira@campus.fct.unl.pt
 * @autor Pestana 71251 tf.pestana@campus.fct.unl.pt
 */

package Students;

import Enumerators.StudentType;
import Services.Service;
import dataStructures.Iterator;

public interface Student{

    /**
     * Gets the name of the student
     * @return the name of the student
     */
    String getName();

    /**
     * Gets the type of the student
     * @return the type of the student
     */
    StudentType getType();          // "bookish", "outgoing", or "thrifty"

    /**
     * Gets the location of the student
     * @return the location
     */
    String getCurrentLocation();

    /**
     * Gets student's home
     * @return the student's home
     */
    String getHome();

    /**
     * Updates current location
     * @param location the location where the student goes to
     */
    void goTo(Service location, Student studentName); // Updates current location

    /**
     * Changes student's home
     * @param lodgingName the new lodging of that student
     */
    void moveHome(Service lodgingName, Student studentName);

    /**
     * Adds a  visited location to the students visited array for bookish/outgoing students
     * @param location the location visited
     */
    void addVisitedLocation(Service location); // For bookish/outgoing students

    /**
     * Counter of the locations visited by one student
     * @return the number of visited locations
     */
    int getLocationsVisitedNum();

    /**
     * Lists the visited locations
     * @return the visited locations
     */
    Iterator<Service> getVisitedLocationsIterator();

    String getCountry ();


    void changeCapacityOfTheLodging(Student studentName);

}

/**
 * @autor Henrique 71122 hv.pereira@campus.fct.unl.pt
 * @autor Pestana 71251 tf.pestana@campus.fct.unl.pt
 */

package Students;

import Services.Service;
import dataStructures.Iterator;

public interface StudentCollection {

    /**
     * Checks if the collection is empty.
     *
     * @return true if the collection is empty, false otherwise.
     */
    boolean isEmpty();

    /**
     * Checks if there is a shape with the provided identifier.
     *
     * @param studentName The identifier of the shape to check.
     * @return true if there is a shape with the provided identifier, false otherwise.
     * @pre ID != null
     */
    boolean hasStudent(String studentName);

    int getSize();
    /**
     * Adds a shape to the collection.
     *
     * @param elem The shape to add.
     * @pre elem != null && this.hasElem(elem.getID())
     */
    void addStudent(String studentName, Student elem, String country);

    /**
     * Retrieves the shape with the provided identifier from the collection.
     *
     * @param studentName The identifier of the shape to retrieve.
     * @return The shape with the provided identifier.
     * @pre ID != null && this.hasElem(ID)
     */
    Student getElement(String studentName);
    /**
     * Removes a student from the system
     * @param studentName The name of the student to remove
     */
    void removeStudent(String studentName);

//    /**
//     * Returns an iterator over all shapes in the collection.
//     *
//     * @return An iterator over all shapes in the collection.
//     */
//    Iterator<Student> clientIterator();

    Iterator<Student> allStudentIterator();

    Iterator<Student> byCountryIterator(String country);

    boolean areThereStudentsFromCountry(String country);
}

/**
 * @autor Henrique 71122 hv.pereira@campus.fct.unl.pt
 * @autor Pestana 71251 tf.pestana@campus.fct.unl.pt
 */

package Services;

import Students.Student;
import Students.StudentCollection;
import dataStructures.List;
import dataStructures.Iterator;

import java.io.Serializable;

public interface ServicesCollection extends Serializable {

    /**
     * Checks if the collection is empty.
     *
     * @return true if the collection is empty, false otherwise.
     */
    boolean isEmpty();

    /**
     * Checks if there is a shape with the provided identifier.
     *
     * @param serviceName The identifier of the shape to check.
     * @return true if there is a shape with the provided identifier, false otherwise.
     * @pre ID != null
     */
    boolean hasElem(String serviceName);

    /**
     * Adds a shape to the collection.
     *
     * @param elem The shape to add.
     * @pre elem != null && this.hasElem(elem.getID())
     */
    void addElem(Service elem);

    /**
     * Checks if there are any services with a certain rating and type
     * @param type The wanted type of service
     * @param stars The wanted rating
     * @return True if there is any service with that characteristics, False otherwise
     */
    boolean isThereServicesWithCertainRate(String type, int stars);
    /**
     * Checks if there is any service with that type
     * @param type The type of the service
     * @return True if there is any, False otherwise
     */
    boolean isThereAnyServiceWithType(String type);

    /**
     * Retrieves the shape with the provided identifier from the collection.
     *
     * @param ID The identifier of the shape to retrieve.
     * @return The shape with the provided identifier.
     * @pre ID != null && this.hasElem(ID)
     */
    Service getElement(String ID);
    /**
     * Gets the nearest service of  certain type from a person
     * @param lat The latitude of the student
     * @param lon The longitude of the student
     * @param type The type of the service
     * @return The nearest service from that type
     */
    Service getTheNearestService(long lat, long lon, String type);
    /**
     * Gets the less expensive service for a thrifty person
     * @param lat The latitude of the student
     * @param lon The longitude of the student
     * @param type The type of the service
     * @return The cheapest service
     */
    Service getTheCheapestServiceThrifty(long lat, long lon, String type);
    /**
     * Returns an iterator over all services in the collection.
     *
     * @return An iterator over all services in the collection.
     */
    Iterator<Service> allServiceIterator();
    /**
     * Lists the services in the program with that type and rating
     * @param type The type of the service
     * @param rating The rating of the service
     * @return list with services of that type and rating
     */
    Iterator<Service> serviceIteratorByType(String type, int rating, long lat, long lon);
    /**
     * Lists the services in the program by rating
     * @return the sorted list of services
     */
    Iterator<Service> allServiceIteratorSortedRating();
    /**
     * Lists the services in the program
     * @return the sorted list of services
     */
    List<Service> sortByRating();

    Iterator<String> getDescriptions();

    Iterator<Service> getServicesWithTag(String tag);

}

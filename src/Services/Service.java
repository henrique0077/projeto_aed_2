/**
 * @autor Henrique 71122 hv.pereira@campus.fct.unl.pt
 * @autor Pestana 71251 tf.pestana@campus.fct.unl.pt
 */

package Services;

import Enumerators.ServiceType;
import Students.Student;
import dataStructures.Iterator;

import java.io.Serializable;

public interface Service extends Serializable {

    /**
     * Gets the name of the service
     * @return the name
     */
    String getName();

    /**
     * Gets the latitude of the service
     * @return the latitude
     */
    long getLatitude();

    /**
     * Gets the longitude of the service
     * @return the longitude
     */
    long getLongitude();

    /**
     * Gets the average stars of a service
     *
     * @return the rating of the service
     */
    int getAverageStars();

    /**
     * Gets the "time" when the last update was made
     * @return The lastUpdateOrder
     */
    int getLastUpdatedOrder();

    /**
     * Adds a new rating to the service
     * @param stars the rating given to the service
     */
    void addRating(int stars, int updateCount);

    /**
     * Gets the number of stars of a service
     * @return The number of stars
     */
    float getTotalStars();

    /**
     * Gets the rating count
     * @return The ratingCount
     */
    int getRatingCount();
    /**
     * Increments the lastUpdateOrder
     */
    void updateCounterRating();

    /**
     * Gets the service type
     * @return the type of the service
     */
    ServiceType getServiceType();

    /**
     * Gets the service price
     * @return the price of the service
     */
    int getServicePrice();

    Iterator<String> getDescriptions();

    void addDescription(String description);

    Iterator<Student> clientsIterator(String order, Service service);

    boolean checkCapacity(Service service);

}

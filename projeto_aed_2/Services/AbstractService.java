/**
 * @autor Henrique 71122 hv.pereira@campus.fct.unl.pt
 * @autor Pestana 71251 tf.pestana@campus.fct.unl.pt
 */

package Services;

import Enumerators.ServiceType;
import Students.Student;
import dataStructures.DoublyLinkedList;
import dataStructures.*;
import dataStructures.TwoWayList;
import dataStructures.Iterator;

import java.io.Serializable;

public abstract class AbstractService implements Service, Serializable {

    private final String name;
    private final long latitude;
    private final long longitude;
    private final int DEFAULT_DIMENTION = 1;

    private float totalStars;
    private int ratingCount;
    private final int servicePrice;
    private int lastUpdatedOrder; // Helps deciding which one was updated last
    private TwoWayList<String> descriptions;

    public AbstractService(String name, long lat, long lon, int price, int updateCount){
        this.name = name;
        this.latitude = lat;
        this.longitude = lon;
        this.totalStars = 4;  // Default initial rating (4 stars)
        this.ratingCount = 1;
        servicePrice = price;
        lastUpdatedOrder = updateCount;
        descriptions = new DoublyLinkedList<>();
    }

    @Override
    public int getServicePrice(){
        return servicePrice;
    }

    @Override
    public float getTotalStars(){
        return totalStars;
    }

    @Override
    public int getRatingCount(){
        return ratingCount;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public long getLatitude() {
        return latitude;
    }
    @Override
    public long getLongitude() {
        return longitude;
    }
    @Override
    public int getAverageStars() {
        return Math.round(totalStars / ratingCount);
    }
    @Override
    public void addRating(int stars, int updateCounter) {
        totalStars += stars;
        ratingCount++;
        lastUpdatedOrder = updateCounter;
    }

    @Override
    public void updateCounterRating() {
        lastUpdatedOrder++;
    }

    @Override
    public int getLastUpdatedOrder(){
        return lastUpdatedOrder;
    }

    public void addDescription(String description){
        descriptions.addLast(description);
    }

    public Iterator<String> getDescriptions(){
        return descriptions.iterator();
    }

    public Iterator<Student> clientsIterator(String order, Service service) {
        List<Student> clients = new  ListInArray<>(DEFAULT_DIMENTION);
        if (service instanceof EatingServiceClass eating) {
                if (order.equals(">"))
                    return eating.getClientsList().iterator();
                else {
                    for (int i = eating.getClientsList().size() - 1; i >= 0; i--) {
                        clients.addLast(eating.getClientsList().get(i));
                    }
                }
        }
        else if (service instanceof LodgingServiceClass lodging) {
                if (order.equals(">"))
                    return lodging.getClientsList().iterator();
                else {
                    for (int i = lodging.getClientsList().size() - 1; i >= 0; i--) {
                        clients.addLast(lodging.getClientsList().get(i));
                    }
                }
        }
        return clients.iterator();
    }

    public boolean checkCapacity(Service service){
        boolean result = false;
        if (service instanceof EatingServiceClass eating)
            result = eating.getCapacity() == eating.getFullCapacity();
        else if (service instanceof LodgingServiceClass lodging)
            result = lodging.getCapacity() == lodging.getFullCapacity();
        return result;
    }
}

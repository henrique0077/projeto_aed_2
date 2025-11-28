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

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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

    // Se não tens writeObject, ADICIONA:
    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.defaultWriteObject();
    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
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
        DoublyLinkedList<Student> clients = new DoublyLinkedList<>();
        DoublyLinkedList<Student> sourceList = null;

        if (service instanceof EatingServiceClass eating) {
            sourceList = eating.getClientsList();
        } else if (service instanceof LodgingServiceClass lodging) {
            sourceList = lodging.getClientsList();
        }

        if (sourceList != null) {
            if (order.equals(">")) {
                return sourceList.iterator();
            } else {
                // Inversão eficiente O(N)
                Iterator<Student> it = sourceList.iterator();
                while (it.hasNext()) {
                    clients.addFirst(it.next());
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

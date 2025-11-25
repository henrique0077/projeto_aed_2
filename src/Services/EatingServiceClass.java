/**
 * @autor Henrique 71122 hv.pereira@campus.fct.unl.pt
 * @autor Pestana 71251 tf.pestana@campus.fct.unl.pt
 */

package Services;

import Enumerators.ServiceType;
import Students.Student;
import dataStructures.DoublyLinkedList;

import java.io.Serializable;

public class EatingServiceClass extends AbstractService implements EatingLodging, Serializable {

    int capacity;
    int fullCapacity;
    private final DoublyLinkedList<Student> clientsList;


    public EatingServiceClass(String name, long lat, long lon, int price, int value, int updateCount){
        super(name, lat, lon, price, updateCount);
        this.capacity = value;
        this.fullCapacity = value;
        clientsList = new DoublyLinkedList<>();
    }
    @Override
    public ServiceType getServiceType() {
        return ServiceType.EATING;
    }


//    @Override
//    public int getSpace() {
//        return 0;
//    }

    public int getCapacity() {
       return this.capacity;
    }

    public int getFullCapacity() {
        return this.fullCapacity;
    }

    public boolean hasCapacity() {
        return this.capacity > 0;
    }

    public void addClient(Student client) {
        this.capacity --;
        clientsList.addLast(client);
        //todo array com os bacanos
    }

    public void removeClient(Student client) {
        this.capacity ++;
        clientsList.remove(clientsList.indexOf(client));
        //Todo array com os bacanos
    }

    public DoublyLinkedList<Student> getClientsList() {
        return clientsList;
    }
}
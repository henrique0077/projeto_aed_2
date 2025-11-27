/**
 * @autor Henrique 71122 hv.pereira@campus.fct.unl.pt
 * @autor Pestana 71251 tf.pestana@campus.fct.unl.pt
 */

package Services;

import Enumerators.ServiceType;
import Students.Student;
import dataStructures.DoublyLinkedList;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class LodgingServiceClass extends AbstractService implements EatingLodging, Serializable {

    int fullCapacity;
    int capacity;
    private DoublyLinkedList<Student> clientsList;


    public LodgingServiceClass(String name, long lat, long lon, int price, int value, int updateCount){
        super(name, lat, lon, price, updateCount);
        this.fullCapacity = value;
        this.capacity = value;
        clientsList = new DoublyLinkedList<>();
    }
    @Override
    public ServiceType getServiceType() { //!!! Podemos Fazer ISTO !!!
        return ServiceType.LODGING;
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
    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.defaultWriteObject();
    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        clientsList = new DoublyLinkedList<>();
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

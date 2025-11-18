package Services;

import Students.Student;
import dataStructures.DoublyLinkedList;

import java.io.Serializable;

public interface EatingLodging extends Serializable {



    int getCapacity();

    int getFullCapacity();

    boolean hasCapacity();

    void addClient(Student client);

    void removeClient(Student client);

    DoublyLinkedList<Student> getClientsList();
}


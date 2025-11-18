/**
 * @autor Henrique 71122 hv.pereira@campus.fct.unl.pt
 * @autor Pestana 71251 tf.pestana@campus.fct.unl.pt
 */

package Students;

import dataStructures.DoublyLinkedList;
import dataStructures.Iterator;

import java.io.Serializable;

public class StudentsIteratorClass implements Iterator<Student>, Serializable { //ver se não trocamos por um twoWayDoublyIterator

    private final DoublyLinkedList<Student> students;
    private final int counter;
    private int nextStudents;

    public StudentsIteratorClass(DoublyLinkedList<Student> students, int counter) {
        this.students = students;
        this.counter = counter;
    }

    @Override
    public boolean hasNext() {
        return nextStudents < counter;
    }

    @Override
    public Student next() {
        return students.get(nextStudents++);
    }

    @Override
    public void rewind() {
        nextStudents = 0;
    }
}

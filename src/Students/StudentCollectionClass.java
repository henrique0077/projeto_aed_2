/**
 * @autor Henrique 71122 hv.pereira@campus.fct.unl.pt
 * @autor Pestana 71251 tf.pestana@campus.fct.unl.pt
 */

package Students;

import dataStructures.*;
import java.io.Serializable;

public class StudentCollectionClass implements StudentCollection, Serializable {

    private final Map<String, Student> studentsByName;
    private final Map<String, Student> studentsSorted;
    private final Map<String, List<Student>> studentsByCountry;  //este não tinhamos no relatório, mas acho boa ideia
    private int studentsCounter;

    public StudentCollectionClass(){
        studentsByName = new SepChainHashTable<>();
        studentsSorted = new AVLSortedMap<>();
        studentsByCountry = new SepChainHashTable<>();
        studentsCounter = 0;
    }

    @Override
    public boolean isEmpty() {
        return studentsCounter == 0;
    }

    @Override
    public boolean hasStudent(String studentName) {
        return studentsByName.get(studentName.toUpperCase()) != null;
    }

    public int getSize() {
        return studentsCounter;
    }

    @Override
    public void addStudent(String studentName, Student elem, String country) {
        String student = studentName.toUpperCase();
        String countryCode = country.toUpperCase();

        studentsByName.put(student, elem);
        studentsSorted.put(student, elem);
        if (studentsByCountry.get(countryCode) == null) { //se aquele país ainda não existir, criamos
            studentsByCountry.put(countryCode, new DoublyLinkedList<>());
        }
        studentsByCountry.get(countryCode).addLast(elem);
        studentsCounter++;
    }

    @Override
    public Student getElement(String studentName) {
        return studentsByName.get(studentName.toUpperCase());
    }

    @Override
    public void removeStudent(String studentName) {
        String country = getElement(studentName.toUpperCase()).getCountry().toUpperCase();
        studentsByName.remove(studentName);
        studentsSorted.remove(studentName);
        studentsByCountry.get(country).remove(getStudentIndex(studentName.toUpperCase(), country));
        studentsCounter--;
    }

    private int getStudentIndex(String studentName, String country) {
        for(int i = 0; i < studentsCounter; i++){
            if(studentName.equals(studentsByCountry.get(country.toUpperCase()).get(i).getName()))
                return i;
        }
        return -1;
    }

//    @Override //para que é que serve este método?
//    public Iterator<Student> clientIterator() {
//        return new InOrderIterator<>()
//        return new StudentsIteratorClass(students,studentsCounter);
//    }

    @Override
    public Iterator<Student> allStudentIterator() {
        return studentsSorted.values();
    }

    public Iterator<Student> byCountryIterator(String country){
        return studentsByCountry.get(country.toUpperCase()).iterator();
    }


}

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
    private final Map<String, Map<String, Student>> studentsByCountry;  //este não tinhamos no relatório, mas acho boa ideia
    private int studentsCounter;

    public StudentCollectionClass(){
        studentsByName = new SepChainHashTable<>();
        studentsSorted = new BSTSortedMap<>();
        studentsByCountry = new SepChainHashTable<>();
        studentsCounter = 0;
    }

    @Override
    public boolean isEmpty() {
        return studentsCounter == 0;
    }

    @Override
    public boolean hasStudent(String studentName) {
        return studentsByName.get(studentName) != null;
    }

    public int getSize() {
        return studentsCounter;
    }

    @Override
    public void addStudent(String studentName, Student elem, String country) {
        //students.addLast(elem);
        studentsByName.put(studentName, elem);
        studentsSorted.put(studentName, elem);
        if (studentsByCountry.get(country) == null) { //se aquele país ainda não existir, criamos
            studentsByCountry.put(country, new SepChainHashTable<>());
        }
        studentsByCountry.get(country).put(studentName, elem);
        studentsCounter++;
    }

    @Override
    public Student getElement(String studentName) {
        return studentsByName.get(studentName);
    }

    @Override
    public void removeStudent(String studentName) {
        studentsByName.remove(studentName);
        studentsSorted.remove(studentName);
        studentsByCountry.get(getElement(studentName).getCountry()).remove(studentName); //isto não está bem. Temos que dar uma key, mas o nome dele está dentro do Objeto que é o value. Talvez seja um mapa dentro de um mapa
        studentsCounter--;
    }

    @Override //para que é que serve este método?
    public Iterator<Student> clientIterator() {
        return new InOrderIterator<>()
        return new StudentsIteratorClass(students,studentsCounter);
    }

    @Override
    public Iterator<Student> allStudentIterator() {
        return studentsSorted.values();
    }

    public Iterator<Student> byCountryIterator(String country){
        return studentsByCountry.get(country).values();
    }


}

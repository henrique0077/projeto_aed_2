/**
 * @autor Henrique 71122 hv.pereira@campus.fct.unl.pt
 * @autor Pestana 71251 tf.pestana@campus.fct.unl.pt
 */

package Students;

import dataStructures.*;

import javax.swing.plaf.PanelUI;
import java.io.Serializable;

import java.io.IOException;
import java.io.ObjectInputStream;

public class StudentCollectionClass implements StudentCollection, Serializable {

    private final Map<String, Student> studentsByName;

    private transient Map<String, Student> studentsSorted;
    private transient Map<String, List<Student>> studentsByCountry;  //este não tinhamos no relatório, mas acho boa ideia
    private int studentsCounter;

    public StudentCollectionClass(){
        studentsByName = new SepChainHashTable<>();
        studentsCounter = 0;
        initializeMaps();
    }

    private void initializeMaps() {
        studentsSorted = new AVLSortedMap<>();
        studentsByCountry = new SepChainHashTable<>();
    }

    private void repopulateMaps(Student elem) {
        String studentName = elem.getName().toUpperCase();
        String countryCode = elem.getCountry().toUpperCase();

        studentsSorted.put(studentName, elem);

        if (studentsByCountry.get(countryCode) == null) {
            studentsByCountry.put(countryCode, new DoublyLinkedList<>());
        }
        studentsByCountry.get(countryCode).addLast(elem);
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
        /**studentsSorted.put(student, elem);
        if (studentsByCountry.get(countryCode) == null) { //se aquele país ainda não existir, criamos
            studentsByCountry.put(countryCode, new DoublyLinkedList<>());
        }
        studentsByCountry.get(countryCode).addLast(elem);*/
        studentsCounter++;
        repopulateMaps(elem);
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject(); // Lê studentsByName e studentsCounter

        initializeMaps(); // Cria a AVL e o Mapa de Países (vazios)

        // Percorre o mapa principal para reconstruir os outros
        // Nota: Precisamos de um iterador para os valores do mapa studentsByName
        if (studentsByName.size() > 0) {
            Iterator<Student> it = studentsByName.values();
            while (it.hasNext()) {
                Student s = it.next();
                if (s != null) {
                    repopulateMaps(s);
                }
            }
        }
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
            List<Student> list = studentsByCountry.get(country.toUpperCase());
            if (list == null)
                return new DoublyLinkedList<Student>().iterator();
            return list.iterator();
    }

    public boolean areThereStudentsFromCountry(String country){
        List<Student> students = studentsByCountry.get(country.toUpperCase());
        if (students == null)
            return false;           
        return !students.isEmpty();
    }


}

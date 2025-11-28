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
        in.defaultReadObject(); // Lê as variáveis normais

        System.out.println("1. Leitura padrão concluída.");

        if (this.studentsByName == null) {
            System.out.println("ERRO CRÍTICO: studentsByName está NULL! Verifica se não a marcaste como 'transient' sem querer.");
        } else {
            System.out.println("2. studentsByName recuperado. Tamanho: " + this.studentsByName.size());
        }

        initializeMaps(); // Cria os mapas vazios
        System.out.println("3. Mapas auxiliares inicializados.");

        // TESTE DE SANIDADE AOS MAPAS AUXILIARES
        if (this.studentsSorted == null) System.out.println("ERRO: studentsSorted está NULL");
        if (this.studentsByCountry == null) System.out.println("ERRO: studentsByCountry está NULL");

        Iterator<Student> it = studentsByName.values();
        if (it == null) {
            System.out.println("ERRO CRÍTICO: O método .values() retornou NULL. O problema está na HashTable/Map.");
        } else {
            System.out.println("4. Iterador criado. A iniciar reconstrução...");
            while(it.hasNext()) {
                Student s = it.next();
                if (s == null) {
                    System.out.println("AVISO: Encontrado um estudante NULL na lista.");
                    continue;
                }
                repopulateMaps(s);
            }
        }
        System.out.println("5. Reconstrução terminada com sucesso.");
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

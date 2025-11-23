/**
 * @autor Henrique 71122 hv.pereira@campus.fct.unl.pt
 * @autor Pestana 71251 tf.pestana@campus.fct.unl.pt
 */

package Students;

import dataStructures.*;
import org.hamcrest.internal.ArrayIterator;

import java.io.Serializable;
import java.util.ArrayList;

public class StudentCollectionClass implements StudentCollection, Serializable {

    private final DoublyLinkedList<Student> students; //este é para desaparecer
    private final SepChainHashTable<String, Student> studentsByName;
    private final BSTSortedMap<String, Student> studentsSorted;
    private final SepChainHashTable<String, Student> studentsByCountry;  //este não tinhamos no relatório, mas acho boa ideia
    private int studentsCounter;
    private final int DEFAULT_DIMENTION = 1;

    public StudentCollectionClass(){
        students = new DoublyLinkedList<>();
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
        students.addLast(elem);
        studentsByName.put(studentName, elem);
        studentsSorted.put(studentName, elem);
        studentsByCountry.put(country, elem);
        studentsCounter++;
    }

    @Override
    public Student getElement(String studentName) {
        return studentsByName.get(studentName);
    }

    @Override
    public void removeElem(String studentName) {
        studentsByName.remove(studentName);
        studentsSorted.remove(studentName);
        studentsByCountry.remove(studentName); //isto não está bem. Temos que dar uma key, mas o nome dele está dentro do Objeto que é o value. Talvez seja um mapa dentro de um mapa
        studentsCounter--;
    }

    @Override
    public Iterator<Student> clientIterator() {
        return new StudentsIteratorClass(students,studentsCounter);
    }

//    @Override
//    public Iterator<Student> allStudentIterator() {
//        int change = 0;
//        List<Student> temp = new ListInArray<>(DEFAULT_DIMENTION);
//        for (int i = 0; i < studentsCounter; i++) {
//            for (int j = 0; j < studentsCounter; j++) {
//                if (i != j)
//                    change = sort(students.get(i).getName(), students.get(j).getName());
//                if (change==-1)
//                    temp.add(i-1,students.get(i));
//                else {
//                    temp.addLast(students.get(i));
//                }
//            }
//        }
//        return temp.iterator();
//    }

    @Override
    public Iterator<Student> allStudentIterator() {
        List<Student> temp = new ListInArray<>(studentsCounter);
        // Copia todos os estudantes para a lista temporária
        for (int i = 0; i < studentsCounter; i++) {
            temp.addLast(students.get(i));
        }
        // Ordena os estudantes pelo nome usando bubble sort simples (ou qualquer algoritmo de ordenação)
        for (int i = 0; i < temp.size() - 1; i++) {
            for (int j = 0; j < temp.size() - 1 - i; j++) {
                Student s1 = temp.get(j);
                Student s2 = temp.get(j + 1);
                if (sort(s1.getName(), s2.getName()) > 0) {
                    // Troca de posições
                    Student aux = temp.get(j);
                    temp.remove(j);
                    temp.add(j, temp.get(j)); // move o próximo para a posição atual
                    temp.remove(j + 1);
                    temp.add(j + 1, aux); // insere aux na posição seguinte
                }
            }
        }
        return temp.iterator();
    }


    public static int sort(String a, String b) {
        if (a == null && b == null) {
            return 0; // São iguais
        }
        if (a == null) {
            return 1; // "a" é nulo, deve vir depois
        }
        if (b == null) {
            return -1; // "b" é nulo, deve vir depois
        }
        String aLower = a.toLowerCase();
        String bLower = b.toLowerCase();
        int minLength = Math.min(aLower.length(), bLower.length());
        for (int i = 0; i < minLength; i++) {
            char charA = aLower.charAt(i);
            char charB = bLower.charAt(i);
            if (charA < charB) {
                return -1; // "a" vem antes de "b"
            } else if (charA > charB) {
                return 1; // "a" vem depois de "b"
            }
        }
        if (aLower.length() < bLower.length()) {
            return -1;
        } else if (aLower.length() > bLower.length()) {
            return 1;
        } else {
            return 0; // São iguais
        }
    }

    public Iterator<Student> byCountryIterator(String country){ //tentar fazer com o filterIterator, mas deve estar a funcionar
        //Iterator<Student> stu = new FilterIterator<>();
        List<Student> temp = new ListInArray<>(DEFAULT_DIMENTION);
        for (int i = 0; i < studentsCounter; i++) {
            if (students.get(i).getCountry().equalsIgnoreCase(country))
                temp.addLast(students.get(i));
        }
        return temp.iterator();
    }

//    private Predicate<Student> getUsersByCountry(String country) {
//        Predicate<Student> s1 = s1 -> (s1.getCountry().equalsIgnoreCase(country));
//        return s1;
//    }


}

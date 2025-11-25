package dataStructures;

import dataStructures.exceptions.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;


/**
 * Sorted Doubly linked list Implementation
 * @author AED  Team
 * @version 1.0
 * @param <E> Generic Element
 * 
 */
public class SortedDoublyLinkedList<E> implements SortedList<E> {

    /**
     *  Node at the head of the list.
     */
    private DoublyListNode<E> head;
    /**
     * Node at the tail of the list.
     */
    private DoublyListNode<E> tail;
    /**
     * Number of elements in the list.
     */
    private int currentSize;
    /**
     * Comparator of elements.
     */
    private final Comparator<E> comparator;
    /**
     * Constructor of an empty sorted double linked list.
     * head and tail are initialized as null.
     * currentSize is initialized as 0.
     */
    public SortedDoublyLinkedList(Comparator<E> comparator) {
        if (comparator == null) throw new InvalidPositionException();
        this.head = null;
        this.tail = null;
        this.currentSize = 0;
        this.comparator = comparator;

    }

    /**private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.defaultWriteObject(); // Escreve o comparator
        oos.writeInt(currentSize);

        DoublyListNode<E> node = head;
        while (node != null) {
            oos.writeObject(node.getElement());
            node = node.getNext();
        }
        oos.flush();
    }*/

    /**private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        /**ois.defaultReadObject(); // Lê o comparator
        int size = ois.readInt();

        head = null;
        tail = null;
        currentSize = 0;

        // Usa addDirect para preservar a ordem original
        for (int i = 0; i < size; i++) {
            @SuppressWarnings("unchecked")
            E element = (E) ois.readObject();
            addDirect(element); // Adiciona na mesma ordem que foi guardado
        }

        ois.defaultReadObject(); // Lê o comparator (pode ser null)
        int size = ois.readInt();

        head = null;
        tail = null;
        currentSize = 0;

        // Se comparator for null, adiciona diretamente
        if (comparator == null) {
            for (int i = 0; i < size; i++) {
                @SuppressWarnings("unchecked")
                E element = (E) ois.readObject();
                addDirect(element); // Adiciona no final
            }
        } else {
            // Se tem comparator, adiciona normalmente
            for (int i = 0; i < size; i++) {
                @SuppressWarnings("unchecked")
                E element = (E) ois.readObject();
                add(element); // Usa a ordenação
            }
        }
    }

    private void addDirect(E element) {
        DoublyListNode<E> newNode = new DoublyListNode<>(element);

        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.setNext(newNode);
            newNode.setPrevious(tail);
            tail = newNode;
        }
        currentSize++;
    }*/

    /**
     * Returns true iff the list contains no elements.
     * @return true if list is empty
     */
    public boolean isEmpty() {
        return currentSize==0;
    }

    /**
     * Returns the number of elements in the list.
     * @return number of elements in the list
     */

    public int size() {
        return currentSize;
    }

    /**
     * Returns an iterator of the elements in the list (in proper sequence).
     * @return Iterator of the elements in the list
     */
    public Iterator<E> iterator() {
        return new DoublyIterator<>(head);
    }

    /**
     * Returns the first element of the list.
     * @return first element in the list
     * @throws NoSuchElementException - if size() == 0
     */
    public E getMin( ) {
        if (size()==0)
            throw new NoSuchElementException();
        return head.getElement();
    }

    /**
     * Returns the last element of the list.
     * @return last element in the list
     * @throws NoSuchElementException - if size() == 0
     */
    public E getMax( ) {
        if (size()==0)
            throw new NoSuchElementException();
        return tail.getElement();
    }
    /**
     * Returns the first occurrence of the element equals to the given element in the list.
     * @return element in the list or null
     */
    public E get(E element) {
        if (element == null) return null;
        DoublyListNode<E> current = head;
        while(current != null) {
            if (comparator.compare(current.getElement(), element) == 0)
                return current.getElement();
            current = current.getNext();
        }
        return null;
    }

    /**
     * Returns true iff the element exists in the list.
     *
     * @param element to be found
     * @return true iff the element exists in the list.
     */
    public boolean contains(E element) {
        return get(element) != null;
    }

    /**
     * Inserts the specified element at the list, according to the natural order.
     * If there is an equal element, the new element is inserted after it.
     * @param element to be inserted
     */
    public void add(E element) {
        if (element==null) throw new InvalidPositionException();
        DoublyListNode<E> newNode = new DoublyListNode<>(element);
        if (this.isEmpty()) {
            head = newNode;
            tail = newNode;
            currentSize++;
            return;
        }
        DoublyListNode<E> current = head;
        while (current != null && comparator.compare(current.getElement(), element) <= 0) {
            current = current.getNext();
        }
        if (current == null) {
            tail.setNext(newNode);
            newNode.setPrevious(tail);
            tail = newNode;
        }
        else if (current == head) {
            newNode.setNext(head);
            head.setPrevious(newNode);
            head = newNode;
        }
        else {
            DoublyListNode<E> previous = current.getPrevious();
            previous.setNext(newNode);
            newNode.setPrevious(previous);
            current.setPrevious(newNode);
            newNode.setNext(current);
        }
        currentSize++;
    }

    /**
     * Removes and returns the first occurrence of the element equals to the given element in the list.
     * @return element removed from the list or null if !belongs(element)
     */
    public E remove(E element){
        if (element == null || this.isEmpty()) return null;
        DoublyListNode<E> current = head;

        while(current != null && comparator.compare(current.getElement(), element) != 0) {
            current = current.getNext();
        }
        if (current == null) return null;
        if (current == head){
            head = head.getNext();
            if (head != null) head.setPrevious(null);
            else tail = null;
        }
        else if (current == tail){
            tail = tail.getPrevious();
            if (tail != null) tail.setNext(null);
            else head = null;
        }
        else{
            DoublyListNode<E> previous = current.getPrevious();
            DoublyListNode<E> next = current.getNext();
            previous.setNext(next);
            next.setPrevious(previous);
        }
        currentSize--;
        return current.getElement();
    }
}

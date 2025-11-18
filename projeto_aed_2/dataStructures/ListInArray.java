package dataStructures;
import dataStructures.exceptions.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * List in Array
 * @author AED  Team
 * @version 1.0
 * @param <E> Generic Element
 *
 */
public class ListInArray<E> implements List<E>, Serializable {

    private static final int FACTOR = 2;
    /**
     * Array of generic elements E.
     */
    private transient E[] elems;

    private final int DEFAULT_DIMENTION = 5;

    /**
     * Number of elements in array.
     */
    private transient int counter;

    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.defaultWriteObject(); // escreve os campos normais (não temos aqui, mas é boa prática)
        oos.writeInt(counter); // escreve o tamanho
        for (int i = 0; i < counter; i++) {
            oos.writeObject(elems[i]); // escreve cada elemento
        }
        oos.flush();
    }
    @SuppressWarnings("unchecked")
    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject(); // lê os campos normais
        int size = ois.readInt(); // lê o tamanho
//cenas novas
        int capacity = Math.max(size, DEFAULT_DIMENTION);
        this.elems = (E[]) new Object[capacity];
        this.counter = 0;

        for (int i = 0; i < size; i++) {
            E element = (E) ois.readObject();
            addLast(element); // recria os nós
        }
    }


    /**
     * Construtor with capacity.
     * @param dimension - initial capacity of array.
     */
    @SuppressWarnings("unchecked")
    public ListInArray(int dimension) {
        elems = (E[]) new Object[dimension];
        counter = 0;
    }

    /**
     * Returns true iff the list contains no elements.
     *
     * @return true if list is empty
     */
    public boolean isEmpty() {
        return counter==0;
    }

    /**
     * Returns the number of elements in the list.
     *
     * @return number of elements in the list
     */
    public int size() {
        return counter;
    }

    /**
     * Returns an iterator of the elements in the list (in proper sequence).
     *
     * @return Iterator of the elements in the list
     */
    public Iterator<E> iterator() {
        return new ArrayIterator<>(elems,counter);
    }

    /**
     * Returns the first element of the list.
     *
     * @return first element in the list
     * @throws NoSuchElementException - if size() == 0
     */
    public E getFirst() {
        if(size() == 0) throw new NoSuchElementException();
        return elems[0];
    }

    /**
     * Returns the last element of the list.
     *
     * @return last element in the list
     * @throws NoSuchElementException - if size() == 0
     */
    public E getLast() {
        if(size() == 0) throw new NoSuchElementException();
        return elems[counter-1];
    }

    /**
     * Returns the element at the specified position in the list.
     * Range of valid positions: 0, ..., size()-1.
     * If the specified position is 0, get corresponds to getFirst.
     * If the specified position is size()-1, get corresponds to getLast.
     *
     * @param position - position of element to be returned
     * @return element at position
     * @throws InvalidPositionException if position is not valid in the list
     */
    public E get(int position) throws InvalidPositionException {
        if (position < 0 || position >= size()) throw new InvalidPositionException();
        if(position == 0)
            return getFirst();
        else if(position == size() - 1)
            return getLast();
        else
            return elems[position];
    }

    /**
     * Returns the position of the first occurrence of the specified element
     * in the list, if the list contains the element.
     * Otherwise, returns -1.
     *
     * @param element - element to be searched in list
     * @return position of the first occurrence of the element in the list (or -1)
     */
    public int indexOf(E element) {
        for (int i = 0; i < counter; i++) {
            if (elems[i].equals(element))
                return i;
        }
        return -1;
    }

    /**
     * Inserts the specified element at the first position in the list.
     *
     * @param element to be inserted
     */
    public void addFirst(E element) {
        ensureCapacity();
        for (int i = counter; i > 0; i--) {
            elems[i] = elems[i-1];
        }
        elems[0] = element;
        counter++;
    }

    /**
     * Inserts the specified element at the last position in the list.
     *
     * @param element to be inserted
     */
    public void addLast(E element) {
        ensureCapacity();
        elems[counter] = element;
        counter++;
    }

    /**
     * Inserts the specified element at the specified position in the list.
     * Range of valid positions: 0, ..., size().
     * If the specified position is 0, add corresponds to addFirst.
     * If the specified position is size(), add corresponds to addLast.
     *
     * @param position - position where to insert element
     * @param element  - element to be inserted
     * @throws InvalidPositionException - if position is not valid in the list
     */
    public void add(int position, E element) {
        if (position < 0 || position > size())
            throw new InvalidPositionException();
        else if (position == 0)
            addFirst(element);
        else if (position == size()) {
            ensureCapacity();
            addLast(element);
        } else {
            ensureCapacity();
            putInPlace(position, element);
        }
    }



    private void putInPlace(int position ,E element) {
        for (int i = counter; i > position; i--) {
            elems[i] = elems[i - 1];
        }
        elems[position] = element;
        counter++;
    }

    /**
     * Removes and returns the element at the first position in the list.
     *
     * @return element removed from the first position of the list
     * @throws NoSuchElementException - if size() == 0
     */
    public E removeFirst() {
        if (size() == 0) throw new NoSuchElementException();
        E removed = elems[0];
        for (int i = 0; i < counter - 1; i++) {
            elems[i] = elems[i + 1];
        }
        elems[counter - 1] = null; // opcional para ajudar GC
        counter--;
        return removed;
    }


    /**
     * Removes and returns the element at the last position in the list.
     *
     * @return element removed from the last position of the list
     * @throws NoSuchElementException - if size() == 0
     */
    public E removeLast() {
        if(size() == 0) throw new NoSuchElementException();
        E removed = elems[counter - 1];
        elems[counter-1] = null;
        counter--;
        return removed;
    }

    private void ensureCapacity() {
        if (counter == elems.length) {
            resize();
        }
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        E[] newElems = (E[]) new Object[elems.length * FACTOR];
        System.arraycopy(elems, 0, newElems, 0, elems.length);
        elems = newElems;
    }

    /**
     * Removes and returns the element at the specified position in the list.
     * Range of valid positions: 0, ..., size()-1.
     * If the specified position is 0, remove corresponds to removeFirst.
     * If the specified position is size()-1, remove corresponds to removeLast.
     *
     * @param position - position of element to be removed
     * @return element removed at position
     * @throws InvalidPositionException - if position is not valid in the list
     */
    public E remove(int position) {
        if (position < 0 || position >= size())
            throw new InvalidPositionException();
        else if (position == 0)
            return removeFirst();
        else if (position == size() - 1)
            return removeLast();
        else {
            E removed = elems[position];
            for (int i = position; i < size() - 1; i++) {
                elems[i] = elems[i + 1];
            }
            elems[size() - 1] = null; // Limpa a última posição para ajudar garbage collection
            counter--;
            return removed;
        }
    }
}

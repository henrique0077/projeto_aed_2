package dataStructures;

import dataStructures.exceptions.*;
import java.io.IOException;
import java.io.Serializable;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SinglyLinkedList<E> implements List<E>, Serializable {
    /**
     *  Node at the head of the list.
     */
    private transient SinglyListNode<E> head;  //estes todos mudei para transient
    /**
     * Node at the tail of the list.
     */
    private transient SinglyListNode<E> tail;
    /**
     * Number of elements in the list.
     */
    private transient int currentSize;
    /**
     * Constructor of an empty singly linked list.
     * head and tail are initialized as null.
     * currentSize is initialized as 0.
     */
    public SinglyLinkedList( ) {
        head = null;
        tail = null;
        currentSize = 0;
    }

    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.defaultWriteObject();

        oos.writeInt(currentSize);

        SinglyListNode<E> currentNode = head;
        while (currentNode != null) {
            oos.writeObject(currentNode.getElement());
            currentNode = currentNode.getNext();
        }
    }

    @SuppressWarnings("unchecked")
    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();

        int size = ois.readInt();

        head = null;
        tail = null;
        currentSize = 0;

        for (int i = 0; i < size; i++) {
            E element = (E) ois.readObject();
            this.addLast(element);
        }
    }



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
        return new SinglyIterator<>(head);
    }

    /**
     * Returns the first element of the list.
     *
     * @return first element in the list
     * @throws NoSuchElementException - if size() == 0
     */
    @Override
    public E getFirst() {
        if ( this.isEmpty() )
            throw new NoSuchElementException();
        return head.getElement();
    }

    /**
     * Returns the last element of the list.
     *
     * @return last element in the list
     * @throws NoSuchElementException - if size() == 0
     */
    @Override
    public E getLast() {
        if ( this.isEmpty() )
            throw new NoSuchElementException();
        return tail.getElement();
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
    @Override
    public E get(int position) {
        if ( position < 0 || position >= currentSize )
            throw new InvalidPositionException();
        if (position == 0)
            return getFirst();
        if (position == currentSize-1)
            return getLast();
        return getNode(position).getElement();
    }

    private SinglyListNode<E> getNode(int position) {
        SinglyListNode<E> node = head;

        for ( int i = 0; i < position; i++)
            node = node.getNext();
        return node;
    }



    /**
     * Returns the position of the first occurrence of the specified element
     * in the list, if the list contains the element.
     * Otherwise, returns -1.
     *
     * @param element - element to be searched in list
     * @return position of the first occurrence of the element in the list (or -1)
     */
    @Override
    public int indexOf(E element) {
        SinglyListNode<E> node = head;
        int position = 0;

        while ( node != null && !node.getElement().equals(element) ) {
            node = node.getNext();
            position++;
        }
        if ( node == null )
            return NOT_FOUND;
        return position;
    }

    /**
     * Inserts the specified element at the first position in the list.
     *
     * @param element to be inserted
     */
    @Override
    public void addFirst(E element) {
        SinglyListNode<E> newNode = new SinglyListNode<>(element, head);
        if ( this.isEmpty() )
            tail = newNode;
        head = newNode;
        currentSize++;
    }

    /**
     * Inserts the specified element at the last position in the list.
     *
     * @param element to be inserted
     */
    @Override
    public void addLast(E element) {
        if ( this.isEmpty() ) {
            addFirst(element);
            return;
        }
        SinglyListNode<E> newNode = new SinglyListNode<>(element, null);
        tail.setNext(newNode);
        tail = newNode;
        currentSize++;
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
    @Override
    public void add(int position, E element) {
        if ( position < 0 || position > currentSize )
            throw new InvalidPositionException();
        if ( position == 0 )
            this.addFirst(element);
        else if ( position == currentSize )
            this.addLast(element);
        else
            this.addMiddle(position, element);
    }

    private void addMiddle(int position, E element) {
        SinglyListNode<E> prevNode = this.getNode(position - 1);
        SinglyListNode<E> nextNode = prevNode.getNext();
        SinglyListNode<E> newNode = new SinglyListNode<>(element, nextNode);
        prevNode.setNext(newNode);
        currentSize++;
    }



    /**
     * Removes and returns the element at the first position in the list.
     *
     * @return element removed from the first position of the list
     * @throws NoSuchElementException - if size() == 0
     */
    @Override
    public E removeFirst() {
        if ( this.isEmpty() )
            throw new NoSuchElementException();
        E element=head.getElement();
        head = head.getNext();
        if ( head == null )
            tail = null;
        currentSize--;
        return element;
    }

    /**
     * Removes and returns the element at the last position in the list.
     *
     * @return element removed from the last position of the list
     * @throws NoSuchElementException - if size() == 0
     */

    public E removeLast() {
        if ( this.isEmpty() )
            throw new NoSuchElementException();
        if ( size() == 1 )
            return removeFirst();
        E element=tail.getElement();
        tail = getNode(size() - 2);
        tail.setNext(null);
        currentSize--;
        return element;
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
    @Override
    public E remove(int position) {
        if ( position < 0 || position >= currentSize )
            throw new InvalidPositionException();
        if ( position == 0 )
            return this.removeFirst();
        if ( position == currentSize - 1 )
            return this.removeLast();
        return this.removeMiddle(position);
    }

    private E removeMiddle(int position) {
        SinglyListNode<E> prevNode = this.getNode(position - 1);
        SinglyListNode<E> node = prevNode.getNext();
        prevNode.setNext(node.getNext());
        currentSize--;
        return node.getElement();
    }

}

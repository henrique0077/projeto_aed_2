package dataStructures;
import dataStructures.exceptions.NoSuchElementException;

/**
 * Implementation of Two Way Iterator for DLList 
 * @author AED  Team
 * @version 1.0
 * @param <E> Generic Element
 *
 */
class DoublyIterator<E> implements Iterator<E> {
    /**
     * Node with the first element in the iteration.
     */
    private DoublyListNode<E> firstNode;

    /**
     * Node with the next element in the iteration.
     */
    DoublyListNode<E> nextToReturn;


    /**
     * DoublyIterator constructor
     *
     * @param first - Node with the first element of the iteration
     */
    public DoublyIterator(DoublyListNode<E> first) {
        this.firstNode = first;
        nextToReturn = first;
    }
    /**
     * Returns the next element in the iteration.
     *
     * @return the next element in the iteration
     * @throws NoSuchElementException - if call is made without verifying pre-condition
     */
    public E next( ) {
        if (!this.hasNext())
            throw new NoSuchElementException();
        E element = nextToReturn.getElement();
        nextToReturn = nextToReturn.getNext();
        return element;
    }

    /**
     * Restart the iterator
     */
    public void rewind() {
        nextToReturn = firstNode;
    }
    /**
     * Returns true if next would return an element
     * rather than throwing an exception.
     * @return true iff the iteration has more elements
     */
    public boolean hasNext( ) {
        return nextToReturn != null;
    }


}

package dataStructures;

import dataStructures.exceptions.NoSuchElementException;

/**
 * Iterator Abstract Data Type with Filter
 * Includes description of general methods for one way iterator.
 * @author AED  Team
 * @version 1.0
 * @param <E> Generic Element
 *
 */
public class FilterIterator<E> implements Iterator<E> {

    /**
     *  Iterator of elements to filter.
     */
    private Iterator<E> iterator;

    /**
     *  Filter.
     */
    private Predicate<E> filter;

    /**
     * Node with the next element in the iteration.
     */
    private E nextToReturn;

    /**
     *
     * @param list to be iterated
     * @param filter
     */
    public FilterIterator(Iterator<E> list, Predicate<E> filter) {
        this.iterator = list;
        this.filter = filter;
        advanceNext();
    }

    /**
     * Returns true if next would return an element
     *
     * @return true iff the iteration has more elements
     */
    public boolean hasNext() {
        return nextToReturn != null;
    }

    /**
     * Returns the next element in the iteration.
     *
     * @return the next element in the iteration
     * @throws NoSuchElementException - if call is made without verifying pre-condition
     */
    public E next() {
        if (!hasNext()){
            throw new NoSuchElementException();
        }
        E element = nextToReturn;
        advanceNext();
        return element;
    }

    private void advanceNext() {
        nextToReturn = null;
        while (iterator.hasNext() && nextToReturn == null) {
            E element = iterator.next();
            if (filter.check(element)) {
                nextToReturn = element;
                break;
            }
        }
    }

    /**
     * Restarts the iteration.
     * After rewind, if the iteration is not empty, next will return the first element.
     */
    public void rewind() {
        iterator.rewind();
        advanceNext();
    }

}

package dataStructures;
/**
 * SepChain Hash Table Iterator
 * @author AED  Team
 * @version 1.0
 * @param <K> Generic Key
 * @param <V> Generic Value
 */
import dataStructures.exceptions.NoSuchElementException;

class SepChainHashTableIterator<K,V> implements Iterator<Map.Entry<K,V>> {

    private Map<K,V>[] table;
    private int currentBucket;
    private Iterator<Map.Entry<K,V>> currentBucketIterator;

    public SepChainHashTableIterator(Map<K,V>[] table) {
        this.table = table;
        rewind();    }

    /**
     * Returns true if next would return an element
     * rather than throwing an exception.
     *
     * @return true iff the iteration has more elements
     */
    public boolean hasNext() {
        return currentBucketIterator != null;
    }

    /**
     * Returns the next element in the iteration.
     *
     * @return the next element in the iteration
     * @throws NoSuchElementException - if call is made without verifying pre-condition
     */
    public Map.Entry<K,V> next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        Map.Entry<K,V> aux =  currentBucketIterator.next();
        findNextBucket();
        return aux;
    }

    private void findNextBucket() {
        if (currentBucketIterator != null && currentBucketIterator.hasNext()) {
            return;
        }
        currentBucket++;
        while (currentBucket < table.length) {
            if (!table[currentBucket].isEmpty()) {
                currentBucketIterator = table[currentBucket].iterator();
                return;
            }
            currentBucket++;
        }
        currentBucketIterator = null;
    }

    /**
     * Restarts the iteration.
     * After rewind, if the iteration is not empty, next will return the first element.
     */
    public void rewind() {
        currentBucket = -1;
        currentBucketIterator = null;
        findNextBucket();
    }
}


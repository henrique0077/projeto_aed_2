package dataStructures;

import dataStructures.exceptions.NoSuchElementException;

/**
 * Iterador robusto para a SepChainHashTable.
 * Resolve o bug onde o iterador ficava "preso" em buckets vazios.
 */
class SepChainHashTableIterator<K,V> implements Iterator<Map.Entry<K,V>> {

    private Map<K,V>[] table;
    private int currentBucket;
    private Iterator<Map.Entry<K,V>> currentBucketIterator;

    public SepChainHashTableIterator(Map<K,V>[] table) {
        this.table = table;
        rewind();
    }

    public boolean hasNext() {
        return currentBucketIterator != null && currentBucketIterator.hasNext();
    }

    public Map.Entry<K,V> next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        Map.Entry<K,V> entry = currentBucketIterator.next();

        // Se este bucket acabou, procura já o próximo elemento válido
        if (!currentBucketIterator.hasNext()) {
            findNextValidBucket();
        }
        return entry;
    }

    private void findNextValidBucket() {
        currentBucket++;
        while (currentBucket < table.length) {
            if (table[currentBucket] != null && !table[currentBucket].isEmpty()) {
                currentBucketIterator = table[currentBucket].iterator();
                if (currentBucketIterator.hasNext()) {
                    return; // Encontrámos um bucket com dados
                }
            }
            currentBucket++;
        }
        // Se chegámos aqui, não há mais elementos
        currentBucketIterator = null;
    }

    public void rewind() {
        currentBucket = -1;
        currentBucketIterator = null;
        findNextValidBucket(); // Avança logo para o primeiro elemento válido
    }
}
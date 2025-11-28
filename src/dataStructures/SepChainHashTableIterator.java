package dataStructures;

import dataStructures.exceptions.NoSuchElementException;

/**
 * SepChain Hash Table Iterator - VERSÃO CORRIGIDA
 * @author AED  Team
 * @version 1.0
 * @param <K> Generic Key
 * @param <V> Generic Value
 */
class SepChainHashTableIterator<K,V> implements Iterator<Map.Entry<K,V>> {

    private Map<K,V>[] table;
    private int currentBucket;
    private Iterator<Map.Entry<K,V>> currentBucketIterator;

    public SepChainHashTableIterator(Map<K,V>[] table) {
        this.table = table;
        rewind();
    }

    /**
     * Returns true if next would return an element
     * rather than throwing an exception.
     */
    public boolean hasNext() {
        return currentBucketIterator != null;
    }

    /**
     * Returns the next element in the iteration.
     */
    public Map.Entry<K,V> next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        // Guarda o elemento atual
        Map.Entry<K,V> aux = currentBucketIterator.next();

        // Prepara JÁ o próximo elemento (Eager Advance)
        // Isto garante que o hasNext() seguinte responde corretamente
        findNextBucket();

        return aux;
    }

    /**
     * Avança para o próximo bucket que tenha elementos.
     */
    private void findNextBucket() {
        // Se o iterador atual ainda tem elementos, não fazemos nada
        if (currentBucketIterator != null && currentBucketIterator.hasNext()) {
            return;
        }

        // Avança para o próximo bucket
        currentBucket++;
        while (currentBucket < table.length) {
            // Verifica se o bucket existe e não está vazio
            if (table[currentBucket] != null && !table[currentBucket].isEmpty()) {
                currentBucketIterator = table[currentBucket].iterator();
                return; // Encontrámos! Paramos aqui.
            }
            currentBucket++;
        }

        // Se chegámos ao fim da tabela sem encontrar nada
        currentBucketIterator = null;
    }

    /**
     * Restarts the iteration.
     */
    public void rewind() {
        this.currentBucket = -1;
        this.currentBucketIterator = null;
        // Usa a lógica do findNextBucket para encontrar o primeiro elemento válido
        findNextBucket();
    }
}
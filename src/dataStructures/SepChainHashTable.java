package dataStructures;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * SepChain Hash Table
 * @author AED  Team
 * @version 1.0
 * @param <K> Generic Key
 * @param <V> Generic Value
 */
public class SepChainHashTable<K,V> extends HashTable<K,V> implements Serializable {

    //Load factors
    static final float IDEAL_LOAD_FACTOR =0.75f;
    static final float MAX_LOAD_FACTOR =0.9f;

    // The array of Map with singly linked list.
    private transient Map<K,V>[] table;

    public SepChainHashTable( ){
        this(DEFAULT_CAPACITY);
    }
    @SuppressWarnings("unchecked")
    public SepChainHashTable( int capacity ){
        super(capacity);
        int arraySize = HashTable.nextPrime((int) (capacity / IDEAL_LOAD_FACTOR));
        table = (MapSinglyList<K,V>[]) new MapSinglyList[arraySize];
        for ( int i = 0; i < arraySize; i++ )
            table[i] = new MapSinglyList<>();
        maxSize = (int)(arraySize * MAX_LOAD_FACTOR);
    }

/**
    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.defaultWriteObject(); // escreve os campos normais (não temos aqui, mas é boa prática)
        oos.writeFloat(IDEAL_LOAD_FACTOR);
        oos.writeFloat(MAX_LOAD_FACTOR);
        oos.writeInt(currentSize); // escreve o tamanho
        for ( int i = 0; i < table.length; i++ )
            oos.writeObject(table[i]);
        oos.flush();
    }*/

    /**
     * Serializa o estado da Hash Table.
     * Salva a capacidade do array, o número de elementos e os pares chave-valor individualmente.
     */
    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.defaultWriteObject();
        oos.writeInt(table.length);
        oos.writeInt(currentSize);
        for(Map<K,V> bucket : table) {
            Iterator<Entry<K,V>> it = bucket.iterator();
            while(it.hasNext()) {
                Entry<K,V> entry = it.next();
                oos.writeObject(entry.key());   // Serializa apenas a Chave
                oos.writeObject(entry.value()); // Serializa apenas o Valor
            }
        }
        oos.flush();
    }

    /**
     * Reconstrói a Hash Table a partir do stream.
     */
    @SuppressWarnings("unchecked")
    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        int savedCapacity = ois.readInt();
        int savedSize = ois.readInt();
        table = (MapSinglyList<K, V>[]) new MapSinglyList[savedCapacity];
        for (int i = 0; i < savedCapacity; i++) {
            table[i] = new MapSinglyList<>();
        }
        maxSize = (int)(savedCapacity * MAX_LOAD_FACTOR);
        currentSize = 0;
        for (int i = 0; i < savedSize; i++) {
            K key = (K) ois.readObject();     // Lê a Chave
            V value = (V) ois.readObject();   // Lê o Valor
            int index = hash(key);
            table[index].put(key, value);
            currentSize++;
        }
    }


    // Returns the hash value of the specified key.
    protected int hash( K key ){
        return Math.abs( key.hashCode() ) % table.length;
    }
    /**
     * If there is an entry in the dictionary whose key is the specified key,
     * returns its value; otherwise, returns null.
     *
     * @param key whose associated value is to be returned
     * @return value of entry in the dictionary whose key is the specified key,
     * or null if the dictionary does not have an entry with that key
     */
    public V get(K key) {
        int position = hash(key);
        Map<K,V> bucket = table[position];
        return bucket.get(key);
    }

    /**
     * If there is an entry in the dictionary whose key is the specified key,
     * replaces its value by the specified value and returns the old value;
     * otherwise, inserts the entry (key, value) and returns null.
     *
     * @param key   with which the specified value is to be associated
     * @param value to be associated with the specified key
     * @return previous value associated with key,
     * or null if the dictionary does not have an entry with that key
     */
    public V put(K key, V value) {
        if (isFull())
            rehash();
        int index = hash(key);
        V aux = get(key);
        if (aux != null) {
            Map<K,V> bucket = table[index];
            bucket.put(key, value);
            return aux;
        }
        Map<K, V> bucket = table[index];
        bucket.put(key, value);
        currentSize++;
        return null;
    }

    @SuppressWarnings("unchecked")
    private void rehash() {
        Map<K,V>[] oldTable = table;
        int newCapacity = HashTable.nextPrime(table.length * 2);
        table = (MapSinglyList<K,V>[]) new MapSinglyList[newCapacity];
        for (int i = 0; i < newCapacity; i++) {
            table[i] = new MapSinglyList<>();
        }
        currentSize = 0;
        for (Map<K,V> bucket : oldTable) {
            if (bucket != null) {
                Iterator<Entry<K,V>> it = bucket.iterator();
                while (it.hasNext()) {
                    Entry<K,V> entry = it.next();
                    K key = entry.key();
                    V value = entry.value();
                    int newIndex = Math.abs(key.hashCode()) % newCapacity;
                    table[newIndex].put(key, value);
                    currentSize++;
                }
            }
        }
        maxSize = (int)(newCapacity * MAX_LOAD_FACTOR);}

    /**
     * If there is an entry in the dictionary whose key is the specified key,
     * removes it from the dictionary and returns its value;
     * otherwise, returns null.
     *
     * @param key whose entry is to be removed from the map
     * @return previous value associated with key,
     * or null if the dictionary does not an entry with that key
     */
    public V remove(K key) {
        int index = hash(key);
        V oldValue = table[index].remove(key);
        if (oldValue != null)
            currentSize--;
        return oldValue;
    }

    /**
     * Returns an iterator of the entries in the dictionary.
     *
     * @return iterator of the entries in the dictionary
     */
    public Iterator<Entry<K, V>> iterator() {
        return new SepChainHashTableIterator<>(table);
    }


}

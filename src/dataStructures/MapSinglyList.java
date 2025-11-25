package dataStructures;
/**
 * Map with a singly linked list with head and size
 * @author AED  Team
 * @version 1.0
 * @param <K> Generic Key
 * @param <V> Generic Value
 */
public class MapSinglyList<K,V> implements Map<K, V> {


    private SinglyListNode<Entry<K,V>> head;

    private int size;

    public MapSinglyList() {
        head = null;
        size = 0;
    }

    /**
     * Returns true iff the dictionary contains no entries.
     *
     * @return true if dictionary is empty
     */
  
    public boolean isEmpty() {
        return head == null;
    }

    /**
     * Returns the number of entries in the dictionary.
     *
     * @return number of elements in the dictionary
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * If there is an entry in the dictionary whose key is the specified key,
     * returns its value; otherwise, returns null.
     *
     * @param key whose associated value is to be returned
     * @return value of entry in the dictionary whose key is the specified key,
     * or null if the dictionary does not have an entry with that key
     */
    @Override
    public V get(K key) {
        SinglyListNode<Entry<K,V>> current = findNode(key);
        if (current != null)
            return current.getElement().value();
        return null;
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
        SinglyListNode<Entry<K,V>> current = findNode(key);
        Entry<K,V> entry = new Entry<>(key, value);
        if (current != null) {
            V aux = current.getElement().value();
            current.setElement(entry);
            return aux;
        }
        else {
            head = new SinglyListNode<>(entry, head);
            size++;
            return null;
        }
    }

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
        SinglyListNode<Entry<K,V>> current = head;
        if (current == null) {
            return null;
        }
        if (current.getElement().key().equals(key)) {
            V aux = current.getElement().value();
            head = current.getNext();
            size--;
            return aux;
        }
        while (current.getNext() != null){
            if (current.getNext().getElement().key().equals(key)) {
                V aux = current.getNext().getElement().value();
                current.setNext(current.getNext().getNext());
                size--;
                return aux;
            }
            current = current.getNext();
        }
        return null;
    }

    private SinglyListNode<Entry<K,V>> findNode(K key) {
        SinglyListNode<Entry<K,V>> node = head;
        while (node != null){
            if (node.getElement().key().equals(key)){
                return node;
            }
            node = node.getNext();
        }
        return null;
    }

    /**
     * Returns an iterator of the entries in the dictionary.
     *
     * @return iterator of the entries in the dictionary
     */
    public Iterator<Entry<K, V>> iterator() {
        return new SinglyIterator<>(head);
    }

    /**
     * Returns an iterator of the values in the dictionary.
     *
     * @return iterator of the values in the dictionary
     */
@SuppressWarnings({"unchecked","rawtypes"})
    public Iterator<V> values() {
        return new ValuesIterator(iterator());
    }

    /**
     * Returns an iterator of the keys in the dictionary.
     *
     * @return iterator of the keys in the dictionary
     */
@SuppressWarnings({"unchecked","rawtypes"})
    public Iterator<K> keys() {
        return new KeysIterator(iterator());
    }

}

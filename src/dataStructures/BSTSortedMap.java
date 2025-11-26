package dataStructures;

import dataStructures.exceptions.EmptyMapException;

import java.io.Serializable;

/**
 * Binary Search Tree Sorted Map
 * @author AED  Team
 * @version 1.0
 * @param <K> Generic Key
 * @param <V> Generic Value
 */
public class BSTSortedMap<K extends Comparable<K>,V> extends BTree<Map.Entry<K,V>> implements SortedMap<K,V> , Serializable {

    /**
     * Constructor
     */
    public BSTSortedMap(){
        super();
    }
    /**
     * Returns the entry with the smallest key in the dictionary.
     *
     * @return
     * @throws EmptyMapException
     */
    @Override
    public Entry<K, V> minEntry() {
        if (isEmpty())
            throw new EmptyMapException();
        return furtherLeftElement().getElement();
    }

    /**
     * Returns the entry with the largest key in the dictionary.
     *
     * @return
     * @throws EmptyMapException
     */
    @Override
    public Entry<K, V> maxEntry() {
        if (isEmpty())
            throw new EmptyMapException();
        return furtherRightElement().getElement();
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
        Node<Entry<K,V>> node=getNode((BTNode<Entry<K,V>>)root,key);
        if (node!=null)
            return node.getElement().value();
        return null;
    }

    private BTNode<Entry<K,V>> getNode(BTNode<Entry<K,V>> node, K key) {
        BTNode<Entry<K,V>> current = node;
        while (current!=null) {
            Entry<K,V> currentEntry = current.getElement();
            K currentKey = currentEntry.key();
            if (key.compareTo(currentKey) == 0)
                return current;
            else if (key.compareTo(currentKey) < 0)
                current = (BTNode<Entry<K, V>>) current.getLeftChild();
            else
                current = (BTNode<Entry<K, V>>) current.getRightChild();
        }
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
    @Override
    public V put(K key, V value) {
        Entry<K,V> entry = new Entry<>(key, value);

        // Caso 1: Árvore vazia
        if (root == null) {
            root = new BTNode<>(entry);
            currentSize++;
            return null;
        }

        // Procura a posição para inserir ou atualizar
        BTNode<Entry<K,V>> current = (BTNode<Entry<K,V>>) root;
        BTNode<Entry<K,V>> parent = null;
        boolean isLeftChild = false;

        while (current != null) {
            parent = current;
            int comparison = key.compareTo(current.getElement().key());

            if (comparison == 0) {
                // Chave encontrada - atualiza o valor
                V oldValue = current.getElement().value();
                current.setElement(entry);
                return oldValue;
            }
            else if (comparison < 0) {
                // Vai para a esquerda
                current = (BTNode<Entry<K,V>>) current.getLeftChild();
                isLeftChild = true;
            }
            else {
                // Vai para a direita
                current = (BTNode<Entry<K,V>>) current.getRightChild();
                isLeftChild = false;
            }
        }

        // Chave não existe - cria novo nó
        BTNode<Entry<K,V>> newNode = new BTNode<>(entry);
        newNode.setParent(parent);

        // Insere como filho esquerdo ou direito do parent
        if (isLeftChild) {
            parent.setLeftChild(newNode);
        } else {
            parent.setRightChild(newNode);
        }

        currentSize++;
        return null;
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
    @Override
    public V remove(K key) {
        BTNode<Entry<K,V>> nodeToRemove = getNode((BTNode<Entry<K,V>>)root,key);
        if (nodeToRemove==null) {
            return null;
        }
        V value =  nodeToRemove.getElement().value();
        if (nodeToRemove.getLeftChild()!=null && nodeToRemove.getRightChild()!=null) {
            removeNodeWithTwoChildren(nodeToRemove);
        }
        else if (nodeToRemove.isLeaf())
            removeLeafNode(nodeToRemove);
        else
            removeNodeWithOneChild(nodeToRemove);
        currentSize--;
        return value;
    }

    private void removeLeafNode(BTNode<Entry<K, V>> node) {
        if (node.isRoot()) {
            root = null;
        } else {
            BTNode<Entry<K, V>> parent = (BTNode<Entry<K, V>>) node.getParent();
            if (parent.getLeftChild() == node) {
                parent.setLeftChild(null);
            } else {
                parent.setRightChild(null);
            }
        }
    }

    private void removeNodeWithOneChild(BTNode<Entry<K, V>> node) {
        BTNode<Entry<K, V>> child;
        if (node.getLeftChild() != null){
            child = (BTNode<Entry<K, V>>) node.getLeftChild();
        } else {
            child = (BTNode<Entry<K, V>>) node.getRightChild();
        }
        if (node.isRoot()) {
            root = child;
            if (child != null) {
                child.setParent(null);
            }
        }
        else {
            BTNode<Entry<K, V>> parent = (BTNode<Entry<K, V>>) node.getParent();
            if (parent.getLeftChild() == node) {
                parent.setLeftChild(child);
            }
            else {
                parent.setRightChild(child);
            }
        }
    }

    private void removeNodeWithTwoChildren(BTNode<Entry<K, V>> node) {
        // Encontrar o sucessor in-order (menor elemento da subárvore direita)
        BTNode<Entry<K, V>> successor = (BTNode<Entry<K, V>>) node.getRightChild();
        while (successor.getLeftChild() != null) {
            successor = (BTNode<Entry<K, V>>) successor.getLeftChild();
        }
        BTNode<Entry<K, V>> successorParent = (BTNode<Entry<K, V>>) successor.getParent();

        // Copiar o elemento do sucessor para o nó a remover
        node.setElement(successor.getElement());

        // Remover o sucessor da árvore
        // O sucessor é sempre o filho mais à esquerda, então só pode ter filho direito
        if (successorParent.getLeftChild() == successor) {
            // Sucessor é filho esquerdo do seu pai
            successorParent.setLeftChild(successor.getRightChild());
        } else {
            // Sucessor é filho direito do seu pai (caso onde não há filho esquerdo)
            successorParent.setRightChild(successor.getRightChild());
        }

        // Atualizar o pai do filho direito do sucessor (se existir)
        if (successor.getRightChild() != null) {
            ((BTNode<Entry<K, V>>) successor.getRightChild()).setParent(successorParent);
        }
    }

        /**
         * Returns an iterator of the entries in the dictionary.
         *
         * @return iterator of the entries in the dictionary
         */
    @Override
    public Iterator<Entry<K, V>> iterator() {
        return new InOrderIterator<>((BTNode<Entry<K,V>>) root);
    }

    /**
     * Returns an iterator of the values in the dictionary.
     *
     * @return iterator of the values in the dictionary
     */
    @Override
@SuppressWarnings({"unchecked","rawtypes"})
    public Iterator<V> values() {
        return new ValuesIterator(iterator());
    }

    /**
     * Returns an iterator of the keys in the dictionary.
     *
     * @return iterator of the keys in the dictionary
     */
    @Override
@SuppressWarnings({"unchecked","rawtypes"})
    public Iterator<K> keys() {
        return new KeysIterator(iterator());
    }
}

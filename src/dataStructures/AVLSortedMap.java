package dataStructures;
/**
 * AVL Tree Sorted Map
 * @author AED  Team
 * @version 1.0
 * @param <K> Generic Key
 * @param <V> Generic Value
 */
public class AVLSortedMap <K extends Comparable<K>,V> extends AdvancedBSTree<K,V>{
    /**
     * 
     * @param key
     * @param value
     * @return
     */
    public V put(K key, V value) {
        V oldValue = get(key);
        root = putNode((AVLNode<Entry<K, V>>) root, key, value, null);
        if (oldValue == null) currentSize++;
        return oldValue;
    }

    private AVLNode<Entry<K, V>> putNode(AVLNode<Entry<K, V>> node, K key, V value, AVLNode<Entry<K, V>> parent) {
        Entry<K,V> entry = new Entry<>(key, value);
        if (node == null) {
            return new AVLNode<>(new Entry<>(key, value), parent);
        }

        int comparison = key.compareTo(node.getElement().key());

        if (comparison == 0) {
            node.setElement(entry);
        }
        else if (comparison < 0) {
            AVLNode<Entry<K, V>> leftChild = (AVLNode<Entry<K, V>>) node.getLeftChild();
            node.setLeftChild(putNode(leftChild, key, value, node));
        }
        else {
            AVLNode<Entry<K, V>> rightChild = (AVLNode<Entry<K, V>>) node.getRightChild();
            node.setRightChild(putNode(rightChild, key, value, node));
        }

        node.updateHeight();
        return rebalanceFromNode(node);
    }

    /**
     * Reorganiza a árvore percorrendo todos os nós do caminho
     * que começa no pai do nó inserido e termina na raiz
     */
    private AVLNode<Entry<K, V>> rebalanceFromNode(AVLNode<Entry<K, V>> node) {
        int balance = node.getBalance();

        if (balance > 1) {
            AVLNode<Entry<K, V>> leftChild = (AVLNode<Entry<K, V>>) node.getLeftChild();
            AVLNode<Entry<K, V>> x;
            if (leftChild.getBalance() < 0) {
                x = (AVLNode<Entry<K, V>>) leftChild.getRightChild();
            } else {
                x = leftChild;
            }
            return (AVLNode<Entry<K, V>>) restructure(x);
        }

        if (balance < -1) {
            AVLNode<Entry<K, V>> rightChild = (AVLNode<Entry<K, V>>) node.getRightChild();
            AVLNode<Entry<K, V>> x;
            if (rightChild.getBalance() > 0) {
                x = (AVLNode<Entry<K, V>>) rightChild.getLeftChild();
            } else {
                x = rightChild;
            }
            return (AVLNode<Entry<K, V>>) restructure(x);
        }

        return node;
    }




    /**
     *
     * @param key whose entry is to be removed from the map
     * @return
     */
    public V remove(K key) {
        AVLNode<Entry<K,V>> nodeToRemove = getNode((AVLNode<Entry<K,V>>)root,key);
        if (nodeToRemove==null) {
            return null;
        }
        V value =  nodeToRemove.getElement().value();
        AVLNode<Entry<K,V>> parentToRebalance;

        if (nodeToRemove.getLeftChild()!=null && nodeToRemove.getRightChild()!=null) {
            parentToRebalance = removeNodeWithTwoChildren(nodeToRemove);
        }
        else if (nodeToRemove.isLeaf()) {
            parentToRebalance = (AVLNode<Entry<K,V>>) nodeToRemove.getParent();
            removeLeafNode(nodeToRemove);
        }
        else {
            parentToRebalance = (AVLNode<Entry<K,V>>) nodeToRemove.getParent();
            removeNodeWithOneChild(nodeToRemove);
        }

        currentSize--;

        if (parentToRebalance != null) {
            rebalanceFromNode(parentToRebalance);
        } else if (root != null) {
            rebalanceFromNode((AVLNode<Entry<K,V>>) root);
        }

        return value;
    }

    private void removeLeafNode(AVLNode<Entry<K, V>> node) {
        if (node.isRoot()) {
            root = null;
        } else {
            AVLNode<Entry<K, V>> parent = (AVLNode<Entry<K, V>>) node.getParent();
            if (parent.getLeftChild() == node) {
                parent.setLeftChild(null);
            } else {
                parent.setRightChild(null);
            }
        }
    }

    private void removeNodeWithOneChild(AVLNode<Entry<K, V>> node) {
        AVLNode<Entry<K, V>> child;
        if (node.getLeftChild() != null){
            child = (AVLNode<Entry<K, V>>) node.getLeftChild();
        } else {
            child = (AVLNode<Entry<K, V>>) node.getRightChild();
        }
        if (node.isRoot()) {
            root = child;
            if (child != null) {
                child.setParent(null);
            }
        }
        else {
            AVLNode<Entry<K, V>> parent = (AVLNode<Entry<K, V>>) node.getParent();
            if (parent.getLeftChild() == node) {
                parent.setLeftChild(child);
            }
            else {
                parent.setRightChild(child);
            }
        }
    }

    private AVLNode<Entry<K,V>> removeNodeWithTwoChildren(AVLNode<Entry<K, V>> node) {
        // Encontrar o sucessor in-order (menor elemento da subárvore direita)
        AVLNode<Entry<K, V>> successor = (AVLNode<Entry<K, V>>) node.getRightChild();
        while (successor.getLeftChild() != null) {
            successor = (AVLNode<Entry<K, V>>) successor.getLeftChild();
        }
        AVLNode<Entry<K, V>> successorParent = (AVLNode<Entry<K, V>>) successor.getParent();

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
            ((AVLNode<Entry<K, V>>) successor.getRightChild()).setParent(successorParent);
        }
        return successorParent;
    }

    private AVLNode<Entry<K,V>> getNode(AVLNode<Entry<K,V>> node, K key) {
        AVLNode<Entry<K,V>> current = node;
        while (current!=null) {
            Entry<K,V> currentEntry = current.getElement();
            K currentKey = currentEntry.key();
            if (key.compareTo(currentKey) == 0)
                return current;
            else if (key.compareTo(currentKey) < 0)
                current = (AVLNode<Entry<K, V>>) current.getLeftChild();
            else
                current = (AVLNode<Entry<K, V>>) current.getRightChild();
        }
        return null;
    }


}

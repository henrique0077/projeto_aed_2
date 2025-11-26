package dataStructures;
/**
 * Tree
 * @author AED  Team
 * @version 1.0
 * @param <E> Generic element
 */
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.io.IOException;

abstract class Tree<E> implements Serializable {

    /**
     * Root
     */
    protected transient Node<E> root;

    /**
     * Number of elements
     */
    protected transient int currentSize;

    public Tree(){
        root=null;
        currentSize=0;
    }

    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.defaultWriteObject();

        oos.writeInt(currentSize);

        oos.flush();
    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();

        this.currentSize = ois.readInt();

        this.root = null;
    }

    /**
     * Returns true iff the dictionary contains no entries.
     *
     * @return true if dictionary is empty
     */
    public boolean isEmpty() {
        return currentSize==0;
    }

    /**
     * Returns the number of entries in the dictionary.
     *
     * @return number of elements in the dictionary
     */
    public int size() {
        return currentSize;
    }


    /**
     * Return the root of the tree
     * @return
     */
    Node<E> root(){ return root;}

}
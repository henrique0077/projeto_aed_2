package dataStructures;
/**
 * Advanced Binary Search Tree
 * @author AED  Team
 * @version 1.0
 * @param <K> Generic Key
 * @param <V> Generic Value
 */
abstract class AdvancedBSTree <K extends Comparable<K>,V> extends BSTSortedMap<K,V>{
      /**
 	* Performs a single left rotation rooted at z node.
 	* Node y was a  right  child  of z before the  rotation,
 	* then z becomes the left child of y after the rotation.
 	* @param z - root of the rotation
	 * @pre: z has a right child
 	*/
	protected void rotateLeft( BTNode<Entry<K,V>> z) {
        BTNode<Entry<K, V>> theParent = (BTNode<Entry<K, V>>) z.getParent();
        BTNode<Entry<K, V>> y = (BTNode<Entry<K, V>>) z.getRightChild();
        BTNode<Entry<K, V>> newZRightChild = (BTNode<Entry<K, V>>) y.getLeftChild();

        z.setRightChild(newZRightChild);
        if (newZRightChild != null)
            newZRightChild.setParent(z);
        y.setLeftChild(z);

        y.setParent(theParent);

        if (theParent != null) {
            int value = findHasParent(z);
            if (value == 0) {
                theParent.setLeftChild(y);
            } else if (value == 1) {
                theParent.setRightChild(y);
            }
        } else {
            this.root = y;
        }

   	 //TODO: Left as an exercise.
   	 //  a single rotation modifies a constant number of parent-child relationships,
    	// it can be implemented in O(1)time
	}

     /**
     * Performs a single right rotation rooted at z node.
     * Node y was a left  child  of z before the  rotation,
     * then z becomes the right child of y after the rotation.
     * @param z - root of the rotation
     * @pre: z has a left child
     */
    protected void rotateRight( BTNode<Entry<K,V>> z){
        BTNode<Entry<K, V>> theParent = (BTNode<Entry<K, V>>) z.getParent();
        BTNode<Entry<K, V>> y = (BTNode<Entry<K, V>>) z.getLeftChild();
        BTNode<Entry<K, V>> newZLeftChild = (BTNode<Entry<K, V>>) y.getRightChild();

        z.setLeftChild(newZLeftChild);
        if (newZLeftChild != null)
            newZLeftChild.setParent(z);
        y.setRightChild(z);

        y.setParent(theParent);

        if (theParent != null) {
            int value = findHasParent(z);
            if (value == 0) {
                theParent.setLeftChild(y);
            } else if (value == 1) {
                theParent.setRightChild(y);
            }
        } else {
            this.root = y;
        }

        //TODO: Left as an exercise.
        //  a single rotation modifies a constant number of parent-child relationships,
        // it can be implemented in O(1)time
    }

    /**
     * Performs a tri-node restructuring (a single or double rotation rooted at X node).
     * Assumes the nodes are in one of following configurations:
     *
     * @param x - root of the rotation
     * <pre>
     *          z=c       z=c        z=a         z=a
     *          /  \      /  \       /  \        /  \
     *        y=b  t4   y=a  t4    t1  y=c     t1  y=b
     *       /  \      /  \           /  \         /  \
     *     x=a  t3    t1 x=b        x=b  t4       t2 x=c
     *    /  \          /  \       /  \             /  \
     *   t1  t2        t2  t3     t2  t3           t3  t4
     * </pre>
     * @return the new root of the restructured subtree
     */
    protected BTNode<Entry<K,V>> restructure (BTNode<Entry<K,V>> x) {
        if (x == null || x.getParent() == null || ((BTNode<Entry<K, V>>)x.getParent()).getParent() == null)
            return x;

        BTNode<Entry<K, V>> parent = (BTNode<Entry<K, V>>) x.getParent();
        BTNode<Entry<K, V>> grandParent = (BTNode<Entry<K, V>>) parent.getParent();

        boolean parentIsLeftChild = (grandParent.getLeftChild() == parent);
        boolean xIsLeftChild = (parent.getLeftChild() == x);

        if (parentIsLeftChild) {
            if (xIsLeftChild) {
                rotateRight(grandParent);
                return parent;
            } else {
                rotateLeft(parent);
                rotateRight(grandParent);
                return x;
            }
        } else {
            if (x == parent.getRightChild()) {
                rotateLeft(grandParent);
                return parent;
            } else {
                rotateRight(parent);
                rotateLeft(grandParent);
                return x;
            }
        }
        //TODO: Left as an exercise.
        // the modification of a tree T caused by a trinode restructuring operation
        // can be implemented through case analysis either as a single rotation or as a double rotation.
        // The double rotation arises when position x has the middle of the three relevant keys
        // and is first rotated above its parent Y, and then above what was originally its grandparent Z.
        // In any of the cases, the trinode restructuring is completed with O(1)running time
    }

    private int findHasParent( BTNode<Entry<K,V>> z){
        BTNode<Entry<K,V>> parent = (BTNode<Entry<K,V>>) z.getParent();
        if (parent.getLeftChild()==z){
            return 0; //0 = é filho esquerdo
        }
        else if (parent.getRightChild()==z){
            return 1; //1 = é filho direito
        }
        return -1; //-1 = não existe, logo é raíz
    }
}

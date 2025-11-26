package dataStructures;

import java.io.Serializable;

/**
 * AVL Tree Node
 * @author AED  Team
 * @version 1.0
 * @param <E> Generic Element
 */
class AVLNode<E> extends BTNode<E> {
    // Height of the node
    protected int height; // era protected

    public AVLNode(E elem) {
        super(elem);
        height=0;
    }
    
    public AVLNode( E element, AVLNode<E> parent, AVLNode<E> left, AVLNode<E> right ){
        super(element,parent,left,right);
        height= 0;
    }
    public AVLNode( E element, AVLNode<E> parent){
        super(element, parent,null, null);
        height= 0;
    }

    private int height(AVLNode<E> no) {
        if (no==null)	return -1;
        return no.getHeight();
    }
    public int getHeight() {
        return height;
    }

    /**
     * Update the left child and height
     * @param node
     */
    public void setLeftChild(AVLNode<E> node) {
        super.setLeftChild(node);
        if (node != null) {
            node.setParent(this);
        }
        updateHeight();
    }

    /**
     * Update the right child and height
     * @param node
     */
    public void setRightChild(AVLNode<E> node) {
        super.setRightChild(node);
        if (node != null) {
            node.setParent(this);
        }
        updateHeight();
    }
// others public methods
//TODO: Left as an exercise.

    /**
     * Update the height of this node based on children's heights
     */
    public void updateHeight() {
        this.height = Math.max(height((AVLNode<E>) getLeftChild()),
                height((AVLNode<E>) getRightChild())) + 1;
    }

    /**
     * Get balance factor of this node
     * @return balance factor (left height - right height)
     */
    public int getBalance() {
        return height((AVLNode<E>) getLeftChild()) - height((AVLNode<E>) getRightChild());
    }

    /**
     * Returns the taller child of this node
     * @return the child with greater height, or left child if equal heights
     */
    public AVLNode<E> getTallerChild() {
        AVLNode<E> leftChild = (AVLNode<E>) this.getLeftChild();
        AVLNode<E> rightChild = (AVLNode<E>) this.getRightChild();

        int leftHeight;
        if (leftChild != null) {
            leftHeight = leftChild.getHeight();
        } else {
            leftHeight = -1;
        }

        int rightHeight;
        if (rightChild != null) {
            rightHeight = rightChild.getHeight();
        } else {
            rightHeight = -1;
        }

        if (leftHeight >= rightHeight) {
            return leftChild;
        } else
            return rightChild;
    }
}



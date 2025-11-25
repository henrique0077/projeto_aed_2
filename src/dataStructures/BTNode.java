package dataStructures;

/**
 * Binary Tree Node
 * @author AED  Team
 * @version 1.0
 * @param <E> Generic Element
 */
class BTNode<E> implements Node<E> {
    // Element stored in the node.
    private E element;

    // (Pointer to) the father.
    private Node<E> parent;

    // (Pointer to) the left child.
    private Node<E> leftChild;

    // (Pointer to) the right child.
    private Node<E> rightChild;

    /**
     * Constructor
     * @param elem
     */
    BTNode(E elem){
        this(elem,null,null,null);
    }

    /**
     * Constructor
     * @param elem
     * @param parent
     */
    BTNode(E elem, BTNode<E> parent) {
        this(elem,parent,null,null);
    }
    /**
     * Constructor
     * @param elem
     * @param parent
     * @param leftChild
     * @param rightChild
     */
    BTNode(E elem, BTNode<E> parent, BTNode<E> leftChild, BTNode<E> rightChild){
        this.element = elem;
        this.parent = parent;
        this.leftChild = leftChild;
        this.rightChild = rightChild;
    }

    /**
     *  Returns the element of the node
     * @return
     */
    public E getElement() {
        return element;
    }
    /**
     * Returns the left son of node
     * @return
     */
    public Node<E> getLeftChild(){
        return leftChild;
    }
    /**
     * Returns the right son of node
     * @return
     */
    public Node<E> getRightChild(){
        return rightChild;
    }
    /**
     * Returns the parent of node
     * @return
     */
    public Node<E> getParent(){
        return parent;
    }

    /**
     * Returns true if node n does not have any children.
     * @return
     */
    boolean isLeaf() {
        return getLeftChild()== null && getRightChild()==null;
    }

    /**
     * Update the element
     * @param elem
     */
    public void setElement(E elem) {
        element=elem;
    }

    /**
     * Update the left child
     * @param node
     */
    public void setLeftChild(Node<E> node) {
        leftChild=node;
    }

    /**
     * Update the right child
     * @param node
     */
    public void setRightChild(Node<E> node) {
        rightChild=node;
    }

    /**
     * Update the parent
     * @param node
     */
    public void setParent(Node<E> node) {
        parent=node;
    }

    /**
     * Returns true if is the root
     */
    boolean isRoot() {
        return getParent()==null;
    }

    /**
     * Returns the height of the subtree rooted at this node.
     */

    public int getHeight() {
        int leftHeight = -1;
        int rightHeight = -1;

        if (leftChild != null)
            leftHeight = ((BTNode<E>) leftChild).getHeight();

        if (rightChild != null)
            rightHeight = ((BTNode<E>) rightChild).getHeight();

        return Math.max(leftHeight, rightHeight) + 1;//a altura do filho mais a root
    }

    /**
     *
     * @return The furthest element on the left side
     */
     public BTNode<E> furtherLeftElement() {   //será que é preciso meter a público e adicionar na
        BTNode<E> current = this;      //interface, para funcionar no Advance do
        while (current.getLeftChild() != null) {    //InOrderIterator?
            current = (BTNode<E>) current.getLeftChild();
        }
        return current;
    }

   /**
     *
     * @return The furthest element on the right side
     */
   BTNode<E> furtherRightElement() {
       BTNode<E> current = this;
       while (current.getRightChild() != null) {
           current = (BTNode<E>) current.getRightChild();
       }
       return current;
   }

    /**
     * Returns true if this node has a left child.
     */
    public boolean hasLeftChild() {
        return leftChild != null;
    }

    /**
     * Returns true if this node has a right child.
     */
    public boolean hasRightChild() {
        return rightChild != null;
    }

    /**
     * Returns the number of children of this node.
     */
    public int getNumberOfChildren() {
        int count = 0;
        if (leftChild != null) count++;
        if (rightChild != null) count++;
        return count;
    }

    /**
     * Returns the sibling of this node, or null if none exists.
     */
    public BTNode<E> getSibling() {
        if (parent == null) return null;

        BTNode<E> parentNode = (BTNode<E>) parent;
        if (parentNode.getLeftChild() == this) {
            return (BTNode<E>) parentNode.getRightChild();
        } else {
            return (BTNode<E>) parentNode.getLeftChild();
        }
    }
}

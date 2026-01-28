public class BinNode<T> {
    private T value; // Node value
    private BinNode<T> left;
    private BinNode<T> right;

    /*
     * Constructor - returns a Node with "value" as value and without successesor
     * Node
     **/
    public BinNode(T value) {
        this.value = value;
    }

    /*
     * Constructor - returns a Node with "value" as value and its successesor is
     * "next"
     **/
    public BinNode(BinNode<T> left, T value, BinNode<T> right) {
        this.value = value;
        this.right = right;
        this.left = left;
    }

    public BinNode<T> getRight() {
        return right;
    }

    public void setRight(BinNode<T> right) {
        this.right = right;
    }

    public boolean hasRight() {
        return right != null;
    }

    public BinNode<T> getLeft() {
        return left;
    }

    public void setLeft(BinNode<T> left) {
        this.left = left;
    }

    public boolean hasLeft() {
        return left != null;
    }

    /* Returns the Node "value" **/
    public T getValue() {
        return this.value;
    }

    /* Set the value attribute to be "value" **/
    public void setValue(T value) {
        this.value = value;
    }

    public String toString_DList() {
        return value + " <=> " + right;
    }

    /* Returns in-order string representation of the tree **/
    @Override
    public String toString() {
        // if leaf avoid recursion
        if (left == null && right == null)
            return value.toString();
        return "( " + left + " " + value + " " + right + " )";
    }

    public void display() {
        System.out.println("--------------Tree-------------");
        // 1. Process Right Subtree (Top)
        if (right != null) {
            display(right, "", false);
        }

        // 2. Process Root (No prefix, just value)
        System.out.println(value);

        // 3. Process Left Subtree (Bottom)
        if (left != null) {
            display(left, "", true);
        }
        System.out.println("--------------Tree-------------");
    }

    /*
     * Helper for recursive printing
     * isLeft = true means the node is a bottom child (└──)
     * isLeft = false means the node is a top child (┌──)
     */
    private void display(BinNode<T> node, String prefix, boolean isLeft) {
        // 1. Recurse Right (Top child of this node)
        if (node.hasRight()) {
            // If we are the bottom child (isLeft), the vertical bar | passes through us to
            // the top
            display(node.getRight(), prefix + (isLeft ? "│   " : "    "), false);
        }

        // 2. Print Current Node
        System.out.println(prefix + (isLeft ? "└── " : "┌── ") + node.getValue());

        // 3. Recurse Left (Bottom child of this node)
        if (node.hasLeft()) {
            // If we are the top child (!isLeft), the vertical bar | passes through us to
            // the bottom
            display(node.getLeft(), prefix + (isLeft ? "    " : "│   "), true);
        }
    }

}

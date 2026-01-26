import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Main {
public static void main(String[] args) {
        BinNode<Integer> t = createSampleTree();
        BinNode<Integer> t2 = createBigTree();
        BinNode<Integer> t3 = createBigNonSearchTree();
        t.display();
        t2.display();
        t3.display();
        System.out.printf("ex_14:the tree has %s leaves\n", ex_14(t));
        System.out.printf("ex_18:the tree t2 %s t\n", ex_18(t2, t2) ? "contains" : "does not contain");
        System.out.printf("the positive numbers sum - the negative numbers sum = %s\n", ex_20(t3));
    }

    public static BinNode<Integer> createSampleTree() {
        // 1. Create the bottom-most layer (Level 4)
        BinNode<Integer> deepLeaf1 = new BinNode<>(1); // Child for 2
        BinNode<Integer> deepLeaf2 = new BinNode<>(6); // Left child for 7
        BinNode<Integer> deepLeaf3 = new BinNode<>(8); // Right child for 7

        // 2. Create the middle layer (Level 3)
        // Node 2: Has '1' as left child, no right child
        BinNode<Integer> node2 = new BinNode<>(deepLeaf1, 2, null);

        // Node 7: Has '6' and '8' as children
        BinNode<Integer> node7 = new BinNode<>(deepLeaf2, 7, deepLeaf3);

        // These remain leaves for now
        BinNode<Integer> leaf12 = new BinNode<>(12);
        BinNode<Integer> leaf20 = new BinNode<>(20);

        // 3. Create the sub-parents (Level 2)
        BinNode<Integer> subLeft = new BinNode<>(node2, 5, node7);
        BinNode<Integer> subRight = new BinNode<>(leaf12, 15, leaf20);

        // 4. Create the root (Level 1)
        return new BinNode<>(subLeft, 10, subRight);
    }

    public static BinNode<Integer> createBigTree() {
        BinNode<Integer> leaf1 = new BinNode<>(1);
        BinNode<Integer> leaf3 = new BinNode<>(3);
        BinNode<Integer> leaf6 = new BinNode<>(6);
        BinNode<Integer> leaf8 = new BinNode<>(8);
        BinNode<Integer> leaf30 = new BinNode<>(30);
        BinNode<Integer> node2 = new BinNode<>(leaf1, 2, leaf3);
        BinNode<Integer> node7 = new BinNode<>(leaf6, 7, leaf8);
        BinNode<Integer> node25 = new BinNode<>(null, 25, leaf30);
        BinNode<Integer> leaf12 = new BinNode<>(12);
        BinNode<Integer> subLeft = new BinNode<>(node2, 5, node7);
        BinNode<Integer> node20 = new BinNode<>(null, 20, node25);
        BinNode<Integer> subRight = new BinNode<>(leaf12, 15, node20);
        return new BinNode<>(subLeft, 10, subRight);
    }

    public static BinNode<Integer> createBigNonSearchTree() {
        // --- Step 1: Create new "Bad" Nodes (breaking BST rules) ---

        // A negative number (-99).
        // We will put this on the RIGHT of 20. (Error: Right child must be larger than
        // parent)
        BinNode<Integer> badRightChild = new BinNode<>(-99);

        // A huge number (1000).
        // We will put this on the LEFT of 2. (Error: Left child must be smaller than
        // parent)
        BinNode<Integer> badLeftChild = new BinNode<>(12);

        // --- Step 2: Create the Original Leaves (with attached bad nodes) ---

        // Original leaf '2' gets '1000' on its left
        BinNode<Integer> node2 = new BinNode<>(badLeftChild, 2, null);

        // Original leaf '20' gets '-99' on its right
        BinNode<Integer> node20 = new BinNode<>(null, 20, badRightChild);

        // These original leaves remain untouched
        BinNode<Integer> node7 = new BinNode<>(7);
        BinNode<Integer> node12 = new BinNode<>(12);

        // --- Step 3: Rebuild the Original Parents ---

        // Recreating the original '5' (Left subtree)
        BinNode<Integer> subLeft = new BinNode<>(node2, 5, node7);

        // Recreating the original '15' (Right subtree)
        BinNode<Integer> subRight = new BinNode<>(node12, 15, node20);

        // --- Step 4: The Original Root ---
        return new BinNode<>(subLeft, 10, subRight);
    }

    public static <T> int ex_14(BinNode<T> t) {
        if (t == null)
            return 0;
        if (t.hasLeft() && !t.hasRight())
            return ex_14(t.getLeft());
        if (!t.hasLeft() && t.hasRight())
            return ex_14(t.getRight());
        if (!t.hasLeft() && !t.hasRight())
            return 1;
        return ex_14(t.getLeft()) + ex_14(t.getRight());
    }

    private static boolean isIn(BinNode<Integer> t, int num) {
        if (t == null)
            return false;
        if (t.getValue() == num)
            return true;
        if (t.hasLeft() && !t.hasRight())
            return isIn(t.getLeft(), num);
        if (!t.hasLeft() && t.hasRight())
            return isIn(t.getRight(), num);
        return isIn(t.getLeft(), num) || isIn(t.getRight(), num);
    }

    public static boolean ex_18(BinNode<Integer> t1, BinNode<Integer> t2) {
        if (t2 == null)
            return true;
        if (!isIn(t1, t2.getValue()))
            return false;
        return ex_18(t1, t2.getLeft()) && ex_18(t1, t2.getRight());
    }

    public static int ex_20(BinNode<Integer> t) {
        return posSum(t) - negSum(t);
    }

    private static int posSum(BinNode<Integer> t) {
        if (t == null)
            return 0;
        if (t.getValue() > 0)
            return t.getValue() + posSum(t.getLeft()) + posSum(t.getRight());
        return posSum(t.getLeft()) + posSum(t.getRight());
    }

    private static int negSum(BinNode<Integer> t) {
        if (t == null)
            return 0;
        if (t.getValue() < 0)
            return -t.getValue() + negSum(t.getLeft()) + negSum(t.getRight());
        return negSum(t.getLeft()) + negSum(t.getRight());
    }
}

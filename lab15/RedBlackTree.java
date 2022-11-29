public class RedBlackTree<T extends Comparable<T>> {

    /* Root of the tree. */
    RBTreeNode<T> root;

    static class RBTreeNode<T> {

        final T item;
        boolean isBlack;
        RBTreeNode<T> left;
        RBTreeNode<T> right;

        /* Creates a RBTreeNode with item ITEM and color depending on ISBLACK
           value. */
        RBTreeNode(boolean isBlack, T item) {
            this(isBlack, item, null, null);
        }

        /* Creates a RBTreeNode with item ITEM, color depending on ISBLACK
           value, left child LEFT, and right child RIGHT. */
        RBTreeNode(boolean isBlack, T item, RBTreeNode<T> left,
                   RBTreeNode<T> right) {
            this.isBlack = isBlack;
            this.item = item;
            this.left = left;
            this.right = right;
        }
    }

    /* Creates an empty RedBlackTree. */
    public RedBlackTree() {
        root = null;
    }

    /* Creates a RedBlackTree from a given 2-3 TREE. */
    public RedBlackTree(TwoThreeTree<T> tree) {
        Node<T> ttTreeRoot = tree.root;
        root = buildRedBlackTree(ttTreeRoot);
    }

    /* Builds a RedBlackTree that has isometry with given 2-3 tree rooted at
       given node R, and returns the root node. */
    RBTreeNode<T> buildRedBlackTree(Node<T> r) {
        if (r == null) {
            return null;
        }
        RBTreeNode root;
        if (r.getItemCount() == 1) {
            // TODO: Replace with code to create a 2-node equivalent
            root = new RBTreeNode(true, r.getItemAt(0));
            if (r.getChildrenCount() != 0) {
                root.left = buildRedBlackTree(r.getChildAt(0));
                root.right = buildRedBlackTree(r.getChildAt(1));
            }
        } else {
            // TODO: Replace with code to create a 3-node equivalent
            root = new RBTreeNode(true, r.getItemAt(1));
            root.left = new RBTreeNode(false, r.getItemAt(0));
            if (r.getChildrenCount() != 0) {
                root.left.left = buildRedBlackTree(r.getChildAt(0));
                root.left.right = buildRedBlackTree(r.getChildAt(1));
                root.right = buildRedBlackTree(r.getChildAt(2));
            }
        }
        return root;
    }

    /* Flips the color of NODE and its children. Assume that NODE has both left
       and right children. */
    void flipColors(RBTreeNode<T> node) {
        // TODO: YOUR CODE HERE
        node.isBlack = !node.isBlack;
        node.left.isBlack = !node.left.isBlack;
        node.right.isBlack = !node.right.isBlack;
    }

    /* Rotates the given node NODE to the right. Returns the new root node of
       this subtree. */
    RBTreeNode<T> rotateRight(RBTreeNode<T> node) {
        // TODO: YOUR CODE HERE
        if (node.left == null) {
            return node;
        }
        RBTreeNode right = new RBTreeNode(false, node.item, node.left.right, node.right);
        boolean color = node.isBlack;
        node = node.left;
        node.right = right;
        node.isBlack = color;
        return node;
    }

    /* Rotates the given node NODE to the left. Returns the new root node of
       this subtree. */
    RBTreeNode<T> rotateLeft(RBTreeNode<T> node) {
        // TODO: YOUR CODE HERE
        if (node.right == null) {
            return node;
        }
        RBTreeNode left = new RBTreeNode(false, node.item, node.left, node.right.left);
        boolean color = node.isBlack;
        node = node.right;
        node.left = left;
        node.isBlack = color;
        return node;
    }

    public void insert(T item) {
        root = insert(root, item);
        if (root != null) {
            root.isBlack = true;
        }
    }

    /* Inserts the given node into this Red Black Tree*/
    private RBTreeNode<T> insert(RBTreeNode<T> node, T item) {
        // Insert (return) new red leaf node.
        if (node == null) {
            return new RBTreeNode<>(false, item);
        }

        // Handle normal binary search tree insertion.
        int comp = item.compareTo(node.item);
        if (comp == 0) {
            return node; // do nothing.
        } else if (comp < 0) {
            node.left = insert(node.left, item);
        } else {
            node.right = insert(node.right, item);
        }

        // handle "middle of three" and "right-leaning red" structures

        // handle "smallest of three" structure
        
        // handle "largest of three" structure
        
        // TODO: YOUR CODE HERE
        if (item == null) {
            return node;
        }
        if (isRed(node.left)) {
            if (node.left != null) {
                if (isRed(node.left.left)) {
                    node = rotateRight(node);
                }
            }
        }
        if (isRed(node.left) && isRed(node.right)) {
            flipColors(node);
        }
        if (!isRed(node.left) && isRed(node.right)) {
            node = rotateLeft(node);
        }
        if (isRed(node.left) && isRed(node.right)) {
            flipColors(node);
        }
        return node; //fix this return statement
    }

    /* Returns whether the given node NODE is red. Null nodes (children of leaf
       nodes are automatically considered black. */
    private boolean isRed(RBTreeNode<T> node) {
        return node != null && !node.isBlack;
    }

}

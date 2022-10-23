public class BinaryTree {
    // Members
    int value;
    int size;
    BinaryTree root;
    BinaryTree parent;
    BinaryTree left;
    BinaryTree right;

    // Constructors
    BinaryTree(int val) {
        this.value = val;
        this.left = null;
        this.right = null;
        this.parent = null;
        size++;
        this.root = this;
    }

    BinaryTree() {
        this.right = null;
        this.left = null;
        this.parent = null;
        this.root = null;
        size++;
    }

    // Returns false if val is found
    // Otherwise add val (as a node) to the tree and returns true
    boolean add(int val) {
        boolean member = search(val);
        if (member) {
            System.out.println("Value already in tree");
            return false;
        } else {
            BinaryTree node = root;
            addNode(node, val);
            return true;
        }
    }

    private void addNode(BinaryTree node, int val) {
        if (val < node.value) {
            if (node.left == null) {
                BinaryTree child = new BinaryTree();
                child.value = val;
                child.parent = node;
                child.root = node.root;
                node.left = child;
                size++;
            } else {
                addNode(node.left, val);
            }
        } else {
            if (node.right == null) {
                BinaryTree child = new BinaryTree();
                child.value = val;
                child.parent = node;
                child.root = node.root;
                node.right = child;
                size++;
            } else {
                addNode(node.right, val);
            }
        }
    }

    // Returns true if val is found, otherwise returns false
    boolean search(int val) {
        // start from root and search every children
        boolean result = searchChildren(root, val);
        return result;
    }

    // search val in children of a specific node
    boolean searchChildren(BinaryTree node, int val) {
        if (node == null)
            return false;
        if (node.value == val)
            return true;
        boolean result;
        if (val < node.value) {
            result = searchChildren(node.left, val);
        } else {
            result = searchChildren(node.right, val);
        }
        return result;
    }

    // Returns the # of branches needed (from root) to reach lowest leaf
    int height(BinaryTree node) {
        int ht = 0;
        if (node.left != null) {
            int leftHt = height(node.left);
            ht = Math.max(ht, leftHt);
        }
        if (node.right != null) {
            int rightHt = height(node.right);
            ht = Math.max(ht, rightHt);
        }
        ht++;
        return ht;
    }

    int height() {
        int ht = height(root);
        return ht;
    }

    // Returns number of children the given node has
    private int degree(BinaryTree node) {
        int deg = 0;
        if (node.left != null)
            deg++;
        if (node.right != null)
            deg++;
        return deg;
    }

    // If node has two children return false
    // If node has no children, return false
    // If node has only one child, returns the child node
    private BinaryTree getOnlyChild(BinaryTree node) {
        int deg = degree(node);
        if (deg == 1) {
            if (node.left != null)
                return node.left;
            else {
                return node.right;
            }
        }
        return null;
    }

    // If val is found, remove the node and return true
    // Otherwise return false
    // remove uses helper method removeHelper for the recursion
    boolean remove(int val) {
        boolean bool = search(val);
        if (bool){
            root = removeHelper(root, val);
            System.out.println("Removed " + val + " from tree");
        }
        return bool;
    }

    // Uses recursion to find and remove the node that contains val
    // If val is not found, returns false
    private BinaryTree removeHelper(BinaryTree node, int val) {
        // dead node = node to remove
        if (node == null)
            return null;
        if (val > node.value) {
            node.right = removeHelper(node.right, val); // attach returned node to right
        } else if (val < node.value) {
            node.left = removeHelper(node.left, val); // attach returned node to left
        } else {
            if (node.left != null && node.right != null) {
                BinaryTree leftMaxNode = getInorderPredecessor(node);
                node.value = leftMaxNode.value;
                node.left = removeHelper(node.left, leftMaxNode.value); // remove the leftMaxNode from left-subtree
            } else {
                return getOnlyChild(node);  //for no child it will return null & for only 1 child it will return that child
            }
        }
        return node; // if the node is not dead node and not in left or right of dead node return itself
    }

    // Returns node with the largest value that is also
    // smaller than or equal to the value of the calling node
    // This method returns null if left node is null
    // Otherwise it returns left.getInOrderPredecessorHelper()
    BinaryTree getInorderPredecessor(BinaryTree node) {
        if (node.left == null)
            return null;
        return getInorderPredecessorHelper(node.left);

    }

    // Returns the largest node it can find by recursively calling
    // itself using the right node
    BinaryTree getInorderPredecessorHelper(BinaryTree node) {
        if (node.right == null)
            return node;
        else
            return getInorderPredecessorHelper(node.right);
    }

    // Traverses and prints the binary tree in Preorder by calling the
    // recursive method printPreoderHelper and then prints a newline
    void printPreorder() {
        printPreorderHelper(root);
        System.out.println();
    }

    // Prints the value of the node and then calls itself with the
    // left node (if it’s not null) and then calls itself with the
    // right node (if it’s not null)
    private void printPreorderHelper(BinaryTree node) {
        if (node == null)
            return;
        System.out.print(node.value + " ");
        printPreorderHelper(node.left);
        printPreorderHelper(node.right);
    }

    // Traverses and prints the binary tree in Inorder by calling the
    // recursive method printInoderHelper and then prints a newline
    void printInorder() {
        printInorderHelper(root);
        System.out.println();
    }

    // Calls itself with the left node (if it’s not null) and then
    // prints the value of the node and then finally calls itself with
    // the right node (if it’s not null)
    private void printInorderHelper(BinaryTree node) {
        if (node == null)
            return;
        printInorderHelper(node.left);
        System.out.print(node.value + " ");
        printInorderHelper(node.right);
    }

    // Traverses and prints the binary tree in Postorder by calling the
    // recursive method printPostoderHelper and then prints a newline
    void printPostorder() {
        printPostorderHelper(root);
        System.out.println();
    }

    // Calls itself with the left node (if it’s not null) and then
    // calls itself with the right node (if it’s not null) and finally
    // prints the value of the node
    private void printPostorderHelper(BinaryTree node) {
        if (node == null)
            return;
        printPostorderHelper(node.left);
        printPostorderHelper(node.right);
        System.out.print(node.value + " ");
    }

    public static void main(String[] args) {
        BinaryTree a = new BinaryTree(30);
        // boolean bool = a.search(1);
        // System.out.println(bool);

        a.add(20);
        a.add(40);
        a.add(25);
        a.add(10);
        a.add(50);
        a.add(35);
        a.add(15);
        a.add(16);
        a.add(19);

        a.add(55);
        a.add(45);
        a.add(36);
        a.add(32);

        // System.out.println(added);
        // BinaryTree c = a.right;
        // System.out.println(c.root.value);
        System.out.println("Preorder : -");
        a.printPreorder();
        System.out.println("Inorder : -");
        a.printInorder();
        System.out.println("Postorder : -");
        a.printPostorder();
        a.remove(19);
        a.remove(35);
        a.remove(50);
        a.remove(10);
        System.out.println("Inorder after removal: -");
        a.printInorder();

        // BinaryTree n = a.getInorderPredecessor(a.right);
        // System.out.println(n.value);
        // int ht = a.height();
        // System.out.println(ht);
    }
}

// The quick brown fox jumps over the lazy dog. 1234567890
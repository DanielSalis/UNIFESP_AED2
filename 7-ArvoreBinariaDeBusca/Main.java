import java.util.Scanner;

public class Main {
    class Node {
        int key;
        Node left;
        Node right;

        public Node(int newKey) {
            this.key = newKey;
            this.left = null;
            this.right = null;
        }
    }

    // Main Attributes
    Node root = null;
    boolean isIn = false;
    int numberToSearch;
    int heightBefore;
    int itensBefore;
    int heightAffter;
    int itensAffter;

    public void setInputs() {
        Scanner s = new Scanner(System.in);

        int number = 0;
        while (number >= 0) {
            number = s.nextInt();
            if (number < 0) {
                break;
            }
            this.preInsert(number);
            this.itensBefore++;
        }

        this.numberToSearch = s.nextInt();

        this.heightBefore = this.getHeigth(this.root);
        this.search(this.root, numberToSearch);

        if (this.isIn) {
            this.preDelete(numberToSearch);
            this.heightAffter = this.getHeigth(this.root);
            this.itensAffter = this.itensBefore - 1;
        } else {
            this.preInsert(numberToSearch);
            this.itensAffter = this.itensBefore + 1;
            this.heightAffter = this.getHeigth(this.root);
        }
        s.close();
    }

    public int getHeigth(Node node) {
        if (node == null)
            return 0;
        else {
            int leftDepth = getHeigth(node.left);
            int rigthDepth = getHeigth(node.right);

            if (leftDepth > rigthDepth)
                return (leftDepth + 1);
            else
                return (rigthDepth + 1);
        }
    }

    public void search(Node root, int key) {
        if (root == null) {
            return;
        }

        if (root.key == key) {
            this.isIn = true;
            return;
        }

        if (key < root.key) {
            search(root.left, key);
        }

        else if (key > root.key) {
            search(root.right, key);
        }
    }

    public void preInsert(int newKey) {
        this.root = insert(this.root, newKey);
    }

    public Node insert(Node root, int newKey) {

        if (root == null) {
            root = new Node(newKey);
            return root;
        }

        if (newKey < root.key) {
            root.left = insert(root.left, newKey);
        }

        else if (newKey > root.key) {
            root.right = insert(root.right, newKey);
        }

        return root;
    }

    public void preDelete(int key) {
        root = delete(root, key);
    }

    public Node delete(Node root, int key) {
        if (root == null) {
            return root;
        }

        if (key < root.key)
            root.left = delete(root.left, key);
        else if (key > root.key)
            root.right = delete(root.right, key);

        else {
            if (root.left == null)
                return root.right;
            else if (root.right == null)
                return root.left;

            root.key = minValue(root.right);
            root.right = delete(root.right, root.key);
        }

        return root;
    }

    public int minValue(Node root) {
        int minv = root.key;
        while (root.left != null) {
            minv = root.left.key;
            root = root.left;
        }
        return minv;
    }

    void printTree(Node root) {
        if (root != null) {
            printTree(root.left);
            System.out.print(root.key + " ");
            printTree(root.right);
        }
    }

    void printAttributes() {
        System.out.println(this.itensBefore + " " + this.heightBefore);
        System.out.println(this.itensAffter + " " + this.heightAffter);
    }

    public static void main(String[] args) {
        Main m = new Main();
        m.setInputs();
        m.printAttributes();
    }
}
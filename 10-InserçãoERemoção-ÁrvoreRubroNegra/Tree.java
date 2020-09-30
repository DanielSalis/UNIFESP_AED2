import java.util.Scanner;

public class Tree {
    class Node {
        int data;
        String color;
        Node left;
        Node right;
        Node parent;

        Node(int key) {
            this.data = key;
            this.left = null;
            this.right = null;
            this.parent = null;
            this.color = "RED";
        }

        public boolean verifyLeftNode() {
            return this == parent.left;
        }

        public Node getUncleNode() {
            if (parent == null || parent.parent == null)
                return null;

            if (parent.verifyLeftNode())
                return parent.parent.right;
            else
                return parent.parent.left;
        }

        public void moveDown(Node nParent) {
            if (parent != null) {
                if (verifyLeftNode()) {
                    parent.left = nParent;
                } else {
                    parent.right = nParent;
                }
            }
            nParent.parent = parent;
            parent = nParent;
        }

        public Node sibling() {
            if (this.parent == null)
                return null;

            if (verifyLeftNode())
                return this.parent.right;

            return this.parent.left;
        }

        public boolean hasRedChild() {
            return (left != null && left.color == "RED") || (right != null && right.color == "RED");
        }
    }

    Node root;
    int nodeToDelete;
    int sizeBefore;
    int sizeAffter;

    int leftBlackHeight = 0;
    int rightBlackHeight = 0;

    Tree() {
        root = null;
    }

    public void setValues(Scanner s) {
        int count = s.nextInt();
        int[] items = new int[count];

        for (int i = 0; i < count; i++) {
            items[i] = s.nextInt();
            insertNode(items[i]);
        }

        this.nodeToDelete = s.nextInt();

        this.sizeBefore = altura(this.root);
        System.out.println(sizeBefore);
        printTree(this.root);

        System.out.println();

        preDelete(nodeToDelete);
        this.sizeAffter = altura(this.root);
        System.out.println(sizeAffter);
    }

    public void insertNode(int n) {
        Node newNode = new Node(n);
        if (this.root == null) {
            newNode.color = "BLACK";
            root = newNode;
        } else {
            Node temp = search(n);

            if (temp.data == n) {
                return;
            }
            newNode.parent = temp;

            if (n < temp.data)
                temp.left = newNode;
            else
                temp.right = newNode;

            fixRedRed(newNode);
        }
    }

    public Node search(int n) {
        Node temp = root;
        while (temp != null) {
            if (n < temp.data) {
                if (temp.left == null)
                    break;
                else
                    temp = temp.left;
            } else if (n == temp.data) {
                break;
            } else {
                if (temp.right == null)
                    break;
                else
                    temp = temp.right;
            }
        }
        return temp;
    }

    public void fixRedRed(Node node) {
        if (node == root) {
            node.color = "BLACK";
            return;
        }

        Node parent = node.parent;
        Node grandparent = parent.parent;
        Node uncle = node.getUncleNode();

        if (parent.color != "BLACK") {
            if (uncle != null && uncle.color == "RED") {
                parent.color = "BLACK";
                uncle.color = "BLACK";
                grandparent.color = "RED";
                fixRedRed(grandparent);
            } else {
                if (parent.verifyLeftNode()) {
                    if (node.verifyLeftNode()) {
                        swapColors(parent, grandparent);
                    } else {
                        leftRotate(parent);
                        swapColors(node, grandparent);
                    }
                    rightRotate(grandparent);
                } else {
                    if (node.verifyLeftNode()) {
                        rightRotate(parent);
                        swapColors(node, grandparent);
                    } else {
                        swapColors(parent, grandparent);
                    }
                    leftRotate(grandparent);
                }
            }
        }
    }

    public void swapColors(Node node1, Node node2) {
        String temp = "";
        temp = node1.color;
        node1.color = node2.color;
        node2.color = temp;
    }

    public void swapValues(Node u, Node v) {
        int temp;
        temp = u.data;
        u.data = v.data;
        v.data = temp;
    }

    public void leftRotate(Node node) {
        Node nParent = node.right;
        if (node == root)
            root = nParent;

        node.moveDown(nParent);

        node.right = nParent.left;
        if (nParent.left != null)
            nParent.left.parent = node;

        nParent.left = node;
    }

    void rightRotate(Node node) {
        Node nParent = node.left;

        if (node == root)
            root = nParent;

        node.moveDown(nParent);

        node.left = nParent.right;
        if (nParent.right != null)
            nParent.right.parent = node;

        nParent.right = node;
    }

    void preDelete(int key) {
        if (this.root == null)
            return;

        Node n = search(key);

        deleteNode(n);
    }

    void deleteNode(Node v) {
        Node u = BSTreplace(v);

        boolean uvBlack = ((u == null || u.color == "BLACK") && (v.color == "BLACK"));
        Node parent = v.parent;

        if (u == null) {
            if (v == root) {
                root = null;
            } else {
                if (uvBlack) {
                    fixBlackBlackNodes(v);
                } else {
                    if (v.sibling() != null)
                        v.sibling().color = "RED";
                }

                if (v.verifyLeftNode()) {
                    parent.left = null;
                } else {
                    parent.right = null;
                }
            }
            return;
        }

        if (v.left == null || v.right == null) {
            if (v == root) {

                v.data = u.data;
                v.left = v.right = null;
            } else {
                if (v.verifyLeftNode()) {
                    parent.left = u;
                } else {
                    parent.right = u;
                }
                u.parent = parent;
                if (uvBlack) {
                    fixBlackBlackNodes(u);
                } else {
                    u.color = "BLACK";
                }
            }
            return;
        }

        swapValues(u, v);
        deleteNode(u);
    }

    public Node BSTreplace(Node node) {
        if (node.left != null && node.right != null)
            return getSucessorNode(node.right);

        if (node.left == null && node.right == null)
            return null;

        if (node.left != null)
            return node.left;
        else
            return node.right;
    }

    public Node getSucessorNode(Node node) {
        Node temp = node;

        while (temp.left != null)
            temp = temp.left;

        return temp;
    }

    void fixBlackBlackNodes(Node node) {
        if (node == root)
            return;

        Node sibling = node.sibling();
        Node parent = node.parent;

        if (sibling == null) {
            fixBlackBlackNodes(parent);
        } else {
            if (sibling.color == "RED") {
                parent.color = "RED";
                sibling.color = "BLACK";
                if (sibling.verifyLeftNode()) {
                    rightRotate(parent);
                } else {
                    leftRotate(parent);
                }
                fixBlackBlackNodes(node);
            } else {
                if (sibling.hasRedChild()) {
                    if (sibling.left != null && sibling.left.color == "RED") {
                        if (sibling.verifyLeftNode()) {
                            sibling.left.color = sibling.color;
                            sibling.color = parent.color;
                            rightRotate(parent);
                        } else {
                            sibling.left.color = parent.color;
                            rightRotate(sibling);
                            leftRotate(parent);
                        }
                    } else {
                        if (sibling.verifyLeftNode()) {
                            sibling.right.color = parent.color;
                            leftRotate(sibling);
                            rightRotate(parent);
                        } else {
                            sibling.right.color = sibling.color;
                            sibling.color = parent.color;
                            leftRotate(parent);
                        }
                    }
                    parent.color = "BLACK";
                } else {
                    sibling.color = "RED";
                    if (parent.color == "BLACK")
                        fixBlackBlackNodes(parent);
                    else
                        parent.color = "BLACK";
                }
            }
        }
    }

    public void printTree(Node no) {
        String closeParentesis = ")";
        String nullElement = "()";

        if (no != null) {
            String openParentesis = "";
            if (no.color == "BLACK") {
                openParentesis = "(N";
            }
            if (no.color == "RED") {
                openParentesis = "(R";
            }
            System.out.print(openParentesis + no.data);
            printTree(no.left);
            printTree(no.right);
            System.out.print(closeParentesis);
        } else {
            System.out.print(nullElement);
        }
    }

    public int altura(Node x) {
        int He, Hd, black = 0;
        if (x == null)
            return 0;
        else {
            if (x.color == "BLACK")
                black = 1;
            He = altura(x.left);
            Hd = altura(x.right);
            if (He > Hd)
                return (He + black);
            else
                return (Hd + black);
        }
    }

    public static void main(String[] args) {
        Tree t = new Tree();
        Scanner s = new Scanner(System.in);
        t.setValues(s);
        t.printTree(t.root);
    }
}
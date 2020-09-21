import java.util.Scanner;

public class Main {
    public class Node {
        public int key;
        public Node left;
        public Node dir;

        public Node(int newKey) {
            left = null;
            dir = null;
            this.key = newKey;
        }
    }

    public Node root;

    public void setValues(Scanner s) {
        int count = s.nextInt();
        int[] items = new int[count];

        for (int i = 0; i < count; i++) {
            items[i] = s.nextInt();
        }

        int search = s.nextInt();
        loadTree(items, count, search);
    }

    public void loadTree(int[] items, int count, int search) {
        int i;

        for (i = 0; i < count; i++) {
            this.preInsert(new Node(items[i]));
        }

        Node no = this.searchValue(this.root, search);
        if (no == null) {
            this.preInsert(new Node(search));
        } else {
            this.root = this.remove(this.root, no.key);
        }
    }

    public void preInsert(Node no) {
        if (root == null) {
            root = new Node(no.key);
        } else {
            root = insertNode(root, no);
        }
    }

    private Node insertNode(Node currentNode, Node no) {
        if (no.key < currentNode.key) {
            if (currentNode.left == null) {
                currentNode.left = (no);
            } else {
                currentNode.left = insertNode(currentNode.left, no);
                currentNode = balanceTree(currentNode);
            }
        } else if (no.key > currentNode.key) {
            if (currentNode.dir == null) {
                currentNode.dir = (no);
            } else {
                currentNode.dir = insertNode(currentNode.dir, no);
                currentNode = balanceTree(currentNode);
            }
        }
        return currentNode;
    }

    public Node balanceTree(Node root) {
        Node tree = root;
        int FA = getFactor(root);

        if (FA > 1) {
            int FB = getFactor(root.left);
            if (FB > 0) {
                tree = rotateLL(tree);
            } else if (FB < 0) {
                tree = rotateLR(tree);
            }
        } else if (FA < -1) {
            int FB = getFactor(root.dir);
            if (FB < 0) {
                tree = rotateRR(tree);
            } else if (FB > 0) {
                tree = rotateRL(tree);
            }
        }

        return tree;
    }

    public int getFactor(Node no) {
        int factor = getHeight(no.left) - getHeight(no.dir);
        return factor;
    }

    public int getHeight(Node no) {
        if (no == null) {
            return 0;
        }

        int HeightLeft = getHeight(no.left) + 1;
        int HeightRigth = getHeight(no.dir) + 1;

        if (HeightLeft > HeightRigth) {
            return HeightLeft;
        } else {
            return HeightRigth;
        }
    }

    public Node rotateLL(Node no) {
        Node NA = no;
        Node NB = NA.left;

        NA.left = NB.dir;
        NB.dir = NA;

        return NB;
    }

    public Node rotateRR(Node no) {
        Node NA = no;
        Node NB = NA.dir;

        NA.dir = NB.left;
        NB.left = NA;

        return NB;
    }

    public Node rotateLR(Node no) {
        Node NA = no;
        Node NB = NA.left;
        Node PC = NB.dir;

        NB.dir = PC.left;
        PC.left = NB;
        NA.left = PC.dir;
        PC.dir = NA;

        return PC;
    }

    public Node rotateRL(Node no) {
        Node NA = no;
        Node NB = NA.dir;
        Node PC = NB.left;

        NB.left = PC.dir;
        PC.dir = NB;
        NA.dir = PC.left;
        PC.left = NA;

        return PC;
    }

    public void printTree(Node no) {
        String openParentesis = "(C";
        String closeParentesis = ")";
        String nullElement = "()";

        if (no != null) {
            System.out.print(openParentesis + no.key);
            printTree(no.left);
            printTree(no.dir);
            System.out.print(closeParentesis);
        } else {
            System.out.print(nullElement);
        }
    }

    public Node searchValue(Node currentNode, int key) {
        if (currentNode == null) {
            return null;
        }

        if (currentNode.key == key) {
            return currentNode;
        }

        else if (key < currentNode.key) {
            return searchValue(currentNode.left, key);
        }

        else {
            return searchValue(currentNode.dir, key);
        }
    }

    public Node remove(Node node, int key) {
        if (node == null) {
            return node;
        }
        if (key < node.key) {
            node.left = remove(node.left, key);
            return balanceTree(node);
        }
        if (key > node.key) {
            node.dir = (remove(node.dir, key));
            return balanceTree(node);
        } else {
            if (node.left == null) {
                return node.dir;
            } else if (node.dir == null) {
                return node.left;
            }

            node.key = getMinValue(node.dir);
            node.dir = remove(node.dir, node.key);

            return balanceTree(node);
        }
    }

    public int getMinValue(Node root) {
        Node r = root;
        int key = root.key;

        while (r.left != null) {
            key = r.left.key;
            r = r.left;
        }

        return key;
    }

    public static void main(String[] args) {
        Main main = new Main();
        Scanner s = new Scanner(System.in);

        main.setValues(s);
        main.printTree(main.root);

        s.close();
    }
}
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Tree {
    public class Node {
        public Node left;
        public Node right;
        public String key;

        public Node(String value) {
            this.key = value;
            this.left = null;
            this.right = null;
        }
    }

    public Node root;

    public void execOriginalTree(Tree arv, String input) {
        arv.root = arv.setTreeValues(input, 1, input.length() - 1);
        System.out.println(arv.getHeigth(arv.root) - 1);
        arv.printTree(arv.root);
        System.out.println();
    }

    public void execOrderedTree(Tree arv) {
        Tree balanceada = arv.balanceTree(arv.root);
        System.out.println(balanceada.getHeigth(balanceada.root) - 1);
        balanceada.printTree(balanceada.root);
    }

    public int getHeigth(Node no) {
        if (no == null)
            return 0;

        return 1 + Math.max(getHeigth(no.left), getHeigth(no.right));
    }

    public int getFactor(Node no) {
        return getHeigth(no.left) - getHeigth(no.right);
    }

    public Tree balanceTree(Node no) {
        Node tree = no;
        int FB = getFactor(no);

        if (FB > 1) {
            if (FB > 0) {
                System.out.println("LL");
                tree = rotateLeftLeft(tree);
            } else if (FB < 0) {
                System.out.println("LR");
                tree = rotateLeftRigth(tree);
            }
        } else if (FB < -1) {
            if (FB < 0) {
                System.out.println("RR");
                tree = rotateRigthRigth(tree);
            } else if (FB > 0) {
                System.out.println("RL");
                tree = rotateRigthLeft(tree);
            }
        }
        Tree newTree = new Tree();
        newTree.root = tree;
        return newTree;
    }

    public Node rotateLeftLeft(Node no) {
        Node PA = no;
        Node PB = PA.left;

        PA.left = PB.right;
        PB.right = PA;
        return PB;
    }

    public Node rotateLeftRigth(Node no) {
        Node PA = no;
        Node PB = PA.left;
        Node PC = PB.right;

        PB.right = PC.left;
        PC.left = PB;
        PA.left = PC.right;
        PC.right = PA;
        return PC;
    }

    public Node rotateRigthRigth(Node no) {
        Node PA = no;
        Node PB = PA.right;

        PA.right = PB.left;
        PB.left = PA;
        return PB;
    }

    public Node rotateRigthLeft(Node no) {
        Node PA = no;
        Node PB = PA.right;
        Node PC = PB.left;

        PB.left = PC.right;
        PC.right = PB;
        PA.right = PC.left;
        PC.left = PA;
        return PC;
    }

    public int getIndex(String arv, int i, int j) {
        if (i > j) {
            return -1;
        }

        List<Character> s = new ArrayList<Character>();
        for (int k = i; i <= j; k++) {
            if (arv.charAt(k) == '(') {
                s.add(arv.charAt(k));
            }

            else if (arv.charAt(k) == ')') {
                if (s.get(0) == '(') {
                    s.remove(0);

                    if (s.size() == 0) {
                        return k;
                    }
                }
            }
        }
        return -1;
    }

    public Node setTreeValues(String arv, int i, int j) {

        if (i > j)
            return null;

        int k = i;
        while (k < arv.length()) {
            if (arv.charAt(k) == ('(') || arv.charAt(k) == (')')) {
                break;
            } else
                k++;
        }

        String key = arv.substring(i, k);
        int len = key.length();
        if (len == 0)
            return null;

        Node no = new Node(key);
        int index = -1;

        if (i + len <= j && arv.charAt(i + len) == '(')
            index = getIndex(arv, i + len, j);

        if (index != -1) {
            no.left = setTreeValues(arv, i + len + 1, index - 1);
            no.right = setTreeValues(arv, index + 2, j);
        }
        return no;
    }

    public void printTree(Node no) {
        if (no != null) {
            System.out.print("(" + no.key);
            printTree(no.left);
            printTree(no.right);
            System.out.print(")");
        } else {
            System.out.print("()");
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        Tree arv = new Tree();
        arv.execOriginalTree(arv, input);
        arv.execOrderedTree(arv);

        scanner.close();
    }
}
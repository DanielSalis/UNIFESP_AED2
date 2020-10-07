import java.util.Scanner;

public class Main {
    class Node {
        int key;
        Node prox;
    }

    class Hash {
        Node first = null;
        Node last = null;
    }

    int size;
    int quantityOfNumbers;
    Hash[] hash;

    public void setValues(Scanner s) {
        this.size = s.nextInt();
        this.quantityOfNumbers = s.nextInt();
        this.hash = new Hash[this.size];

        initializeHash(this.size);

        for (int i = 0; i < this.quantityOfNumbers; i++) {

            int newKey = s.nextInt();
            insertNode(newKey, this.size, this.hash);
        }

    }

    public void initializeHash(int size) {
        int i;
        for (i = 0; i < size; i++) {
            this.hash[i] = new Hash();
            this.hash[i].first = null;
            this.hash[i].last = null;
        }
    }

    public void insertNode(int newValue, int m, Hash[] hashTable) {

        int index = getModulo(newValue, m);
        if (hashTable[index].first == null) {
            hashTable[index].first = createNewNode(newValue);
            hashTable[index].last = hashTable[index].first;
        } else {
            hashTable[index].last.prox = createNewNode(newValue);
            hashTable[index].last = hashTable[index].last.prox;
        }
    }

    public Node createNewNode(int key) {
        Node newNode = new Node();
        newNode.prox = null;
        newNode.key = key;
        return newNode;
    }

    public int getModulo(int key, int m) {
        int modulo = key % m;

        return modulo;
    }

    public void analyseElement(Scanner s) {
        int element = s.nextInt();

        boolean isIn = searchElement(element, this.size, this.hash);

        if (isIn == false) {
            insertNode(element, this.size, this.hash);
        } else {
            removeNode(element, this.size, this.hash);
        }
    }

    public boolean searchElement(int chave, int m, Hash[] hashTable) {
        int index = getModulo(chave, m);
        Node auxNode = hashTable[index].first;

        while (auxNode != null) {
            if (auxNode.key == chave)
                return true;
            else
                auxNode = auxNode.prox;
        }
        return false;
    }

    public void removeNode(int chave, int m, Hash[] hashTable) {
        int index = getModulo(chave, m);
        int counter = 0;

        Node aux = hashTable[index].first;
        Node nodeToBeDeleted;

        if (aux.key == chave) {
            hashTable[index].first = hashTable[index].first.prox;
            nodeToBeDeleted = aux;
        } else {
            while (counter == 0) {
                if (aux.prox.key == chave) {
                    nodeToBeDeleted = aux.prox;

                    if (nodeToBeDeleted == hashTable[index].last)
                        hashTable[index].last = aux;

                    aux.prox = nodeToBeDeleted.prox;
                    counter++;
                } else
                    aux = aux.prox;
            }
        }
    }

    public void printHashTable() {
        Node auxNode;

        int i;
        for (i = 0; i < this.size; i++) {
            System.out.print("[" + i + "]");
            auxNode = this.hash[i].first;

            while (auxNode != null) {
                System.out.print(" " + auxNode.key);
                auxNode = auxNode.prox;
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Main m = new Main();
        Scanner s = new Scanner(System.in);

        m.setValues(s);
        m.analyseElement(s);
        m.printHashTable();

        s.close();
    }
}
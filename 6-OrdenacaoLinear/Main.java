import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    int quantityOfNames;
    List<String> namesString = new ArrayList<String>();
    List<int[]> namesInt = new ArrayList<int[]>();
    int positionToPrint;
    int numberOfPrints;
    int sizeOfBiggestString;

    public void setInputs() {
        Scanner s = new Scanner(System.in);

        this.quantityOfNames = s.nextInt();

        int i;
        for (i = 0; i < quantityOfNames; i++) {
            String name = s.next().toLowerCase();

            int j;
            String spaces = " ";
            for (j = name.length() - 1; j < 26; j++) {
                spaces = spaces + " ";
            }

            name = name + spaces;
            this.namesString.add(name);

            int[] array = new int[name.length() - 1];
            int k;
            for (k = 0; k < name.length() - 1; k++) {
                array[k] = (int) (name.charAt(k));
            }
            namesInt.add(array);
        }

        this.positionToPrint = s.nextInt();
        this.numberOfPrints = s.nextInt();

        s.close();

        this.RadixSort(this.namesInt, 27);
    }

    public void RadixSort(List<int[]> ListA, int d) {
        int i;

        List<int[]> ListB = new ArrayList<int[]>();

        for (i = 0; i < quantityOfNames; i++) {
            int[] A = ListA.get(i);
            int[] B = new int[A.length];
            CountingSort(A, B, d);
            ListB.add(B);
        }
        this.printNamesInt(ListB);
    }

    public void CountingSort(int[] A, int[] B, int k) {
        int[] C = new int[123];

        int i;
        for (i = 0; i < 122; i++) {
            C[i] = 0;
        }

        int j;
        for (j = 0; j < A.length - 1; j++) {
            C[A[j]] = C[A[j]] + 1;
        }

        for (i = 1; i < k - 1; i++) {
            C[i] = C[i] + C[i - 1];
        }

        for (j = A.length - 1; j >= 0; j--) {
            B[C[A[j]]] = A[j];
            C[A[j]] = C[A[j]] - 1;
        }
    }

    public void printNamesInt(List<int[]> ListA) {
        System.out.println("-------");

        int i;
        int j;
        for (i = 0; i < this.quantityOfNames; i++) {
            int[] nameInt = ListA.get(i);
            for (j = 0; j < nameInt.length - 1; j++) {
                System.out.print(nameInt[j] + " ");
            }
            System.out.println(" ");
        }
    }

    public static void main(String[] args) {
        Main m = new Main();
        m.setInputs();
    }
}
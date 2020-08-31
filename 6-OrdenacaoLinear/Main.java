import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class Main {
    List<String> nameStrings = new ArrayList<String>();
    int biggestString = 0;
    int positionToPrint;
    int numberOfPrints;
    int[][] nameInt;

    public void setInputs() {
        Scanner s = new Scanner(System.in);

        int n = s.nextInt();

        int i;
        for (i = 0; i < n; i++) {
            String name = s.next().toLowerCase();
            nameStrings.add(name);
            if (name.length() > this.biggestString)
                this.biggestString = name.length();
        }

        this.positionToPrint = s.nextInt();
        this.numberOfPrints = s.nextInt();

        this.nameInt = this.convertNames(nameStrings, this.biggestString);
        this.radixSort(nameInt, this.biggestString);

        s.close();
    }

    public int[][] convertNames(List<String> names, int max) {
        int[][] array = new int[names.size()][];

        int i;
        for (i = 0; i < names.size(); i++) {
            int[] convertedName = new int[max];

            int j;
            for (j = 0; j < names.get(i).length(); j++) {
                convertedName[j] = (int) names.get(i).charAt(j) - 96;
            }
            if (names.get(i).length() < max) {
                int k;
                for (k = names.get(i).length(); k < max; k++) {
                    convertedName[k] = 0;
                }
            }
            array[i] = convertedName;
        }
        return array;
    }

    public void radixSort(int[][] A, int d) {
        for (int i = d - 1; i >= 0; i--) {
            countingSort(A, 28, i);
        }
    }

    public void countingSort(int[][] A, int k, int digit) {
        int[] C = new int[k];
        int i;

        for (i = 0; i < k; i++) {
            C[i] = 0;
        }

        for (i = 0; i < A.length; i++) {
            C[A[i][digit]]++;
        }

        for (i = 1; i < k; i++) {
            C[i] += C[i - 1];
        }

        int[][] B = new int[A.length][];
        for (i = A.length - 1; i >= 0; i--) {
            B[C[A[i][digit]] - 1] = A[i];
            C[A[i][digit]]--;
        }

        for (i = 0; i < A.length; i++) {
            A[i] = B[i];
        }

        printDigits(C);
    }

    public void printDigits(int[] A) {
        int i;
        for (i = 1; i < A.length; i++) {
            System.out.print(A[i] + " ");
        }
        System.out.println();
    }

    public void printNames() {
        int index = this.positionToPrint - 1;

        int i;
        int j;
        for (i = index; i < index + this.numberOfPrints; i++) {
            for (j = 0; j < this.nameInt[i].length; j++) {
                if (this.nameInt[i][j] != 0)
                    System.out.print((char) (this.nameInt[i][j] + 96));
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Main m = new Main();
        m.setInputs();
        m.printNames();
    }
}
import java.util.Scanner;

public class MainClass {

    int[] setArray(Scanner scanner) {
        int sizeOfArray = scanner.nextInt();
        int[] array = new int[sizeOfArray];

        for (int i = 0; i < sizeOfArray; i++) {
            array[i] = scanner.nextInt();
        }

        return array;
    }

    void printArray(int[] array) {
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + " ");
        }
        System.out.println();
    }

    void joinArrays(int[] arrayA, int[] arrayB, int[] arrayC) {
        int i;
        for (i = 0; i < arrayA.length; i++) {
            arrayC[i] = arrayA[i];
        }

        int j;
        for (j = 0; j < arrayB.length; j++, i++) {
            arrayC[i] = arrayB[j];
        }
    }

    void insertionSort(int[] array) {
        for (int j = 1; j < array.length; j++) {
            int key = array[j];
            int i = j - 1;
            while ((i > -1) && array[i] > key) {
                array[i + 1] = array[i];
                i--;
            }
            array[i + 1] = key;
        }
    }

    public static void main(String[] args) {
        MainClass m = new MainClass();
        Scanner scanner = new Scanner(System.in);

        int[] arrayA = m.setArray(scanner);
        int[] arrayB = m.setArray(scanner);

        int totalLength = arrayA.length + arrayB.length;
        int[] arrayC = new int[totalLength];

        m.joinArrays(arrayA, arrayB, arrayC);
        m.insertionSort(arrayC);

        m.printArray(arrayC);
    }
}
import java.io.InputStream;
import java.util.Scanner;

public class Main {

    int ArrayLength;
    int Arr[];

    public void setArray(Scanner scanner) {
        ArrayLength = scanner.nextInt();
        Arr = new int[ArrayLength];
        for (int i = 0; i < ArrayLength; i++) {
            Arr[i] = scanner.nextInt();
        }
    }

    public void printArray() {
        System.out.println();

        for (int i = 0; i < ArrayLength; i++) {
            System.out.print(Arr[i] + " ");
        }
    }

    public void heapfy(int[] arrayNumbers, int n, int i) {
        int maior = i;
        int filhoEsq = 2 * i + 1;
        int filhoDir = 2 * i + 2;

        if (filhoEsq < n && arrayNumbers[filhoEsq] > arrayNumbers[maior]) {
            maior = filhoEsq;
        }

        if (filhoDir < n && arrayNumbers[filhoDir] > arrayNumbers[maior]) {
            maior = filhoDir;
        }

        if (maior != i) {
            int aux = arrayNumbers[i];
            arrayNumbers[i] = arrayNumbers[maior];
            arrayNumbers[maior] = aux;

            heapfy(arrayNumbers, n, maior);
        }
    }

    public void buildMaxHeap(int arrayNumbers[], int n) {
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapfy(Arr, n, i);
        }
    }

    public void heapExtractMax(int arrayNumbers[], int n) {
        for (int i = n - 1; i >= 0; i--) {
            int temp = arrayNumbers[0];
            arrayNumbers[0] = arrayNumbers[i];
            arrayNumbers[i] = temp;
            heapfy(arrayNumbers, i, 0);
        }
    }

    public void heapSort(int arrayNumbers[]) {
        int n = arrayNumbers.length;

        buildMaxHeap(arrayNumbers, n);
        for (int i = 0; i < arrayNumbers.length; i++) {
            System.out.print(arrayNumbers[i] + " ");
        }
        heapExtractMax(arrayNumbers, n);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Main main = new Main();

        main.setArray(scanner);

        main.heapSort(main.Arr);

        main.printArray();
    }
}
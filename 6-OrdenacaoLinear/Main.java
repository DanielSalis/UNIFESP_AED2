import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public void sort(int[][] A, int k, int digit) {
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
        print(C);
    }

    public void radixSort(int[][] A, int d) {
        for (int i = d - 1; i >= 0; i--) {
            sort(A, 28, i);
        }
    }

    public void print(int[] list) {
        for (int i = 1; i < list.length; i++) {
            System.out.print(list[i] + " ");
        }
        System.out.println();
    }

    public int[][] convertStrings(List<String> arr, int max) {
        int[][] newArray = new int[arr.size()][];
        for (int i = 0; i < arr.size(); i++) {
            int[] converted = new int[max];
            int len = arr.get(i).length();
            for (int j = 0; j < len; j++) {
                converted[j] = (int) arr.get(i).charAt(j) - 96;
            }
            if (len < max) {
                for (int j = len; j < max; j++) {
                    converted[j] = 0;
                }
            }
            newArray[i] = converted;
        }
        return newArray;
    }

    public static void main(String[] args) {
        Main cs = new Main();
        Scanner scanner = new Scanner(System.in);

        List<String> originalList = new ArrayList<String>();
        int max = 0;
        int n = scanner.nextInt();
        for (int i = 0; i < n; i++) {
            String s = scanner.next().toLowerCase();
            originalList.add(s);
            if (s.length() > max)
                max = s.length();
        }

        int start = scanner.nextInt();
        int limit = scanner.nextInt();
        scanner.close();
        int[][] arr = cs.convertStrings(originalList, max);
        cs.radixSort(arr, max);

        int starter = start - 1;
        for (int i = starter; i < starter + limit; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                if (arr[i][j] != 0)
                    System.out.print((char) (arr[i][j] + 96));
            }
            System.out.println();
        }
    }
}
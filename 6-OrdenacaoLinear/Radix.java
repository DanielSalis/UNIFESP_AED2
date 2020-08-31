public class Radix {

    public void radixSort(String[] b, int r) {
        int max = getMax(b, r);
        for (int digit = max; digit > 0; digit--) {
            countSort(b, r, digit - 1);
        }
    }

    public int getMax(String arr[], int n) {
        int max = arr[0].length();
        for (int i = 1; i < n; i++) {
            if (arr[i].length() > max)
                max = arr[i].length();
        }
        return max;
    }

    public void countSort(String a[], int size, int k) {
        String[] b = new String[a.length - 1];
        int[] c = new int[257];

        for (int i = 0; i < 257; i++) {
            c[i] = 0;
        }

        for (int j = 0; j < size; j++) {
            c[k < a[j].length() ? (int) (a[j].charAt(j) + 1) : 0]++;
        }

        for (int f = 1; f < 257; f++) {
            c[f] += c[f - 1];
            if (c[f] != 0) {
                System.out.println(c[f]);
            }
        }

        for (int r = size - 1; r >= 1; r--) {
            b[c[k < (a[r].length() - 1) ? (int) (a[r].charAt(k) + 1) : 0] - 1] = a[r];
            c[k < (a[r].length() - 1) ? (int) (a[r].charAt(k) + 1) : 0]--;
        }

        for (int l = 0; l < size; l++) {
            a[l] = b[l];
        }
    }

    public static void main(String[] args) {
        Radix r = new Radix();

        String data[] = { "Daniel", "Ana", "Jose", "Silvia", "Joao" };

        r.radixSort(data, data.length);

        System.out.println();

        for (int i = 0; i < data.length; i++) {
            System.out.println(data[i]);
        }
    }
}
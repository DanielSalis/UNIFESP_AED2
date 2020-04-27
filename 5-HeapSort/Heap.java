import java.util.Scanner;

public class Heap {

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

    public void maxHeapify(int arr[], int i, int h){
        int largest, left, right;

        left = 2*i+1;
        right = 2*i+2;

        if(right <= h && arr[left] > arr[i]){
            largest = left;
        }else{
            largest = i;
        }

        if(right <= h && arr[right] > arr[largest]){
            largest = right;
        }

        if(largest != i){
            int aux = arr[i];
            arr[i] = arr[largest];
            arr[largest] = aux;
            maxHeapify(arr, largest, h);
        }
    }
    
    public void buildMaxHeap(int arr[], int n, int h){
        int i;
        h=n-1;
        for(i = n/2; i >=0; i--){
            maxHeapify(arr,i, h);
        }
    }

    public void  heapSort(int arr[], int n, int h){
        buildMaxHeap(arr, n, h);

        int i;
        for(i = n-1; i>0;i--){
            int aux = arr[0];
            arr[0] = arr[i];
            arr[i] = aux;
            h=h-1;
            maxHeapify(arr,0, h);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Heap heap = new Heap();

        heap.setArray(scanner);

        int heapSize = 0;
        heap.heapSort(heap.Arr, heap.Arr.length-1, heapSize);

        heap.printArray();
    }
}
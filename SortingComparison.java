import java.util.Arrays;
import java.util.Random;

public class SortingComparison {
    public static void main(String[] args) {
        // Test with more input sizes
        int[] sizes = {100, 500, 1000, 2000, 4000, 8000, 16000, 32000, 64000, 128000};
        
        // Prepare arrays before timing
        int[][] testArrays = new int[sizes.length][];
        for (int i = 0; i < sizes.length; i++) {
            testArrays[i] = generateRandomArray(sizes[i]);
        }
        
        System.out.println("Size\tMergeSort(ms)\tQuickSort(ms)");
        System.out.println("----------------------------------------");
        
        for (int i = 0; i < sizes.length; i++) {
            int size = sizes[i];
            
            // Create copies of the test array
            int[] arr1 = Arrays.copyOf(testArrays[i], testArrays[i].length);
            int[] arr2 = Arrays.copyOf(testArrays[i], testArrays[i].length);
            
            // Warm up JVM
            for (int j = 0; j < 3; j++) {
                MergeSort.sort(Arrays.copyOf(arr1, arr1.length));
                QuickSort.sort(Arrays.copyOf(arr2, arr2.length));
            }
            
            // Test Merge Sort
            long mergeSortTime = 0;
            for (int j = 0; j < 5; j++) {
                int[] arrCopy = Arrays.copyOf(arr1, arr1.length);
                long startTime = System.nanoTime();
                MergeSort.sort(arrCopy);
                mergeSortTime += System.nanoTime() - startTime;
            }
            mergeSortTime /= 5; // Average time
            
            // Test Quick Sort
            long quickSortTime = 0;
            for (int j = 0; j < 5; j++) {
                int[] arrCopy = Arrays.copyOf(arr2, arr2.length);
                long startTime = System.nanoTime();
                QuickSort.sort(arrCopy);
                quickSortTime += System.nanoTime() - startTime;
            }
            quickSortTime /= 5; // Average time
            
            // Print results
            System.out.printf("%d\t%.3f\t\t%.3f%n", 
                size, 
                mergeSortTime / 1_000_000.0,
                quickSortTime / 1_000_000.0);
        }
    }
    
    private static int[] generateRandomArray(int size) {
        Random random = new Random();
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = random.nextInt(10000);
        }
        return arr;
    }
} 
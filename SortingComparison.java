import java.util.Arrays;
import java.util.Random;
import java.util.HashSet;
import java.util.Set;

public class SortingComparison {
    public static void main(String[] args) {
        // Start with smaller sizes and increase gradually
        int[] sizes = {
            100000,     // 100 thousand
            500000,     // 500 thousand
            1000000,    // 1 million
            2000000,    // 2 million
            4000000,    // 4 million
            8000000     // 8 million
        };
        
        System.out.println("Size\t\tMergeSort(ms)\tQuickSort(ms)\tDifference(ms)");
        System.out.println("----------------------------------------------------------------");
        
        for (int i = 0; i < sizes.length; i++) {
            int size = sizes[i];
            System.out.println("\nGenerating array of size: " + size);
            
            // Generate test array
            int[] testArray = generateUniqueRandomArray(size);
            
            // Create copies for testing
            int[] arr1 = Arrays.copyOf(testArray, testArray.length);
            int[] arr2 = Arrays.copyOf(testArray, testArray.length);
            
            // Warm up JVM
            System.out.println("Warming up JVM...");
            for (int j = 0; j < 2; j++) {
                MergeSort.sort(Arrays.copyOf(arr1, arr1.length));
                QuickSort.sort(Arrays.copyOf(arr2, arr2.length));
            }
            
            // Test Merge Sort
            System.out.println("Testing Merge Sort...");
            long mergeSortTime = 0;
            for (int j = 0; j < 3; j++) {
                int[] arrCopy = Arrays.copyOf(arr1, arr1.length);
                long startTime = System.nanoTime();
                MergeSort.sort(arrCopy);
                mergeSortTime += System.nanoTime() - startTime;
            }
            mergeSortTime /= 3; // Average time
            
            // Test Quick Sort
            System.out.println("Testing Quick Sort...");
            long quickSortTime = 0;
            for (int j = 0; j < 3; j++) {
                int[] arrCopy = Arrays.copyOf(arr2, arr2.length);
                long startTime = System.nanoTime();
                QuickSort.sort(arrCopy);
                quickSortTime += System.nanoTime() - startTime;
            }
            quickSortTime /= 3; // Average time
            
            // Calculate time difference in milliseconds
            double mergeSortMs = mergeSortTime / 1_000_000.0;
            double quickSortMs = quickSortTime / 1_000_000.0;
            double differenceMs = Math.abs(mergeSortMs - quickSortMs);
            
            // Print results
            System.out.printf("%d\t%.3f\t\t%.3f\t\t%.3f%n", 
                size, 
                mergeSortMs,
                quickSortMs,
                differenceMs);
                
            // Check if we've reached the 60-second difference
            if (differenceMs >= 60000) {
                System.out.println("\nReached 60-second difference at size: " + size);
                break;
            }
        }
    }
    
    private static int[] generateUniqueRandomArray(int size) {
        Random random = new Random();
        Set<Integer> uniqueNumbers = new HashSet<>();
        int[] arr = new int[size];
        int maxValue = 1_000_000_000; // 1 billion
        
        // Generate unique random numbers
        for (int i = 0; i < size; i++) {
            int num;
            do {
                num = random.nextInt(maxValue) + 1; // Generate positive numbers from 1 to 1 billion
            } while (!uniqueNumbers.add(num));
            arr[i] = num;
        }
        
        // Shuffle the array to ensure random order
        for (int i = size - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
        
        return arr;
    }
} 
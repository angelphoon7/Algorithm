public class MergeSort {
    // Main method to sort the array
    public static void sort(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }
        int[] temp = new int[arr.length];
        mergeSort(arr, temp, 0, arr.length - 1);
    }

    // Recursive method to divide the array into subarrays
    private static void mergeSort(int[] arr, int[] temp, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            
            // Sort first and second halves
            mergeSort(arr, temp, left, mid);
            mergeSort(arr, temp, mid + 1, right);
            
            // Merge the sorted halves
            merge(arr, temp, left, mid, right);
        }
    }

    // Method to merge two subarrays
    private static void merge(int[] arr, int[] temp, int left, int mid, int right) {
        // Copy data to temporary arrays
        for (int i = left; i <= right; i++) {
            temp[i] = arr[i];
        }

        int i = left;    // Initial index of first subarray
        int j = mid + 1; // Initial index of second subarray
        int k = left;    // Initial index of merged subarray

        // Merge the temp arrays back into arr[left..right]
        while (i <= mid && j <= right) {
            if (temp[i] <= temp[j]) {
                arr[k++] = temp[i++];
            } else {
                arr[k++] = temp[j++];
            }
        }

        // Copy remaining elements of left subarray if any
        while (i <= mid) {
            arr[k++] = temp[i++];
        }

        // Copy remaining elements of right subarray if any
        while (j <= right) {
            arr[k++] = temp[j++];
        }
    }
} 
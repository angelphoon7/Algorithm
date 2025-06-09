import csv
import time

def read_csv(filename):
    """Read dataset into a list of tuples (number, string)."""
    data = []
    with open(filename, 'r') as file:
        reader = csv.reader(file)
        for row in reader:
            number = int(row[0])
            # Get the random string from the second column
            rand_str = row[1]
            # Check if the number and text are read successfully
            if number is not None and rand_str is not None:
                data.append((number, rand_str))
    return data

def write_csv(data, filename):
    """Write sorted data to CSV with no header."""
    with open(filename, 'w', newline='') as file:
        writer = csv.writer(file)
        for number, rand_str in data:
            writer.writerow([number, rand_str])

# Partition function for Quick Sort
def partition(arr, low, high):
    # Choose the pivot as the last element
    pivot = arr[high][0]
    # Initialize the index of the smaller element
    i = low - 1
    # Iterate through the array
    for j in range(low, high):
        # If the current element is smaller than the pivot
        if arr[j][0] < pivot:
            # Increment the index of the smaller element
            i += 1
            # Swap the current element with the element at the index of the smaller element
            arr[i], arr[j] = arr[j], arr[i]
    # Swap the pivot with the element at the index of the smaller element
    arr[i+1], arr[high] = arr[high], arr[i+1]
    # Return the index of the pivot
    return i + 1

# Recursive Quick Sort
def quick_sort(arr, low, high):
    # If the array has more than one element
    if low < high:
        # Partition the array
        pi = partition(arr, low, high)
        # Recursively sort the elements before and after the pivot
        quick_sort(arr, low, pi - 1)
        quick_sort(arr, pi + 1, high)

# --- MAIN ---
if __name__ == "__main__":
    try:
        input_file = input("📂 Enter dataset filename (e.g., dataset_5000.csv): ").strip()
        data = read_csv(input_file)

        print(f" Sorting {len(data)} entries using Quick Sort...")
        start_time = time.time()

        quick_sort(data, 0, len(data) - 1)

        end_time = time.time()
        runtime = end_time - start_time

        output_file = f"quick_sort_{len(data)}.csv"
        write_csv(data, output_file)

        print(f" Sorted data saved to: {output_file}")
        print(f" Running time: {runtime:.6f} seconds")

    except Exception as e:
        print(f"Error: {e}")

import csv

#  Read a segment of the CSV file specified by the start and end row indices
#  It returns a list of lists containing the data from the specified rows
def read_csv_segment(file_name, start_row, end_row):
    data = []
    with open(file_name, newline='') as csvfile:
        reader = csv.reader(csvfile)
        #  Read the rows in the specified range
        for i, row in enumerate(reader):
            #  If the row is in the specified range, add it to the data list
            if start_row <= i <= end_row:
                #  Convert the row to a list of integers and strings
                number = int(row[0])
                #  Remove any line breaks from the random string
                random_str = row[1]
                data.append([number, random_str])
    return data

def format_data(arr):
    # Format the array as: [num1/str1, num2/str2, ...]
    return "[" + ", ".join(f"{num}/{s}" for num, s in arr) + "]"

def quick_sort_with_steps(arr, low, high, steps):
    if low < high:
        # Print current segment before partitioning
        steps.append(format_data(arr[low:high+1]))

        pi = partition(arr, low, high)
        # Print partition index and array after partitioning
        steps.append(f"pi={pi} {format_data(arr)}")

        quick_sort_with_steps(arr, low, pi - 1, steps)
        quick_sort_with_steps(arr, pi + 1, high, steps)

def partition(arr, low, high):
    #  Choose the last element as the pivot 
    pivot = arr[high][0]
    i = low - 1
    #  Iterate through the array
    for j in range(low, high):
        #  If the current element is less than the pivot, swap it with the element at index i
        if arr[j][0] < pivot:
            i += 1
            arr[i], arr[j] = arr[j], arr[i]
    #  Swap the pivot element with the element at index i+1
    arr[i + 1], arr[high] = arr[high], arr[i + 1]
    return i + 1

def write_steps_to_file(steps, start_row, end_row):
    filename = f"quick_sort_step_{start_row}_{end_row}.txt"
    with open(filename, 'w') as f:
        for step in steps:
            f.write(step + '\n')
    print(f"\n Sorting steps saved to: {filename}")

# --- Main Execution ---    
if __name__ == "__main__":
    try:
        #  Get the start and end row numbers from the user
        start_row = int(input("Enter the START row number (0-based index): "))
        end_row = int(input("Enter the END row number (0-based index): "))

        #  Check if the range is valid
        if start_row < 0 or end_row < start_row:
            raise ValueError("Invalid range. START must be >= 0 and END >= START.")
        
        #  Get the input file name from the user
        input_file = "dataset_5000.csv"

        #  Read the data segment from the CSV file
        data_segment = read_csv_segment(input_file, start_row, end_row)
        if not data_segment:
            raise ValueError("No data found in the given row range.")

        steps = []
        quick_sort_with_steps(data_segment, 0, len(data_segment) - 1, steps)
        write_steps_to_file(steps, start_row, end_row)

    except Exception as e:
        print(f"Error: {e}")

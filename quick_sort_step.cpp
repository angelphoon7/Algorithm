#include <iostream>      // for input and output
#include <fstream>       // for file operations     
#include <sstream>       // for string stream operations
#include <vector>        // for vector container
#include <string>        // for string operations
#include <algorithm>     // for std::swap

using namespace std;

// Struct to store each row of data
struct DataRow {
    int number;
    string random_string;
};

// Function to read CSV file from start_row to end_row
// This function reads a segment of the CSV file specified by the start and end row indices
// It returns a vector of dataset structs containing the data from the specified rows

vector<DataRow> read_csv_segment(const string& filename, int start_row, int end_row) {
    vector<DataRow> dataset;  
    ifstream file(filename);
    string line;
    int current_row = 0;   // Initialize current row index

    while (getline(file, line)) {
        if (current_row >= start_row && current_row <= end_row) { //Check if current row is within the specified range
            stringstream ss(line);
            string num_str, rand_str; // Initialize string variables to store number and random string  
            if (getline(ss, num_str, ',') && getline(ss, rand_str)) { //Extract number and random string from the line
                DataRow d;    
                d.number = stoi(num_str);
                // Remove any line breaks from the random string
                rand_str.erase(remove(rand_str.begin(), rand_str.end(), '\n'), rand_str.end());
                rand_str.erase(remove(rand_str.begin(), rand_str.end(), '\r'), rand_str.end());
                d.random_string = rand_str;
                dataset.push_back(d);
            }
        }
        current_row++;
        if (current_row > end_row) break;
    }

    return dataset;
}

// Helper function to format array segment as string
// Display the index from low to high
string format_array(const vector<DataRow>& arr, int low, int high) {
    stringstream ss;
    ss << "[";
    for (int i = low; i <= high; i++) {
        ss << arr[i].number << "/" << arr[i].random_string;
        if (i < high) ss << ", ";
    }
    ss << "]";
    return ss.str();
}

// Partition function where last element is chosen as pivot
int partition(vector<DataRow>& arr, int low, int high) {
    int pivot = arr[high].number;
    int i = low - 1;
    for (int j = low; j < high; j++) {
        // If current element is less than pivot, swap it with the element at index i
        if (arr[j].number < pivot) {
            i++;
            std::swap(arr[i], arr[j]);
        }
    }
    // Swap the pivot element with the element at index i+1
    std::swap(arr[i + 1], arr[high]);
    return i + 1;
}

// Quick sort function with output steps tracking
void quick_sort(vector<DataRow>& arr, int low, int high, vector<string>& steps) {
    if (low < high) {
        // Format the array segment as a string and add it to the steps vector
        steps.push_back(format_array(arr, low, high));

        int pi = partition(arr, low, high);
        // Add the partition index and the formatted array to the steps vector
        steps.push_back("pi=" + to_string(pi) + " " + format_array(arr, low, high));

        quick_sort(arr, low, pi - 1, steps);
        // Recursively sort the elements before and after the partition index
        quick_sort(arr, pi + 1, high, steps);
    }
}

// Write steps to file
void write_steps_to_file(const vector<string>& steps, int start_row, int end_row) {
    string filename = "quick_sort_step_" + to_string(start_row) + "_" + to_string(end_row) + ".txt";
    ofstream out(filename);
    for (const string& step : steps) {
        out << step << endl;
    }
    cout << "\nSorting steps saved to: " << filename << endl;
}

int main() {
    int start_row, end_row;
    string input_file = "dataset_5000.csv";

    cout << "Enter the START row number (0-based index): ";
    cin >> start_row;
    cout << "Enter the END row number (0-based index): ";
    cin >> end_row;

    if (start_row < 0 || end_row < start_row) {
        cerr << "Error: Invalid row range." << endl;
        return 1;
    }

    vector<DataRow> dataset = read_csv_segment(input_file, start_row, end_row);
    if (dataset.empty()) {
        cerr << "Error: No data found in the given row range." << endl;
        return 1;
    }

    vector<string> steps;
    quick_sort(dataset, 0, dataset.size() - 1, steps);
    write_steps_to_file(steps, start_row, end_row);

    return 0;
}
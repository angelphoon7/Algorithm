#include <iostream>
#include <fstream>
#include <sstream>
#include <vector>
#include <string>
#include <chrono>

using namespace std;
using namespace chrono;

// Define a pair to hold (number, string)
typedef pair<int, string> DataEntry;

// Function to read CSV file
vector<DataEntry> readCSV(const string& filename) {
    vector<DataEntry> data;
    // Open the file
    ifstream file(filename);
    // Check if the file is open
    if (!file) {
        cerr << "Error: Cannot open file " << filename << endl;
        exit(1);
    }

    string line;
    while (getline(file, line)) {
        // Split the line into number and text
        stringstream ss(line);
        // Read the number
        string numStr, text;
        // Check if the number and text are read successfully
        if (getline(ss, numStr, ',') && getline(ss, text)) {    
            // Convert the number to an integer
            int number = stoi(numStr);
            // Add the number and text to the data vector
            data.emplace_back(number, text);
        }
    }

    return data;
}

// Function to write sorted data to CSV
void writeCSV(const vector<DataEntry>& data, const string& filename) {
    ofstream file(filename);
    if (!file) {
        cerr << "Error: Cannot write to file " << filename << endl;
        exit(1);
    }

    for (const auto& entry : data) {
        file << entry.first << "," << entry.second << "\n";
    }
}

// Partition function for Quick Sort
int partition(vector<DataEntry>& arr, int low, int high) {
    // Choose the pivot as the last element
    int pivot = arr[high].first;
    // Initialize the index of the smaller element
    int i = low - 1;

    for (int j = low; j < high; j++) {  
        // If the current element is smaller than the pivot
        if (arr[j].first < pivot) {
            // Increment the index of the smaller element
            ++i;
            // Swap the current element with the element at the index of the smaller element
            swap(arr[i], arr[j]);
        }
    }

    // Swap the pivot with the element at the index of the smaller element
    swap(arr[i + 1], arr[high]);
    // Return the index of the pivot
    return i + 1;
}

// Recursive Quick Sort
void quickSort(vector<DataEntry>& arr, int low, int high) {
    // If the array has more than one element
    if (low < high) {
        // Partition the array
        int pi = partition(arr, low, high);
        // Recursively sort the elements before and after the pivot
        quickSort(arr, low, pi - 1);
        quickSort(arr, pi + 1, high);
    }
}

int main() {
    string filename;
    cout << " Enter dataset filename (e.g., dataset_5000.csv): ";
    cin >> filename;

    vector<DataEntry> data = readCSV(filename);
    cout << " Sorting " << data.size() << " entries using Quick Sort" << endl;

    auto start = high_resolution_clock::now();

    quickSort(data, 0, data.size() - 1);

    auto end = high_resolution_clock::now();
    double duration = duration_cast<microseconds>(end - start).count() / 1e6;

    string outputFile = "quick_sort_" + to_string(data.size()) + ".csv";
    writeCSV(data, outputFile);

    cout << " Sorted data saved to: " << outputFile << endl;
    cout << " Running time: " << fixed << duration << " seconds" << endl;

    return 0;
}

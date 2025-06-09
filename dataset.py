import random
import string
import csv

def generate_random_string(length=6):
    return ''.join(random.choices(string.ascii_lowercase, k=length))

def generate_dataset(n, filename):
    # Generate unique random integers up to 1 billion
    unique_numbers = random.sample(range(1, 1_000_000_001), n)  
    
    # Generate data pairs
    data = [(num, generate_random_string()) for num in unique_numbers]
    
    # Shuffle the data to ensure random order
    random.shuffle(data)
    
    with open(filename, 'w', newline='') as file:
        writer = csv.writer(file)
        for row in data:
            writer.writerow(row)
    
    print(f"Dataset of {n} entries saved to {filename}")
    print(f"Numbers range: 1 to 1,000,000,000")
    print(f"Data is randomly shuffled")


# Generate a larger dataset to ensure significant time difference between sorting algorithms
generate_dataset(500000, 'dataset_500000.csv')

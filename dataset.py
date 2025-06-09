import random
import string
import csv

def generate_random_string(length=6):
    return ''.join(random.choices(string.ascii_lowercase, k=length))

def generate_dataset(n, filename):
    unique_numbers = random.sample(range(1, 1_000_000_001), n)  
    data = [(num, generate_random_string()) for num in unique_numbers]
    
    with open(filename, 'w', newline='') as file:
        writer = csv.writer(file)
        for row in data:
            writer.writerow(row)
    
    print(f"Dataset of {n} entries saved to {filename}")


generate_dataset(5000, 'dataset_5000.csv')

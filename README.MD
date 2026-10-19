# Concurrent CSV Data Processor

A Spring Boot app that reads employees from a CSV file, calculates their salary raises concurrently using a thread pool, and saves the new salaries back into the CSV.

## Raise rules

- **Less than 60%** of projects completed: no raise
- **2% per full year** worked, only after completing at least 1 year
- **Role:** Director 5%, Manager 2%, Employee 1%
- **Bonus:** more than 80% of projects completed means the whole raise is multiplied by 1.5

The years and role percentages are added together. For example, Eve (Employee, 7 years, 90%) gets (7 × 2% + 1%) × 1.5 = **22.5%**.

Exactly 60% still gets a raise, and exactly 80% does not get the bonus.

## CSV format

No header row, 6 columns:

```
id,name,salary,joinDate,role,completion
1,Alice,52000.0,2019-05-12,Employee,0.8
```

`completion` is between 0 and 1 (`0.8` = 80%). Broken rows are skipped with a message.

## Concurrency

All of it is in `EmployeeProcessor`:

| What | Used for |
|---|---|
| **Fixed thread pool (6 threads)** | Processes the employees at the same time (Java's Executor framework) |
| **Semaphore (3 permits)** | Only 3 threads can update a salary at once |
| **ReentrantLock** | Protects the shared `raisedEmployees` list, since `ArrayList` isn't thread-safe |
| **AtomicInteger** | Counts processed employees safely without a lock |

The main thread waits for all threads with `awaitTermination()`, then writes the CSV once.

## How to run

1. Have `test_employees.csv` in root
2. **Every run overwrites the salaries and applies the raise again, careful**
3. Run `DataprocessorApplication`

## Result
| Name | Before | Raise | After |
|---|---|---|---|
| Alice | 52,000 | 15% | 59,800 |
| Bob | 68,000 | 14% | 77,520 |
| Diana | 60,000 | 0% | 60,000 |
| Eve | 54,000 | 22.5% | 66,150 |
| Nate | 72,000 | 27% | 91,440 |


# Sources
https://www.geeksforgeeks.org/java/atomic-variables-in-java-with-examples/

https://www.geeksforgeeks.org/java/thread-pools-java/
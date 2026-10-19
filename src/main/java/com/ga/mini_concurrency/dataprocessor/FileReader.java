package com.ga.mini_concurrency.dataprocessor;

import com.ga.mini_concurrency.dataprocessor.Exception.FileNotFound;
import com.ga.mini_concurrency.dataprocessor.Exception.InvalidData;
import com.ga.mini_concurrency.dataprocessor.Model.Employee;
import com.ga.mini_concurrency.dataprocessor.Model.Role;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileReader {

    public static List<Employee> readEmployees(String filePath) {
        List<Employee> employees = new ArrayList<>();

        try (Scanner scanner = new Scanner(new File(filePath))) {
            int lineNumber = 1;

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();

                if (!line.isEmpty()) {
                    try {
                        employees.add(parseLine(line, lineNumber));
                    } catch (InvalidData e) {
                        System.out.println("Skipping " + e.getMessage());
                    }
                }
                lineNumber++;
            }
        } catch (FileNotFoundException e) {
            throw new FileNotFound("Can't find " + filePath);
        }

        return employees;
    }

    private static Employee parseLine(String line, int lineNumber) {
        String[] parts = line.split(",");
        if (parts.length != 6) {
            throw new InvalidData("line " + lineNumber + " has missing columns");
        }

        int id;
        String name;
        double salary;
        LocalDate joined;
        Role role;
        double completed;

        try {
            id = Integer.parseInt(parts[0].trim());
            name = parts[1].trim();
            salary = Double.parseDouble(parts[2].trim());
            joined = LocalDate.parse(parts[3].trim());
            role = Role.valueOf(parts[4].trim().toUpperCase());
            completed = Double.parseDouble(parts[5].trim());
        } catch (Exception e) {
            throw new InvalidData("line " + lineNumber + ": " + e.getMessage());
        }

        if (salary < 0) {
            throw new InvalidData("line " + lineNumber + ": salary can't be negative");
        }
        if (completed < 0 || completed > 1) {
            throw new InvalidData("line " + lineNumber + ": projects completion ration must be between 0 and 1");
        }

        return new Employee(id, name, salary, joined, role, completed);
    }


}
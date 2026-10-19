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


}
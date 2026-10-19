package com.ga.mini_concurrency.dataprocessor;

import com.ga.mini_concurrency.dataprocessor.Exception.FileNotFound;
import com.ga.mini_concurrency.dataprocessor.Model.Employee;
import com.ga.mini_concurrency.dataprocessor.Service.EmployeeProcessor;
import com.ga.mini_concurrency.dataprocessor.Service.FileReader;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class DataprocessorApplication implements CommandLineRunner {

	private static final String CSV_FILE = "test_employees.csv";

	public static void main(String[] args) {
		SpringApplication.run(DataprocessorApplication.class, args);
	}

	@Override
	public void run(String... args) {
		try {
			List<Employee> employees = FileReader.readEmployees(CSV_FILE);
			System.out.println("Read " + employees.size() + " employees from " + CSV_FILE + "\n");

			EmployeeProcessor processor = new EmployeeProcessor();
			processor.processAll(employees);

			FileReader.updateSalaries(employees, CSV_FILE);

			System.out.println();
			System.out.println("===== Processing Complete! =======");
			System.out.println("On " + LocalDate.now() + " , " + processor.getProcessedCount() + " employees have been processed.");
			System.out.println("Stats: " + processor.getRaisedEmployees().size() + " employees got a raise... ");

			List<String> names = new ArrayList<>();
			for (Employee employee : processor.getRaisedEmployees()) {
				names.add(employee.getName());
			}

			for (int i = 0; i < names.size(); i += 8) {
				int end = Math.min(i + 8, names.size());
				System.out.println(names.subList(i, end));
			}

			System.out.println("You can view the updated salaries in " + CSV_FILE);
		} catch (FileNotFound e) {
			System.out.println(e.getMessage());
		}
	}
}
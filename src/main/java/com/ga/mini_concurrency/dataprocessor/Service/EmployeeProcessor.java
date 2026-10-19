package com.ga.mini_concurrency.dataprocessor.Service;

import com.ga.mini_concurrency.dataprocessor.Model.Employee;

import java.util.List;

public class EmployeeProcessor {

    public void processAll(List<Employee> employees) {
        for (Employee employee : employees) {
            processOne(employee);
        }
    }

    private void processOne(Employee employee) {
        double raise = CalcRaiseService.calculateRaise(employee);
        employee.applyRaise(raise);
        System.out.println(employee.getName() + "with a raise of: " + raise + "%");
    }
}
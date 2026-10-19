package com.ga.mini_concurrency.dataprocessor.Service;

import com.ga.mini_concurrency.dataprocessor.Model.Employee;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class EmployeeProcessor {

    public void processAll(List<Employee> employees) {
        ExecutorService pool = Executors.newFixedThreadPool(6);

        for (Employee employee : employees) {
            pool.submit(() -> processOne(employee));
        }
        pool.shutdown();

        try { // like join
            pool.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void processOne(Employee employee) {
        double raise = CalcRaiseService.calculateRaise(employee);
        employee.applyRaise(raise);
        System.out.println(Thread.currentThread().getName() + "processed " + employee.getName() + " with a raise of: " + raise + "%");
    }
}
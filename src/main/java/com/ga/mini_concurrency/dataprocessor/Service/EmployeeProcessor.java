package com.ga.mini_concurrency.dataprocessor.Service;

import com.ga.mini_concurrency.dataprocessor.Model.Employee;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class EmployeeProcessor {
    private final Semaphore semaphore = new Semaphore(3);
    private final ReentrantLock lock = new ReentrantLock();
    private final AtomicInteger processedCount = new AtomicInteger();

    private final List<Employee> raisedEmployees = new ArrayList<>();


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
        try{
            semaphore.acquire();

            double raise = CalcRaiseService.calculateRaise(employee);
            employee.applyRaise(raise);

            processedCount.incrementAndGet(); // safely increment
            if (raise > 0){
                addRaisedEmp(employee);
            }

            System.out.println(Thread.currentThread().getName() + " processed " + employee.getName() + " with a raise of: " + raise + "%");
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName()+ " was interrupted while processing " + employee.getName());
        }finally {
            semaphore.release();
        }
    }

    private void addRaisedEmp(Employee employee) {
        lock.lock();
        try {
            raisedEmployees.add(employee);
        } finally {
            lock.unlock();
        }
    }

    public int getProcessedCount() {
        return processedCount.get();
    }

    public List<Employee> getRaisedEmployees() {
        return raisedEmployees;
    }


}
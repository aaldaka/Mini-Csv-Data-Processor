package com.ga.mini_concurrency.dataprocessor.Service;

import com.ga.mini_concurrency.dataprocessor.Model.Employee;

import java.time.LocalDate;
import java.time.Period;

public class CalcRaiseService {
    private static final double MIN_COMPLETED = 0.6;
    private static final double PERCENT_PER_YEAR = 2;
    private static final double BONUS_COMPLETED = 0.8;


    private static int yearsWorked(LocalDate joined) {
        return Period.between(joined, LocalDate.now()).getYears();
    }

    public static double calculateRaise(Employee employee) {

        if (employee.getCompletion() < MIN_COMPLETED){
            System.out.println("No raise gets applied. You must reach at least 60% of the completed projects");
            return 0;
        }

        if (yearsWorked(employee.getJoinDate()) < 1){
            return 0;
        }

        int years = yearsWorked(employee.getJoinDate());
         double yearsRaise = years* PERCENT_PER_YEAR;

        double roleRaise = employee.getRole().getRaisePercentage();
        double totalRaisePercentage = yearsRaise + roleRaise;

        return employee.getCompletion() > BONUS_COMPLETED ? totalRaisePercentage = totalRaisePercentage * 1.5 : totalRaisePercentage;
    }
}
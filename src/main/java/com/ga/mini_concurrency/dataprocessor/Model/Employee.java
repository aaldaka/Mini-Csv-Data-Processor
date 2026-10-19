package com.ga.mini_concurrency.dataprocessor.Model;

import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Employee {
    private int id;
    private String name;
    private double currentSalary;
    private LocalDate joinDate;
    private Role role;
    private double completion;
    private double raisedSalary;
    private double raisePercentage;


    public Employee(int id, String name, double currentSalary, LocalDate joinDate, Role role, double completion) {
        this.id = id;
        this.name = name;
        this.currentSalary = currentSalary;
        this.raisedSalary = currentSalary; // initially equal
        this.joinDate = joinDate;
        this.role = role;
        this.completion = completion;
    }

    public void applyRaise(double raise){
        raisePercentage = raise;
        raisedSalary = currentSalary + (currentSalary * raise);
    }

}

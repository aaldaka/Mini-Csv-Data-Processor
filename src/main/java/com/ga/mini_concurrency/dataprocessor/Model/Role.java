package com.ga.mini_concurrency.dataprocessor.Model;

import lombok.Getter;

@Getter
public enum Role {
    DIRECTOR(5.0),
    EMPLOYEE(1.0),
    MANAGER(2.0);

    private final double raisePercentage;

    Role(double raisePercentage) {
        this.raisePercentage = raisePercentage;
    }
}

package com.company.interview.eqh;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class Employee1 extends Person1 {

    private final String role;

    public Employee1(int age, String name, String role) {
        super(age, name);
        this.role = role;
    }
}

package com.example.modelmappertest.entities;


import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter

public class Employee {
    private Long id;
    private String firstName;
    private String lastName;
    private BigDecimal salary;
    private LocalDate birthday;
    private Address address;

    public Employee(String firstName, BigDecimal salary, Address address) {
        this.firstName = firstName;
        this.salary = salary;
        this.address = address;
    }
}

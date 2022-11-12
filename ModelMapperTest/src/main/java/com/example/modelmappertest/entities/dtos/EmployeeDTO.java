package com.example.modelmappertest.entities.dtos;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class EmployeeDTO {
    private String firstName;
    private BigDecimal salary;
    private String addressCity;

    public EmployeeDTO() {
    }

    @Override
    public String toString() {
        return String.format("%s %.2f %s %n", firstName, salary, addressCity);
    }
}

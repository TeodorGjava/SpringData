package com.example.modelmappertest.entities.dtos;

import com.example.modelmappertest.entities.Address;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class EmployeeDTO {
    private String firstName;
    private BigDecimal salary;
    private String city;
    private Address address;

    public EmployeeDTO() {
    }

    @Override
    public String toString() {
        return String
                .format("First name: %s Salary: %.2f City:%s %n",
                        this.firstName, this.salary, this.address.getCity());
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}

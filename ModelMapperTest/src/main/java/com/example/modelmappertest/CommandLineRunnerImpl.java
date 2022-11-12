package com.example.modelmappertest;

import com.example.modelmappertest.entities.Address;
import com.example.modelmappertest.entities.Employee;
import com.example.modelmappertest.entities.dtos.EmployeeDTO;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {

        System.out.println("run");
        Address address = new Address("Bulgaria", "Plovdiv");
        Employee employee = new Employee("Pesho", BigDecimal.TEN, address);

        ModelMapper modelMapper = new ModelMapper();
        EmployeeDTO employeeDTO = modelMapper.map(employee, EmployeeDTO.class);
        System.out.println(employeeDTO.toString());
    }
}

package com.example.modelmappertest;

import com.example.modelmappertest.entities.Address;
import com.example.modelmappertest.entities.dtos.AddressDTO;
import com.example.modelmappertest.entities.dtos.CreateEmployeeDTO;
import com.example.modelmappertest.services.AddressService;
import com.example.modelmappertest.services.EmployeeService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Scanner;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {
    private final AddressService addressService;
    private final EmployeeService employeeService;

    private final Scanner sc;

    public CommandLineRunnerImpl(AddressService addressService, EmployeeService employeeService, Scanner sc) {
        this.addressService = addressService;
        this.employeeService = employeeService;
        this.sc = sc;
    }

    @Override
    public void run(String... args) throws Exception {
        //createAddress();
        //createEmployee();
       // printAllEmployees();
        this.employeeService.findNameCityAndSalaryById(1L);
    }

    private void printAllEmployees() {
        this.employeeService.findAll().forEach(System.out::println);
    }

    private void createEmployee() {
        String firstName = sc.nextLine();
        BigDecimal salary = new BigDecimal(sc.nextLine());
        LocalDate birthday = LocalDate.parse(sc.nextLine());
        // long addressId = Long.parseLong(sc.nextLine());
        String country = sc.nextLine();
        String city = sc.nextLine();

        AddressDTO address = new AddressDTO(country, city);

        CreateEmployeeDTO employeeDTO =
                new CreateEmployeeDTO(firstName, null, salary, birthday, address);
        this.employeeService.create(employeeDTO);
    }

    private void createAddress() {
        String country = sc.nextLine();
        String city = sc.nextLine();

        AddressDTO data = new AddressDTO(country, city);
        Address address = addressService.create(data);
        this.addressService.create(data);
        System.out.println(address);
    }
/*
        System.out.println("run");
        Address address = new Address("Bulgaria", "Plovdiv");
        Employee employee = new Employee("Pesho", BigDecimal.TEN, address);
        ModelMapper modelMapper = new ModelMapper();
        // PropertyMap<Employee, EmployeeDTO> propertyMap = new PropertyMap<>() {
        //     @Override
        //     protected void configure() {
        //         map().setCity(source.getAddress().getCity());
        //     }
        // };
        // modelMapper.addMappings(propertyMap);
        modelMapper.validate();
        TypeMap<Employee, EmployeeDTO> typeMap = modelMapper.createTypeMap(Employee.class, EmployeeDTO.class);
        typeMap.addMappings(mapping -> mapping.map(s -> s.getAddress().getCity(), EmployeeDTO::setCity));

        typeMap.validate();
        EmployeeDTO employeeDTO = modelMapper.map(employee, EmployeeDTO.class);
        System.out.println(employeeDTO.toString());
    }*/
}

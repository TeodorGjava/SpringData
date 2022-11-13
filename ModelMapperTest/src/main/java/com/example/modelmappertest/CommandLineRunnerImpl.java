package com.example.modelmappertest;

import com.example.modelmappertest.entities.Address;
import com.example.modelmappertest.entities.dtos.AddressDTO;
import com.example.modelmappertest.entities.dtos.CreateEmployeeDTO;
import com.example.modelmappertest.entities.dtos.EmployeeDTO;
import com.example.modelmappertest.services.AddressService;
import com.example.modelmappertest.services.EmployeeService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Scanner;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {
    AddressService addressService;
    EmployeeService employeeService;

    public CommandLineRunnerImpl(AddressService addressService, EmployeeService employeeService) {
        this.addressService = addressService;
        this.employeeService = employeeService;
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner sc = new Scanner(System.in);
        createAddress(sc);

        createEmployee(sc);

    }

    private void createEmployee(Scanner sc) {
        String firstName = sc.nextLine();
        BigDecimal salary = new BigDecimal(sc.nextLine());
        LocalDate birthday = LocalDate.parse(sc.nextLine());
       // long addressId = Long.parseLong(sc.nextLine());
        String country = sc.nextLine();
        String city = sc.nextLine();

        AddressDTO address = new AddressDTO(country,city);

        CreateEmployeeDTO employeeDTO =
                new CreateEmployeeDTO(firstName, null, salary, birthday, address);
        this.employeeService.create(employeeDTO);
    }

    private void createAddress(Scanner sc) {
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

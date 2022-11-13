package com.example.modelmappertest.services;

import com.example.modelmappertest.entities.Address;
import com.example.modelmappertest.entities.Employee;
import com.example.modelmappertest.entities.dtos.CreateEmployeeDTO;
import com.example.modelmappertest.entities.dtos.EmployeeDTO;
import com.example.modelmappertest.repositories.AddressRepository;
import com.example.modelmappertest.repositories.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final AddressRepository addressRepository;
    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeServiceImpl(AddressRepository addressRepository, EmployeeRepository employeeRepository) {
        this.addressRepository = addressRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    @Transactional
    public Employee create(CreateEmployeeDTO employeeDTO) {
        ModelMapper mapper = new ModelMapper();

        Employee mappedEmployee = mapper.map(employeeDTO, Employee.class);
        Optional<Address> address = this.addressRepository.findByCountryAndCity(
                employeeDTO.getAddress().getCountry(),
                employeeDTO.getAddress().getCity());

        address.ifPresent(mappedEmployee::setAddress);

        return this.employeeRepository.save(mappedEmployee);
    }
}

package com.example.modelmappertest.services;

import com.example.modelmappertest.entities.Employee;
import com.example.modelmappertest.entities.dtos.CreateEmployeeDTO;
import com.example.modelmappertest.entities.dtos.EmployeeDTO;
import org.springframework.stereotype.Service;

@Service
public interface EmployeeService {
    Employee create(CreateEmployeeDTO dto);
}

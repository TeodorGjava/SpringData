package com.example.modelmappertest.services;

import com.example.modelmappertest.entities.Employee;
import com.example.modelmappertest.entities.dtos.CreateEmployeeDTO;
import com.example.modelmappertest.entities.dtos.EmployeeDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EmployeeService {
    Employee create(CreateEmployeeDTO dto);

    List<EmployeeDTO> findAll();
    EmployeeDTO findNameCityAndSalaryById(long id);
}

package com.example.modelmappertest.repositories;

import com.example.modelmappertest.entities.Employee;
import com.example.modelmappertest.entities.dtos.EmployeeDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    @Query("SELECT e.firstName,e.salary, e.address.city from Employee e where e.id = :id")
    EmployeeDTO findNameCityAndSalaryById(long id);
}

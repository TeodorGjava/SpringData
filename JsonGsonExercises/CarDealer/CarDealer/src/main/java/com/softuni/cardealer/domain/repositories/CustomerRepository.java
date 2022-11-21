package com.softuni.cardealer.domain.repositories;

import com.softuni.cardealer.domain.entities.Customer;
import com.softuni.cardealer.domain.entities.dtos.customers.ExportOrderedCustomersDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Query(value = "select * from `car_dealer`.customers order by RAND() limit 1", nativeQuery = true)
    Customer getRandomCustomer();

    @Query(value = "select * from `car_dealer`.customers order by birth_date", nativeQuery = true)
    List<Customer> findAllOrderByBirthDate();
}

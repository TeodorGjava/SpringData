package com.softuni.cardealer.domain.services.customer;

import com.softuni.cardealer.domain.entities.Customer;
import com.softuni.cardealer.domain.entities.dtos.customers.ExportOrderedCustomersDTO;

import java.io.IOException;
import java.util.List;

public interface CustomerService {
    List<Customer> findAllOrderByBirthDate() throws IOException;
}

package com.softuni.cardealer.domain.services.customer;

import com.softuni.cardealer.domain.entities.Customer;
import com.softuni.cardealer.domain.entities.dtos.customers.ExportOrderedCustomersDTO;
import com.softuni.cardealer.domain.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

import static com.softuni.cardealer.domain.constants.Paths.JSON_ORDERED_CUSTOMERS_PATH;
import static com.softuni.cardealer.domain.constants.Utils.MODEL_MAPPER;
import static com.softuni.cardealer.domain.constants.Utils.writeJsonIntoFile;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public List<Customer> findAllOrderByBirthDate() throws IOException {

        List<Customer> allOrderByBirthDate = this.customerRepository.findAllOrderByBirthDate().stream()
                .map(customer->MODEL_MAPPER.map(customer,Customer.class)).toList();

        writeJsonIntoFile(allOrderByBirthDate, JSON_ORDERED_CUSTOMERS_PATH);
        return allOrderByBirthDate;
    }
}

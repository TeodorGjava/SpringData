package com.softuni.cardealer;

import com.softuni.cardealer.domain.entities.Customer;
import com.softuni.cardealer.domain.entities.dtos.customers.ExportOrderedCustomersDTO;
import com.softuni.cardealer.domain.services.customer.CustomerService;
import com.softuni.cardealer.domain.services.seed.SeedService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.util.List;

@Component
public class Runner implements CommandLineRunner {

    private final SeedService seedService;
    private final CustomerService customerService;

    public Runner(SeedService seedService, CustomerService customerService) {
        this.seedService = seedService;
        this.customerService = customerService;
    }

    @Override
    public void run(String... args) throws Exception {
        //seedAll();
        List<Customer> allOrderByBirthDate = this.customerService.findAllOrderByBirthDate();
    }

    private void seedAll() throws FileNotFoundException {
        this.seedService.seedSuppliers();
        this.seedService.seedParts();
        this.seedService.seedCars();
        this.seedService.seedCustomers();
        this.seedService.seedSales();
    }
}

package com.softuni.cardealer;

import com.softuni.cardealer.domain.entities.dtos.suppliers.ExportSupplierDTO;
import com.softuni.cardealer.domain.services.car.CarService;
import com.softuni.cardealer.domain.services.customer.CustomerService;
import com.softuni.cardealer.domain.services.seed.SeedService;
import com.softuni.cardealer.domain.services.supplier.SupplierService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@Component
public class Runner implements CommandLineRunner {

    private final SeedService seedService;
    private final SupplierService supplierService;
    private final CarService carService;
    private final CustomerService customerService;

    public Runner(SeedService seedService, SupplierService supplierService, CarService carService, CustomerService customerService) {
        this.seedService = seedService;
        this.supplierService = supplierService;
        this.carService = carService;
        this.customerService = customerService;
    }

    @Override
    public void run(String... args) throws Exception {
        //seedAll();
        //orderedCustomers();
        //carsByToyota();
        //getLocalSuppliersWithCountParts();
        this.carService.findAllCarsAndParts();

    }

    private List<ExportSupplierDTO> getLocalSuppliersWithCountParts() throws IOException {
        return this.supplierService.getSuppliersThatAreNotImporters();
    }

    private void carsByToyota() throws IOException {
        this.carService.findAllOrderByModelAndTravelledDistance();
    }

    //Get all cars from make Toyota and order them by model alphabetically and
    // then by travelled distance descending.
    // Export the list of cars to JSON in the format provided below.
    private void orderedCustomers() throws IOException {
        this.customerService.findAllOrderByBirthDate();
    }

    private void seedAll() throws FileNotFoundException {
        this.seedService.seedSuppliers();
        this.seedService.seedParts();
        this.seedService.seedCars();
        this.seedService.seedCustomers();
        this.seedService.seedSales();
    }
}

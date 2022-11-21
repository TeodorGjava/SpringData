package com.softuni.cardealer.domain.services.seed;

import com.softuni.cardealer.domain.entities.*;
import com.softuni.cardealer.domain.entities.dtos.cars.ImportCarDTO;
import com.softuni.cardealer.domain.entities.dtos.customers.ImportCustomerDTO;
import com.softuni.cardealer.domain.entities.dtos.parts.ImportPartDTO;
import com.softuni.cardealer.domain.entities.dtos.sales.ImportSaleDTO;
import com.softuni.cardealer.domain.entities.dtos.suppliers.ImportSupplierDTO;
import com.softuni.cardealer.domain.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static com.softuni.cardealer.domain.constants.Paths.*;
import static com.softuni.cardealer.domain.constants.Utils.GSON;
import static com.softuni.cardealer.domain.constants.Utils.MODEL_MAPPER;

@Service
public class SeedServiceImpl implements SeedService {
    private final PartRepository partRepository;
    private final CarRepository carRepository;
    private final SupplierRepository supplierRepository;
    private final CustomerRepository customerRepository;
    private final SalesRepository salesRepository;

    @Autowired
    public SeedServiceImpl(PartRepository partRepository, CarRepository carRepository, SupplierRepository supplierRepository, CustomerRepository customerRepository, SalesRepository salesRepository) {
        this.partRepository = partRepository;
        this.carRepository = carRepository;
        this.supplierRepository = supplierRepository;
        this.customerRepository = customerRepository;
        this.salesRepository = salesRepository;
    }

    @Override
    public void seedSuppliers() throws FileNotFoundException {
        if (this.supplierRepository.count() == 0) {
            final FileReader fileReader = new FileReader(JSON_SUPPLIERS_PATH.toFile());
            List<Supplier> suppliers = Arrays.stream(GSON.fromJson(fileReader, ImportSupplierDTO[].class))
                    .map(supplierDTO -> MODEL_MAPPER.map(supplierDTO, Supplier.class)).toList();
            this.supplierRepository.saveAllAndFlush(suppliers);
        }
    }

    @Override
    public void seedParts() throws FileNotFoundException {
        if (this.partRepository.count() == 0) {
            final FileReader fileReader = new FileReader(JSON_PARTS_PATH.toFile());
            List<Part> parts = Arrays.stream(GSON.fromJson(fileReader, ImportPartDTO[].class))
                    .map(importPartDTO -> MODEL_MAPPER.map(importPartDTO, Part.class))
                    .map(this::setRandomSupplier).toList();
            this.partRepository.saveAllAndFlush(parts);
        }
    }

    private Part setRandomSupplier(Part part) {
        Supplier supplier = this.supplierRepository.getRandomSupplier().orElseThrow(NoSuchElementException::new);

        part.setSupplier(supplier);
        return part;

    }


    @Override
    public void seedCars() throws FileNotFoundException {
        if (this.carRepository.count() == 0) {
            final FileReader fileReader = new FileReader(JSON_CARS_PATH.toFile());
            Set<Car> cars = Arrays.stream(GSON.fromJson(fileReader, ImportCarDTO[].class))
                    .map(importCarDTO -> MODEL_MAPPER.map(importCarDTO, Car.class))
                    .map(this::addRandomParts).collect(Collectors.toSet());
            System.out.println();
            this.carRepository.saveAllAndFlush(cars);
        }
    }

    private Car addRandomParts(Car car) {

        Random rnd = new Random();
        int randomNumberOfParts = rnd.nextInt(2, 5) + 1;
        Set<Part> randomParts = new HashSet<>();
        for (int i = 1; i <= randomNumberOfParts; i++) {
            Part part = partRepository.getRandomPart();
            randomParts.add(part);
        }
        car.setParts(randomParts);
        return car;
    }

    @Override
    public void seedCustomers() throws FileNotFoundException {
        if (this.customerRepository.count() == 0) {
            final FileReader fileReader = new FileReader(JSON_CUSTOMERS_PATH.toFile());
            List<Customer> customers = Arrays.stream(GSON.fromJson(fileReader, ImportCustomerDTO[].class))
                    .map(customerDTO -> MODEL_MAPPER.map(customerDTO, Customer.class)).toList();
            this.customerRepository.saveAllAndFlush(customers);
        }
    }

    @Override
    public void seedSales() throws FileNotFoundException {
        final List<Sale> sales = new ArrayList<>();
        Set<Car> saleCars = new HashSet<>();


        if (this.salesRepository.count() == 0) {
            for (int i = (int)this.carRepository.count(); i > 0; i--) {
                Car car = this.carRepository.getRandomCar();
                Customer customer = this.customerRepository.getRandomCustomer();
                Random rnd = new Random();
                BigDecimal discount = new BigDecimal(rnd.nextInt(0, 6));
                ImportSaleDTO saleDTO = new ImportSaleDTO(car, customer, discount.multiply(new BigDecimal(10)));
                Sale sale = MODEL_MAPPER.map(saleDTO, Sale.class);
                sales.add(sale);
            }
            this.salesRepository.saveAllAndFlush(sales);
        }
    }
}

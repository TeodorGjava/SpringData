package com.softuni.cardealer.domain.services;

import com.softuni.cardealer.domain.entities.Car;
import com.softuni.cardealer.domain.entities.Part;
import com.softuni.cardealer.domain.entities.Supplier;
import com.softuni.cardealer.domain.entities.dtos.cars.ImportCarDTO;
import com.softuni.cardealer.domain.entities.dtos.parts.ImportPartDTO;
import com.softuni.cardealer.domain.entities.dtos.suppliers.ImportSupplierDTO;
import com.softuni.cardealer.domain.repositories.CarRepository;
import com.softuni.cardealer.domain.repositories.PartRepository;
import com.softuni.cardealer.domain.repositories.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

import static com.softuni.cardealer.domain.constants.Paths.*;
import static com.softuni.cardealer.domain.constants.Utils.GSON;
import static com.softuni.cardealer.domain.constants.Utils.MODEL_MAPPER;

@Service
public class SeedServiceImpl implements SeedService {
    private final PartRepository partRepository;
    private final CarRepository carRepository;
    private final SupplierRepository supplierRepository;

    @Autowired
    public SeedServiceImpl(PartRepository partRepository, CarRepository carRepository, SupplierRepository supplierRepository) {
        this.partRepository = partRepository;
        this.carRepository = carRepository;
        this.supplierRepository = supplierRepository;
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
            List<Car> cars = Arrays.stream(GSON.fromJson(fileReader, ImportCarDTO[].class))
                    .map(importCarDTO -> MODEL_MAPPER.map(importCarDTO, Car.class))
                    .map(this::addRandomParts).toList();
            this.carRepository.saveAllAndFlush(cars);

        }
    }

    private Car addRandomParts(Car car) {

        Random rnd = new Random();
        int randomNumberOfParts = rnd.nextInt(3, 5);
        Set<Part> randomParts = new HashSet<>();
        for (int i = 0; i <=randomNumberOfParts; i++) {
            Part part = partRepository.getRandomPart();
            randomParts.add(part);
        }
        car.setParts(randomParts);
        return car;
    }

    @Override
    public void seedCustomers() {

    }
}

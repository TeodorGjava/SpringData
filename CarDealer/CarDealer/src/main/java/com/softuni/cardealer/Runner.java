package com.softuni.cardealer;

import com.softuni.cardealer.domain.services.SeedService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Runner implements CommandLineRunner {

    private final SeedService seedService;

    public Runner(SeedService seedService) {
        this.seedService = seedService;
    }

    @Override
    public void run(String... args) throws Exception {
        this.seedService.seedSuppliers();
        this.seedService.seedParts();
        this.seedService.seedCars();
    }
}

package com.softuni.cardealer.domain.repositories;

import com.softuni.cardealer.domain.entities.Car;
import com.softuni.cardealer.domain.entities.Part;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    @Query(value = "select * from `car_dealer`.cars order by RAND() limit 1", nativeQuery = true)
    Car getRandomCar();
}

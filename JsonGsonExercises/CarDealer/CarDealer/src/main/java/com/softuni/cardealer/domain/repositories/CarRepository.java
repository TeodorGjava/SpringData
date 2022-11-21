package com.softuni.cardealer.domain.repositories;

import com.softuni.cardealer.domain.entities.Car;
import com.softuni.cardealer.domain.entities.Customer;
import com.softuni.cardealer.domain.entities.Part;
import com.softuni.cardealer.domain.entities.dtos.cars.CarWithPartsDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    @Query(value = "select * from `car_dealer`.cars order by RAND() limit 1", nativeQuery = true)
    Car getRandomCar();

    @Query(value = "select * from `car_dealer`.cars c where c.make = 'Toyota' order by c.model,c.travelled_distance desc", nativeQuery = true)
    List<Car> findAllOrderByModelAndTravelledDistance();

   @Query("Select c from Car c")
    List<CarWithPartsDTO> findAllCarsAndParts();
}

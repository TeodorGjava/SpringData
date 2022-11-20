package com.softuni.cardealer.domain.repositories;

import com.softuni.cardealer.domain.entities.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    @Query(value = "select * from `car_dealer`.suppliers order by RAND() limit 1", nativeQuery = true)
    Optional<Supplier> getRandomSupplier();
}

package com.softuni.cardealer.domain.repositories;

import com.softuni.cardealer.domain.entities.Part;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.Set;

@Repository
public interface PartRepository extends JpaRepository<Part,Long> {
    @Query(value = "select * from `car_dealer`.parts order by RAND() limit 1", nativeQuery = true)
    Part getRandomPart();
}

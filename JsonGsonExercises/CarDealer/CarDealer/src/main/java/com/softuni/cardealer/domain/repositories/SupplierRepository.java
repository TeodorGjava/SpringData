package com.softuni.cardealer.domain.repositories;

import com.softuni.cardealer.domain.entities.Supplier;
import com.softuni.cardealer.domain.entities.dtos.suppliers.ExportSupplierDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    @Query(value = "select * from `car_dealer`.suppliers order by RAND() limit 1", nativeQuery = true)
    Optional<Supplier> getRandomSupplier();

    @Query("select new com.softuni.cardealer.domain.entities.dtos.suppliers" +
            ".ExportSupplierDTO(s.id, s.name, count(p.id)) " +
            "FROM Supplier s JOIN s.parts p " +
            "WHERE s.isImporter = false " +
            "GROUP BY s.id ORDER BY p.id DESC")
    Optional<List<ExportSupplierDTO>> getSuppliersThatAreNotImporters();
}

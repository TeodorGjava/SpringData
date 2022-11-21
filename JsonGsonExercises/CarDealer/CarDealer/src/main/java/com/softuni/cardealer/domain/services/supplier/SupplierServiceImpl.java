package com.softuni.cardealer.domain.services.supplier;

import com.softuni.cardealer.domain.entities.Supplier;
import com.softuni.cardealer.domain.entities.dtos.parts.PartDTO;
import com.softuni.cardealer.domain.entities.dtos.suppliers.ExportSupplierDTO;
import com.softuni.cardealer.domain.repositories.SupplierRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

import static com.softuni.cardealer.domain.constants.Paths.JSON_LOCAL_SUPPLIERS_PATH;
import static com.softuni.cardealer.domain.constants.Paths.JSON_ORDERED_CUSTOMERS_PATH;
import static com.softuni.cardealer.domain.constants.Utils.MODEL_MAPPER;
import static com.softuni.cardealer.domain.constants.Utils.writeJsonIntoFile;

@Service
public class SupplierServiceImpl implements SupplierService {
    private final SupplierRepository supplierRepository;

    public SupplierServiceImpl(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    @Override
    public List<ExportSupplierDTO> getSuppliersThatAreNotImporters() throws IOException {
        List<ExportSupplierDTO> suppliersThatAreNotImporters = this.supplierRepository.getSuppliersThatAreNotImporters()
                .orElseThrow(NoSuchElementException::new)
                .stream().map(supplier -> MODEL_MAPPER.map(supplier, ExportSupplierDTO.class)).toList();
        writeJsonIntoFile(suppliersThatAreNotImporters, JSON_LOCAL_SUPPLIERS_PATH);
        return suppliersThatAreNotImporters;
    }
}

package com.softuni.cardealer.domain.services.supplier;

import com.softuni.cardealer.domain.entities.Supplier;
import com.softuni.cardealer.domain.entities.dtos.suppliers.ExportSupplierDTO;

import java.io.IOException;
import java.util.List;

public interface SupplierService {
    List<ExportSupplierDTO> getSuppliersThatAreNotImporters() throws IOException;
}

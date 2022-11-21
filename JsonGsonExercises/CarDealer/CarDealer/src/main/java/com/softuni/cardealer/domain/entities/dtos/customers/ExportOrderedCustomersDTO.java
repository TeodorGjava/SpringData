package com.softuni.cardealer.domain.entities.dtos.customers;

import com.softuni.cardealer.domain.entities.Sale;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class ExportOrderedCustomersDTO {
    private Long id;
    private String name;
    private String birthDate;
    private Boolean isYoungDriver;
    private Set<Sale> sales;

    public ExportOrderedCustomersDTO() {
        this.sales = new HashSet<>();
    }

    public ExportOrderedCustomersDTO(Long id, String name, String birthDate, Boolean isYoungDriver) {
        this();
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.isYoungDriver = isYoungDriver;
    }
}

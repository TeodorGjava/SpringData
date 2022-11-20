package com.softuni.cardealer.domain.entities.dtos.customers;

import com.softuni.cardealer.domain.entities.Sale;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExportOrderedCustomersDTO {
    private Long id;
    private String name;
    private String birthDate;
    private Boolean isYoungDriver;
    private Set<Sale> sales;
}

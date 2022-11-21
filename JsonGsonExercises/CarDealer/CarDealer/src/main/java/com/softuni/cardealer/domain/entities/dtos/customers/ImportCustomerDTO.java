package com.softuni.cardealer.domain.entities.dtos.customers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ImportCustomerDTO {

    private String name;

    private String birthDate;

    private Boolean isYoungDriver;
}

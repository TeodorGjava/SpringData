package com.softuni.cardealer.domain.entities.dtos.cars;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ImportCarDTO {
    private String make;
    private String model;
    private Long travelledDistance;
}

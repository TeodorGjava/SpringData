package com.softuni.cardealer.domain.entities.dtos.cars;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExportCarDTO {
    private String make;
    private String model;
    private Long travelledDistance;
}

package com.softuni.cardealer.domain.entities.dtos.cars;

import com.softuni.cardealer.domain.entities.Part;
import com.softuni.cardealer.domain.entities.dtos.parts.PartDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter

public class CarWithPartsDTO {
    private String make;
    private String model;
    private Long travelledDistance;
    private Set<PartDTO> parts;

    public CarWithPartsDTO() {

    }

    public CarWithPartsDTO(Set<PartDTO>parts,String make, String model, Long travelledDistance) {
        this.parts=parts;
        this.make = make;
        this.model = model;
        this.travelledDistance = travelledDistance;
    }
}

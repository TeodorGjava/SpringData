package com.softuni.cardealer.domain.entities.dtos.suppliers;

import com.softuni.cardealer.domain.entities.BaseEntity;
import com.softuni.cardealer.domain.entities.Part;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.OneToMany;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ImportSupplierDTO {
    private String name;
    private Boolean isImporter;

}

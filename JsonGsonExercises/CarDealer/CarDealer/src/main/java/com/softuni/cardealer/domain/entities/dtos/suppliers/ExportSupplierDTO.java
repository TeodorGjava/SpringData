package com.softuni.cardealer.domain.entities.dtos.suppliers;

import com.google.gson.annotations.SerializedName;
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
@AllArgsConstructor
@NoArgsConstructor
public class ExportSupplierDTO {
    @SerializedName("Id")
    private Long id;
    @SerializedName("Name")
    private String name;
    private Long partsCount;
}

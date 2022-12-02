package com.example.football.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "stat")
@XmlAccessorType(XmlAccessType.FIELD)
public class ImportStatDTO {
    @NotNull
    @DecimalMin(value = "1")
    @XmlElement
    private Double passing;
    @DecimalMin(value = "1")
    @NotNull
    @XmlElement
    private Double shooting;
    @DecimalMin(value = "1")
    @NotNull
    @XmlElement
    private Double endurance;

}

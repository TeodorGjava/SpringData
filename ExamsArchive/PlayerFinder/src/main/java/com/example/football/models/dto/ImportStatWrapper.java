package com.example.football.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "stats")
@XmlAccessorType(XmlAccessType.FIELD)
public class ImportStatWrapper {
    @XmlElement(name = "stat")
    private List<ImportStatDTO> stats;
}

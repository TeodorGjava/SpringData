package com.example.football.models.dto;

import com.example.football.models.enums.Positions;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "player")
@XmlAccessorType(XmlAccessType.FIELD)
public class ImportPlayerDTO {
    @Size(min = 2)
    @NotNull
    @XmlElement(name = "first-name")
    private String firstName;
    @Size(min = 2)
    @NotNull
    @XmlElement(name = "last-name")
    private String lastName;
    @NotNull
    @Email
    @XmlElement
    private String email;
    @XmlElement(name = "birth-date")
    @NotNull
    private String birthDate;
    @XmlElement
    @Enumerated(EnumType.STRING)
    @NotNull
    private Positions position;
    @XmlElement(name = "town")
    @NotNull
    private TownWithNameDTO town;
    @XmlElement(name = "team")
    @NotNull
    private TeamWithNameDTO team;
    @XmlElement(name = "stat")
    @NotNull
    private StatWithIdDTO stat;
}

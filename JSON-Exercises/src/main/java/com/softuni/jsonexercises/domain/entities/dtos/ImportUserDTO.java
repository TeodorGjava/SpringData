package com.softuni.jsonexercises.domain.entities.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ImportUserDTO {
    private String firstName;
    private String lastName;
    private Integer age;
}

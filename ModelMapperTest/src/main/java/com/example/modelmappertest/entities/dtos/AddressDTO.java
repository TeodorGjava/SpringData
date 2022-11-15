package com.example.modelmappertest.entities.dtos;

import lombok.Getter;

@Getter
public class AddressDTO {
    private String country;

    private String city;

    public AddressDTO(String country, String city) {
        this.country = country;
        this.city = city;
    }
}

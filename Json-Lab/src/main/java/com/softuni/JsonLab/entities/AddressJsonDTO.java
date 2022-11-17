package com.softuni.JsonLab.entities;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
@Getter
@Setter
public class AddressJsonDTO implements Serializable {
    @Expose
    private String country;
    @Expose
    private String town;
    @Expose
    private String street;

    public AddressJsonDTO(String country, String town, String street) {
        this.country = country;
        this.town = town;
        this.street = street;
    }
}

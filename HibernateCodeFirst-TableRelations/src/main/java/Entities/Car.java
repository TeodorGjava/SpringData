package Entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
public class Car extends Vehicle {
    private static final String TYPE_VEHICLE = "car";


    public Car() {
        super(TYPE_VEHICLE);
    }



}


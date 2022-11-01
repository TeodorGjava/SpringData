package Entities;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
public class Bike extends Vehicle{
    private static final String TYPE_VEHICLE = "bike";
    public Bike() {
        super(TYPE_VEHICLE);
    }
}

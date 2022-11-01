package Entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
public class Truck extends Vehicle {
    private static final String TYPE_VEHICLE = "truck";
    @Column(name = "load_capacity")
    private Double loadCapacity;

    public Truck() {
        super(TYPE_VEHICLE);
    }

    public Double getSeats() {
        return loadCapacity;
    }

    public void setSeats(Double seats) {
        this.loadCapacity = seats;
    }


}

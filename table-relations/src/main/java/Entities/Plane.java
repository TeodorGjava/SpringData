package Entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
public class Plane extends Vehicle {
    private static final String TYPE_VEHICLE = "plane";
    private Integer passengerCapacity;

    public Plane() {
        super(TYPE_VEHICLE);
    }
@Column
    public Integer getPassengerCapacity() {
        return passengerCapacity;
    }

    public void setPassengerCapacity(Integer passengerCapacity) {
        this.passengerCapacity = passengerCapacity;
    }
}

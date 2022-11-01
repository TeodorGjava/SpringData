package hasEntitiesExample;

import javax.persistence.*;

@Entity
@Table(name = "has_plate_numbers")
public class PlateNumber {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String number;
    @OneToOne(targetEntity = Motorcycle.class,mappedBy = "plateNumber")
    private Motorcycle motorcycle;

    public PlateNumber() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Motorcycle getMotorcycle() {
        return motorcycle;
    }

    public void setMotorcycle(Motorcycle motorcycle) {
        this.motorcycle = motorcycle;
    }
}


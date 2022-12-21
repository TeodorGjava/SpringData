package softuni.exam.models.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import softuni.exam.models.enums.CarType;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cars")
public class Car extends BaseEntity {

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CarType carType;

    @Size(min = 2, max = 30)
    @Column(nullable = false)
    private String carMake;

    @Size(min = 2, max = 30)
    @Column(nullable = false)
    private String carModel;

    @Min(1)
    @Column(nullable = false)
    private Integer year;

    @Size(min = 2, max = 30)
    @Column(nullable = false, unique = true)
    private String plateNumber;

    @Min(1)
    @Column(nullable = false)
    private Integer kilometers;

    @Column(nullable = false)
    private Double engine;
    @OneToMany
    private List<Task> tasks;
}


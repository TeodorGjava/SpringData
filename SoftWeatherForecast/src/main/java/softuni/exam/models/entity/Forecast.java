package softuni.exam.models.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import softuni.exam.models.enums.DaysOfWeek;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Size;
import java.time.LocalTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "forecasts")
public class Forecast extends BaseEntity {
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DaysOfWeek dayOfWeek;

    @DecimalMax(value = "60")
    @DecimalMin(value = "-20")
    @Column(nullable = false)
    private Double maxTemperature;

    @DecimalMax(value = "40")
    @DecimalMin(value = "-50")
    @Column(nullable = false)
    private Double minTemperature;

    @Column(nullable = false)
    private LocalTime sunrise;

    @Column(nullable = false)
    private LocalTime sunset;

    @ManyToOne
    private City city;
}

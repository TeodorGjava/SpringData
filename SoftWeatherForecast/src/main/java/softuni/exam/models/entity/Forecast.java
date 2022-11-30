package softuni.exam.models.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import softuni.exam.models.enums.DaysOfWeek;

import javax.persistence.*;
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

    @Size(min = -20, max = 60)
    @Column(nullable = false)
    private Double maxTemperature;

    @Size(min = -50, max = 40)
    @Column(nullable = false)
    private Double minTemperature;

    @Column(nullable = false)
    private LocalTime sunrise;

    @Column(nullable = false)
    private LocalTime sunset;

    @ManyToOne
    private City city;
}

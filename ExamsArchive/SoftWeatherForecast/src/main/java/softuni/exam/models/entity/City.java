package softuni.exam.models.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "cities")
public class City extends BaseEntity {
    @Size(min = 2, max = 60)
    @Column(nullable = false, unique = true)
    private String cityName;
    @Size(min = 2)
    @Column(columnDefinition = "TEXT")
    private String description;
    @Min(500)
    @Column(nullable = false)
    private Integer population;
    @ManyToOne
    private Country country;

    @OneToMany
    private List<Forecast> forecasts;

    public void addForecast(Forecast forecastToSave) {
        this.forecasts.add(forecastToSave);
    }
}

package softuni.exam.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import softuni.exam.models.enums.DaysOfWeek;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "forecast")
@XmlAccessorType(XmlAccessType.FIELD)
public class ImportForecastDTO {
    @XmlElement(name = "day_of_week")
    @NotNull
    private DaysOfWeek dayOfWeek;
    @DecimalMax(value = "60")
    @DecimalMin(value = "-20")
    @XmlElement(name = "max_temperature")
    private Double maxTemperature;
    @DecimalMax(value = "40")
    @DecimalMin(value = "-50")
    @XmlElement(name = "min_temperature")
    private Double minTemperature;
    @NotNull
    @XmlElement
    private String sunrise;
    @NotNull
    @XmlElement
    private String sunset;
    @NotNull
    @XmlElement
    private Long city;
}

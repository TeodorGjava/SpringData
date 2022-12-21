package softuni.exam.models.dto.xml.cars;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import softuni.exam.models.enums.CarType;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "car")
@XmlAccessorType(XmlAccessType.FIELD)
public class ImportCarDTO {
    @Size(min = 2, max = 30)
    @NotNull
    @XmlElement
    private String carMake;
    @Size(min = 2, max = 30)
    @NotNull
    @XmlElement
    private String carModel;
    @Min(1)
    @NotNull
    @XmlElement
    private Integer year;
    @Size(min = 2, max = 30)
    @NotNull
    @XmlElement
    private String plateNumber;
    @Min(1)
    @NotNull
    private Integer kilometers;
    @NotNull
    @XmlElement
    private Double engine;
    @NotNull
    @XmlElement
    @Enumerated(EnumType.STRING)
    private CarType carType;
}

package softuni.exam.models.dto.xml.tasks;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import softuni.exam.models.dto.xml.cars.CarDTO;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "task")
@XmlAccessorType(XmlAccessType.FIELD)
public class ImportTaskDTO {
    @XmlElement
    @NotNull
    private String date;
    @XmlElement
    @NotNull
    @DecimalMin("0.1")
    private Double price;
    @XmlElement(name = "car")
    private CarDTO car;
    @XmlElement(name = "mechanic")
    private MechanicDTO mechanic;
    @XmlElement
    private PartDTO part;
}
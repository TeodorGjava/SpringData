package softuni.exam.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "offer")
@XmlAccessorType(XmlAccessType.FIELD)
public class ImportOfferDTO {
    @XmlElement
    @DecimalMin("2")
    @NotNull
    private Double price;
    @NotNull
    @XmlElement(name = "agent")
    private AgentWithNameDTO agent;
    @NotNull
    @XmlElement(name = "apartment")
    private ApartmentIdDTO apartmentIdDTO;
    @NotNull
    @XmlElement
    private String publishedOn;
}

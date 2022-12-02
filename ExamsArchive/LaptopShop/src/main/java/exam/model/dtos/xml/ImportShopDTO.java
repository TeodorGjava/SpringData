package exam.model.dtos.xml;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "shop")
@XmlAccessorType(XmlAccessType.FIELD)
public class ImportShopDTO {
    // <address>04 Pond Junction</address>
    @Size(min = 4)
    @NotNull
    @XmlElement
    private String address;
    //        <employee-count>47</employee-count>
    @Min(1)
    @NotNull
    @Max(50)
    @XmlElement(name = "employee-count")
    private Integer employeeCount;
    //        <income>625273</income>
    @Min(20000)
    @NotNull
    @XmlElement
    private Long income;
    //        <name>Er</name>
    @NotNull
    @Size(min = 4)
    @XmlElement
    private String name;
    //        <shop-area>278</shop-area>
    @NotNull
    @Min(150)
    @XmlElement(name = "shop-area")
    private Integer shopArea;
    //        <town>
    @XmlElement(name = "town")
    private TownWithNameDTO town;
    //            <name>Hamburg</name>
    //        </town>
}

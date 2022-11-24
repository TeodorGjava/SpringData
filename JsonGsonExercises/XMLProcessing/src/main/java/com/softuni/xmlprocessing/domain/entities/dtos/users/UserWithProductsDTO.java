package com.softuni.xmlprocessing.domain.entities.dtos.users;

import com.softuni.xmlprocessing.domain.entities.dtos.products.SoldProductDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "user")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserWithProductsDTO {
    @XmlElementWrapper(name = "sold-products")
    @XmlElement(name = "product")
    private List<SoldProductDTO> soldProducts;


}

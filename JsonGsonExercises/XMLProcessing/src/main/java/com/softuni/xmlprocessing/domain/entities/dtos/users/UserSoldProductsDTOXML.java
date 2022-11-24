package com.softuni.xmlprocessing.domain.entities.dtos.users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.*;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@XmlRootElement(name = "user")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserSoldProductsDTOXML {

        @XmlAttribute(name = "first-name")
        private String firstName;

        @XmlAttribute(name = "last-name")
        private String lastName;

        @XmlElement(name = "sold-product")
        private XMLSoldProductsWrapperDto soldProducts;

}

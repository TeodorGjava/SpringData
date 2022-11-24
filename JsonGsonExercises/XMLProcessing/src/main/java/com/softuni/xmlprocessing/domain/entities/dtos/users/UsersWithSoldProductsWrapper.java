package com.softuni.xmlprocessing.domain.entities.dtos.users;

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
@XmlRootElement(name = "users")
@XmlAccessorType(XmlAccessType.FIELD)
public class UsersWithSoldProductsWrapper {
    // XmlElement sets the name of the entities
    @XmlElement(name = "user")
    private List<UserWithProductsDTO> users;

}

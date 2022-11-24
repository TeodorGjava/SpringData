package com.softuni.xmlprocessing.domain.entities.dtos.users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsersWithProductsWrapperDTO {

    private Integer usersCount;
    private List<UsersWithProductsDTO> users;

    public UsersWithProductsWrapperDTO(List<UsersWithProductsDTO> users) {
        this.users = users;
        this.usersCount = users.size();
    }
}

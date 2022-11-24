package com.softuni.jsonexercises.domain.entities.dtos.users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.relational.core.sql.In;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor

public class UsersWithProductsWrapperDTO {
    private Integer usersCount;
    private List<UsersWithProductsDTO> users;

    public UsersWithProductsWrapperDTO(List<UsersWithProductsDTO> users) {
        this.users = users;
        this.usersCount = users.size();
    }
}

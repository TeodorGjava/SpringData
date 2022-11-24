package com.softuni.jsonexercises.domain.entities.json.dtos.users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

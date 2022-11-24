package com.softuni.jsonexercises.domain.services;

import com.softuni.jsonexercises.domain.entities.json.dtos.users.UserSoldProductsDTO;
import com.softuni.jsonexercises.domain.entities.json.dtos.users.UsersWithProductsWrapperDTO;

import java.io.IOException;
import java.util.List;

public interface UserService {

    List<UserSoldProductsDTO> findAllBySellingProductsBuyerIsNotNullOrderBySellingProductsBuyerFirstNameSellingProductsBuyerLastName() throws IOException;

    UsersWithProductsWrapperDTO usersAndProducts() throws IOException;
}

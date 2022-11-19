package com.softuni.jsonexercises.domain.services;

import com.softuni.jsonexercises.domain.entities.dtos.users.UserSoldProductsDTO;
import com.softuni.jsonexercises.domain.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

import static com.softuni.jsonexercises.constants.Paths.JSON_SOLD_PRODUCTS_PATH;
import static com.softuni.jsonexercises.constants.Utils.MODEL_MAPPER;
import static com.softuni.jsonexercises.constants.Utils.writeJsonIntoFile;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserSoldProductsDTO> findAllBySellingProductsBuyerIsNotNullOrderBySellingProductsBuyerFirstNameSellingProductsBuyerLastName() throws IOException {
        final List<UserSoldProductsDTO> users = this.userRepository
                .findAllBySellingProductsBuyerIsNotNullOrderBySellingProductsBuyerFirstName().orElseThrow(NoSuchElementException::new)
                .stream()
                .map(user -> MODEL_MAPPER.map(user, UserSoldProductsDTO.class))
                .toList();
        writeJsonIntoFile(users,JSON_SOLD_PRODUCTS_PATH);
        return users;
    }
}

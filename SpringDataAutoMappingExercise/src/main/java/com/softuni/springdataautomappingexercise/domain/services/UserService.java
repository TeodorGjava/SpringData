package com.softuni.springdataautomappingexercise.domain.services;

import com.softuni.springdataautomappingexercise.domain.entities.User;

public interface UserService {
    String registerUser(String[] data);

    String loginUser(String[] input);

    User findByEmail(String email);

    String logoutUser();

    User findByFullName(String fullName);


}

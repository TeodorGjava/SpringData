package com.softuni.springdataautomappingexercise.domain.services;

import com.softuni.springdataautomappingexercise.domain.entities.User;

public interface UserService {
    String registerUser(String[] data);

    User findUserByFullName(String fullName);

    String loginUser(String[] input);

    User findByEmail(String email);

    String logoutUser();

    void setUserOnlineFindByEmail(String email);



}

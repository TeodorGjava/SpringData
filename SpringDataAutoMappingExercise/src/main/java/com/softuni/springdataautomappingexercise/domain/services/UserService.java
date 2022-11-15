package com.softuni.springdataautomappingexercise.domain.services;

public interface UserService {
    String registerUser(String[] data);

    String loginUser(String[] input);
}

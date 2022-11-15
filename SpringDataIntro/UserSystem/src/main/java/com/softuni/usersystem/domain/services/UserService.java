package com.softuni.usersystem.domain.services;

import com.softuni.usersystem.domain.entities.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsersByEmailProvider(String provider);

    void save(User user);

    long getCount();

    List<String> getUserNamesAndAgeByAgeRange(int lowBound, int highBound);
}

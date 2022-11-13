package com.softuni.springdataautomappingexercise.domain.services;

import com.softuni.springdataautomappingexercise.domain.entities.User;
import com.softuni.springdataautomappingexercise.domain.entities.dtos.UserRegister;
import com.softuni.springdataautomappingexercise.domain.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper mapper = new ModelMapper();


    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public String registerUser(String[] data) {
        final String email = data[1];
        final String password = data[2];
        final String confirmPassword = data[3];
        final String fullName = data[4];
        UserRegister userRegister = new UserRegister(email, password, confirmPassword, fullName);

        User user = this.mapper.map(userRegister, User.class);

        if (this.userRepository.count() == 0) {
            user.setAdministrator(true);
        }
        this.userRepository.save(user);
        return userRegister.successfulRegister();
    }

    @Override
    public String loginUser(String[] input) {
return null;
    }
}

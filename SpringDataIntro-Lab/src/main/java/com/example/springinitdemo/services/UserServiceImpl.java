package com.example.springinitdemo.services;

import com.example.springinitdemo.models.User;
import com.example.springinitdemo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public void register(String username, int age) {
        if (username.isBlank() || age < 18) {
            throw new RuntimeException("Failed Validation");
        }
        Optional<User> user = this.userRepository.findByUsername(username);
        if (user.isPresent()) {
            throw new RuntimeException("There is an user with this name!");
        }
    }
}

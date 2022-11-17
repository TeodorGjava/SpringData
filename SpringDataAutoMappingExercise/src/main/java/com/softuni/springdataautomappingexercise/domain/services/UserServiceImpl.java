package com.softuni.springdataautomappingexercise.domain.services;

import com.softuni.springdataautomappingexercise.domain.entities.User;
import com.softuni.springdataautomappingexercise.domain.entities.dtos.UserLogin;
import com.softuni.springdataautomappingexercise.domain.entities.dtos.UserRegisterDTO;
import com.softuni.springdataautomappingexercise.domain.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

import static com.softuni.springdataautomappingexercise.domain.constants.Validations.INVALID_PASSWORD_OR_USERNAME_MESSAGE;
import static com.softuni.springdataautomappingexercise.domain.constants.Validations.NO_USER_LOGGED_IN_MESSAGE;
import static java.lang.String.format;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private User user;
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

        UserRegisterDTO userRegisterDTO = new UserRegisterDTO(email, password, confirmPassword, fullName);

        User user = this.mapper.map(userRegisterDTO, User.class);
        user.setOffline();
        user.setAdministrator(this.userRepository.count() == 0);

        boolean isRegistered = this.userRepository.findFirstByEmail(userRegisterDTO.getEmail()).isPresent();
        if (isRegistered) {
            return "User with this email already exists.";
        }
        this.userRepository.save(user);
        System.out.println("User " + user.getFullName() + " successfully registered");
        return userRegisterDTO.successfulRegister();
    }

    @Override
    public User findUserByFullName(String fullName) {
        return this.userRepository.findUserByFullName(fullName).orElseThrow(NoSuchElementException::new);
    }


    @Override
    public String loginUser(String[] input) {
        final String email = input[1];
        final String password = input[2];

        final UserLogin userLogin = new UserLogin(email, password);

        boolean userExists = this.userRepository.existsUserByEmail(userLogin.getEmail());
        if (userExists) {
            this.user = this.userRepository.findByEmail(userLogin.getEmail()).get();
            if (this.user.getPassword().equals(password)) {
                this.user.setOnline();
                this.userRepository.setUserOnlineFindByEmail(email);
                return user.successfulLogin();
            } else {
                return INVALID_PASSWORD_OR_USERNAME_MESSAGE;
            }
        }
        return INVALID_PASSWORD_OR_USERNAME_MESSAGE;
    }

    @Override
    public User findByEmail(String email) {
        return this.userRepository.findByEmail(email).orElseThrow(NoSuchFieldError::new);
    }

    @Override
    public String logoutUser() {
        if (this.user.getOnline()) {
            this.user.setOffline();
            return "User " + this.user.getFullName() + " logged out";
        }
        return NO_USER_LOGGED_IN_MESSAGE;
    }

    @Override
    public void setUserOnlineFindByEmail(String email) {
        this.userRepository.setUserOnlineFindByEmail(email);
    }


}

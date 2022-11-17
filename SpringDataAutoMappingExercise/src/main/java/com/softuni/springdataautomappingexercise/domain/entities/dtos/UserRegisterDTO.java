package com.softuni.springdataautomappingexercise.domain.entities.dtos;

import com.softuni.springdataautomappingexercise.domain.entities.User;

import java.util.regex.Pattern;

import static com.softuni.springdataautomappingexercise.domain.constants.Validations.*;

public class UserRegisterDTO {
    private String email;
    private String password;
    private String confirmPassword;
    private String fullName;

    //TODO: validate thru setters not void method
    public UserRegisterDTO(String email, String password, String confirmPassword, String fullName) {
        setEmail(email);
        setPassword(password);
        setConfirmPassword(confirmPassword);
        this.fullName = fullName;

    }



    public User toUser() {
        return new User(email, password, fullName);
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        if (Pattern.matches(EMAIL_PATTERN, email)) {
            this.email = email;
        } else {
            throw new IllegalArgumentException(INVALID_EMAIL_MESSAGE);
        }
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if (Pattern.matches(PASSWORD_PATTERN, password)) {
            this.password = password;
        } else {
            throw new IllegalArgumentException(INVALID_PASSWORD_OR_USERNAME_MESSAGE);
        }
    }

    public String getConfirmPassword() {
        return this.confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        if (!confirmPassword.equals(this.password)) {
            throw new IllegalArgumentException(PASSWORDS_DO_NOT_MATCH_MESSAGE);
        }
        this.confirmPassword = confirmPassword;
    }

    public String getFullName() {
        return this.fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String successfulRegister() {
        return String.format("%s was registered", this.fullName);
    }


}

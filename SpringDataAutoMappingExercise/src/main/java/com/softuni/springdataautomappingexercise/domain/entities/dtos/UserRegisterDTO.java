package com.softuni.springdataautomappingexercise.domain.entities.dtos;

import com.softuni.springdataautomappingexercise.domain.entities.User;

import java.util.regex.Pattern;

import static com.softuni.springdataautomappingexercise.domain.constants.Validations.*;

public class UserRegisterDTO {
    private String email;
    private String password;
    private String confirmPassword;
    private String fullName;

    public UserRegisterDTO(String email, String password, String confirmPassword, String fullName) {
        setEmail(email);
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.fullName = fullName;
        validate();
    }

    private void validate() {

        final boolean isPasswordValid = Pattern.matches(PASSWORD_PATTERN, this.password);

        if (!isPasswordValid) {
            System.out.println(INVALID_PASSWORD_MESSAGE);
        }
        if (!password.equals(confirmPassword)) {
            System.out.println(PASSWORDS_DO_NOT_MATCH_MESSAGE);
        }

    }

    public User toUser() {
        return new User(email, password, fullName);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (Pattern.matches(EMAIL_PATTERN, email)) {
            this.email = email;
        }else{
            System.out.println(INVALID_EMAIL_MESSAGE);
        }
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String successfulRegister() {
        return String.format("%s was registered", this.fullName);
    }


}

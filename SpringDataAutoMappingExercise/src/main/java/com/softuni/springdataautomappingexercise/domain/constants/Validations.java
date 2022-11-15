package com.softuni.springdataautomappingexercise.domain.constants;

public enum Validations {
    ;
    public static final String EMAIL_PATTERN = "^([a-z0-9_\\.-]+)@([\\da-z\\.-]+)\\.([a-z\\.]{2,63})$";
    public static final String PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{6,}$";
    public static final String INVALID_EMAIL_MESSAGE = "Incorrect email!";
    public static final String INVALID_PASSWORD_MESSAGE = "Incorrect username / password!";
    public static final String PASSWORDS_DONT_MATCH_MESSAGES = "Passwords are not matching!";
    public static final String COMMAND_NOT_FOUND_MESSAGE= "Command not found!";
}

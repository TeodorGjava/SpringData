package com.softuni.springdataautomappingexercise.domain;

import com.softuni.springdataautomappingexercise.domain.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Scanner;

import static com.softuni.springdataautomappingexercise.domain.constants.Commands.*;
import static com.softuni.springdataautomappingexercise.domain.constants.Validations.COMMAND_NOT_FOUND_MESSAGE;

@Component
public class ConsoleRunner implements CommandLineRunner {
    private static final Scanner sc = new Scanner(System.in);
    private final UserService userService;

    public ConsoleRunner(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
        String input = sc.nextLine();
        while (!input.equals("end")) {
            final String[] data = input.split("[|]");
            final String command = data[0];
            final String output = switch (command) {
                case REGISTER_USER -> userService.registerUser(data);
                case LOGIN_USER -> userService.loginUser(data);
                case LOGOUT -> userService.logoutUser();
                default -> COMMAND_NOT_FOUND_MESSAGE;
            };
            System.out.println(output);
            input = sc.nextLine();
        }
    }
}

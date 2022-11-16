package com.softuni.springdataautomappingexercise.domain;

import com.softuni.springdataautomappingexercise.domain.entities.User;
import com.softuni.springdataautomappingexercise.domain.services.GameService;
import com.softuni.springdataautomappingexercise.domain.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

import static com.softuni.springdataautomappingexercise.domain.constants.Commands.*;
import static com.softuni.springdataautomappingexercise.domain.constants.Validations.COMMAND_NOT_FOUND_MESSAGE;

@Component
public class ConsoleRunner implements CommandLineRunner {
    private static final Scanner sc = new Scanner(System.in);
    private final UserService userService;
    private final GameService gameService;

    public ConsoleRunner(UserService userService, GameService gameService) {
        this.userService = userService;
        this.gameService = gameService;
    }

    @Override
    public void run(String... args) throws Exception {
        String input = sc.nextLine();
        loggingAndRegister(input);

    }

    private void loggingAndRegister(String input) {
        while (!input.equals("end")) {
            final String[] data = input.split("[|]");
            final String command = data[0];
            final String output = switch (command) {
                case REGISTER_USER -> userService.registerUser(data);
                case LOGIN_USER -> userService.loginUser(data);
                case LOGOUT -> userService.logoutUser();
                case ADD_GAME -> gameService.addGame(data);
                case EDIT_GAME -> gameService.editGame(data);
                default -> COMMAND_NOT_FOUND_MESSAGE;
            };
            System.out.println(output);
            input = sc.nextLine();
        }
    }
}

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
    private User user = null;

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
                case REGISTER_USER -> this.userService.registerUser(data);
                case LOGIN_USER -> this.userService.loginUser(data);
                case LOGOUT -> this.userService.logoutUser();
                case ADD_GAME -> this.user.getOnline() ? this.user.getAdministrator() ? this.gameService.addGame(data) : "User not online" :
                        this.user.addGame(this.gameService.findByTitle(data[1])) + " " + this.user.getFullName();
                case EDIT_GAME -> this.user.getOnline() && this.user.getAdministrator() ? this.gameService.editGame(data) : "User not online or not Administrator";
                case DELETE_GAME -> this.user.getOnline() && this.user.getAdministrator() ? this.gameService.deleteGameById(data[1]) : "User not online or not Administrator";
                default -> COMMAND_NOT_FOUND_MESSAGE;
            };
            if (output.startsWith("Successfully logged in ")) {
                final String userName = output.substring(22);
                this.user = this.userService.findByFullName(userName);
            }
            input = sc.nextLine();
        }
    }
}

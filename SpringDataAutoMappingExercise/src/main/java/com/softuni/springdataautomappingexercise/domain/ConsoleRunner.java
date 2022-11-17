package com.softuni.springdataautomappingexercise.domain;

import com.softuni.springdataautomappingexercise.domain.entities.Game;
import com.softuni.springdataautomappingexercise.domain.entities.User;
import com.softuni.springdataautomappingexercise.domain.services.GameService;
import com.softuni.springdataautomappingexercise.domain.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public ConsoleRunner(UserService userService, GameService gameService) {
        this.userService = userService;
        this.gameService = gameService;
    }

    @Override
    public void run(String... args) {
        String input = sc.nextLine();
        loggingAndRegister(input);

    }

    private void loggingAndRegister(String input) {
        while (!input.equals("end")) {
            final String[] data = input.split("[|]");
            final String command = data[0];
            try {
                final String output = switch (command) {
                    case REGISTER_USER -> this.userService.registerUser(data);
                    case LOGIN_USER -> this.userService.loginUser(data);
                    case LOGOUT -> this.userService.logoutUser();
                    case ADD_GAME -> this.user.getAdministrator() ?
                            this.gameService.addGame(data) : "User not Administrator%n" +
                            this.user.addGame(this.gameService.findByTitle(data[1])) + " "
                            + this.user.getFullName();
                    case EDIT_GAME -> this.user.getAdministrator()
                            ? this.gameService.editGame(data) : "User not Administrator%n";
                    case DELETE_GAME -> this.user.getAdministrator()
                            ? this.gameService.deleteGameById(data[1])
                            : "User not Administrator";
                    case ALL_GAMES -> printAllGames();
                    case DETAIL_GAME -> printDetailsForGame(data[1]);
                    case OWNED_GAMES -> printOwnedGames();
                    default -> COMMAND_NOT_FOUND_MESSAGE;
                };
                System.out.println(output);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            if (command.equals(LOGIN_USER)) {
                this.user = userService.findByEmail(data[1]);
            } else if (command.equals(LOGOUT)) {
                this.user = null;
            }

            input = sc.nextLine();

        }
    }

    private String printOwnedGames() {
        StringBuilder stringBuilder = new StringBuilder();
        this.user.getGames().forEach(game -> stringBuilder.append(game.getTitle()));
        return stringBuilder.toString();
    }

    private String printDetailsForGame(String title) {
        Game game = this.gameService.findByTitle(title);
        return String.format("Title: %s%n" +
                        "Price: %s%n" +
                        "Description: %s%n" +
                        "Release date:%s%n", game.getTitle(),
                game.getPrice(), game.getDescription(), game.getReleaseDate());
    }

    private String printAllGames() {
        StringBuilder games = new StringBuilder();
        this.gameService.findAll().forEach(game -> games.append(game.getTitle())
                .append(game.getPrice()).append(" ").append(System.lineSeparator()));
        return games.toString();
    }
}

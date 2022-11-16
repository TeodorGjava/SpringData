package com.softuni.springdataautomappingexercise.domain.services;

import com.softuni.springdataautomappingexercise.domain.entities.Game;
import com.softuni.springdataautomappingexercise.domain.repositories.GameRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Optional;

public class GameServiceImpl implements GameService {
    private final GameRepository gameRepository;

    public GameServiceImpl(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Override
    public String addGame(String[] data) {

        String[] date = data[7].replace("-", " ").split(" ");
        int year = Integer.parseInt(date[2]);
        int month = Integer.parseInt(date[1]);
        int day = Integer.parseInt(date[0]);
        if (validate(data)) {
            Game game = new Game(data[0], data[4],
                    data[5], new BigDecimal(data[3]),
                    new BigDecimal(data[2]), data[6],
                    LocalDate.of(year, month, day));
            this.gameRepository.save(game);
            return "Added " + data[1];
        }
        return "Game parameters are not valid!";
    }

    @Override
    public Game findById(int id) {
        return this.gameRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public String editGame(String[] data) {
        Optional<Game> game = gameRepository.findById(Long.parseLong(data[1]));
        if (game.isPresent()) {
            for (int i = 2; i < data.length; i++) {
                String[] updateValues = data[i].split("=");
                String field = updateValues[0];
                String newValue = updateValues[1];

            }
        }
        return null;
    }

    private boolean validate(String[] data) {
        String title = data[1];
        double price = Double.parseDouble(data[2]);
        int size = Integer.parseInt((data[3]));
        String trailer = data[4];
        String thumbnailURL = data[5];
        String description = data[6];
        final boolean isTitleValid = title.length() >= 3 && title.length() <= 100;
        final boolean pricePositive = price > 0;
        final boolean sizePositive = size > 0;
        final boolean validTrailer = trailer.matches("^(https?://?www\\.youtube\\.com/watch\\?v=)[a-zA-Z]{11}");
        final boolean thumbnailValid = thumbnailURL.startsWith("http://") && thumbnailURL.startsWith("https://");
        final boolean descriptionValid = description.length() > 20;
        return isTitleValid && pricePositive
                && sizePositive && validTrailer && thumbnailValid
                && descriptionValid;
    }
}

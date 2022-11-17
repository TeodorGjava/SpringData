package com.softuni.springdataautomappingexercise.domain.services;

import com.softuni.springdataautomappingexercise.domain.entities.Game;
import com.softuni.springdataautomappingexercise.domain.entities.dtos.GameDTO;
import com.softuni.springdataautomappingexercise.domain.repositories.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class GameServiceImpl implements GameService {
    private final GameRepository gameRepository;

    @Autowired
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
            GameDTO game = new GameDTO(data[0], data[4],
                    data[5], new BigDecimal(data[3]),
                    new BigDecimal(data[2]), data[6],
                    LocalDate.of(year, month, day));
            this.gameRepository.save(game.toGame());
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
                switch (field) {
                    case "price" -> game.get().setPrice(new BigDecimal(newValue));
                    case "title" -> game.get().setTitle(newValue);
                    case "size" -> game.get().setSize(new BigDecimal(newValue));
                    case "trailer" -> game.get().setTrailerId(newValue);
                    case "description" -> game.get().setDescription(newValue);
                }
            }
            return "Edited " + game.get().getTitle();
        } else {
            return "Invalid parameter to edit";
        }
    }

    public void addGame(String title) {
        Game game = gameRepository.findGameByTitle(title);
        this.gameRepository.save(game);
    }

    @Override
    public String deleteGameById(String id) {

        long parsedId = Long.parseLong(id);
        final Optional<Game> game = this.gameRepository.findById(parsedId);
        if (game.isPresent()) {
            this.gameRepository.delete(game.get());
            return "Deleted " + game.get().getTitle();
        }
        return "No such game to delete";
    }

    @Override
    public Game findByTitle(String title) {
        return this.gameRepository.findGameByTitle(title);
    }

    private boolean validate(String[] data) {
        String title = data[1];
        double price = Double.parseDouble(data[2]);
        int size = Integer.parseInt((data[3]));
        String trailer = data[4];
        String thumbnailURL = data[5];
        String description = data[6];
        final boolean isTitleValid = title.length() >= 3 && title.length() <= 100 && Character.isUpperCase(title.charAt(0));
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

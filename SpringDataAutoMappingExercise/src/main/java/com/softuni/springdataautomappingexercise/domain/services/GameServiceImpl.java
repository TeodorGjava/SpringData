package com.softuni.springdataautomappingexercise.domain.services;

import com.softuni.springdataautomappingexercise.domain.entities.Game;
import com.softuni.springdataautomappingexercise.domain.entities.dtos.GameDTO;
import com.softuni.springdataautomappingexercise.domain.repositories.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
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

        String[] dateFormat = data[7].replace("-", " ").split(" ");
        int year = Integer.parseInt(dateFormat[2]);
        int month = Integer.parseInt(dateFormat[1]);
        int day = Integer.parseInt(dateFormat[0]);
        String title = data[1];
        BigDecimal price = new BigDecimal(data[2]);
        BigDecimal size = new BigDecimal(data[3]);
        String trailerId = data[4];
        String imageUrl = data[5];
        String description = data[6];

        GameDTO game = new GameDTO(title, trailerId, imageUrl, size, price, description,
                LocalDate.of(year, month, day));
        Game entity = game.toGame();
        this.gameRepository.save(entity);
        return "Added " + data[1];
    }

    @Override
    public Game findById(Long id) {
        return this.gameRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public void editGameById(BigDecimal price, BigDecimal size, Long id) {
        this.gameRepository.editGameById(price, size, id);
    }

    @Override
    public String editGame(String[] data) {
        Optional<Game> game = gameRepository.findById(Long.parseLong(data[1]));
        if (game.isPresent()) {

            String[] priceValue = data[2].split("=");
            String[] sizeValue = data[3].split("=");

            String price = priceValue[1];
            String size = sizeValue[1];


            this.gameRepository.editGameById(new BigDecimal(price),
                    new BigDecimal(size),
                    Long.parseLong(data[1]));
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

    @Override
    public List<Game> findAll() {
        return this.gameRepository.findAll();
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
        final boolean thumbnailValid = thumbnailURL.startsWith("http://") || thumbnailURL.startsWith("https://");
        final boolean descriptionValid = description.length() > 20;
        return isTitleValid && pricePositive
                && sizePositive && validTrailer && thumbnailValid
                && descriptionValid;
    }
}

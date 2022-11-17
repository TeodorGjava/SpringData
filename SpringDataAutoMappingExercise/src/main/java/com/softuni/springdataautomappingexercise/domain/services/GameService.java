package com.softuni.springdataautomappingexercise.domain.services;

import com.softuni.springdataautomappingexercise.domain.entities.Game;

import java.math.BigDecimal;
import java.util.List;

public interface GameService {
    String addGame(String[] data);

    Game findById(Long id);

    void editGameById(BigDecimal price, BigDecimal size, Long id);

    String editGame(String[] data);

    String deleteGameById(String id);

    Game findByTitle(String title);

    List<Game> findAll();
}

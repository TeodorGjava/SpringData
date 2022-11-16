package com.softuni.springdataautomappingexercise.domain.services;

import com.softuni.springdataautomappingexercise.domain.entities.Game;

public interface GameService {
    String addGame(String[] data);

    Game findById(int id);
    String editGame(String[] data);
}

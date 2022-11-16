package com.softuni.springdataautomappingexercise.domain.services;

import com.softuni.springdataautomappingexercise.domain.entities.Game;
import org.springframework.data.jpa.repository.Modifying;

import javax.transaction.Transactional;

public interface GameService {
    String addGame(String[] data);

    Game findById(int id);
    String editGame(String[] data);
    String deleteGameById(String id);
}

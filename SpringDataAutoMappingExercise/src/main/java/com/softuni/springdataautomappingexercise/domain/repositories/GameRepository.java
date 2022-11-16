package com.softuni.springdataautomappingexercise.domain.repositories;

import com.softuni.springdataautomappingexercise.domain.entities.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
    Optional<Game> findById(long id);

    @Transactional
    @Modifying
    String deleteGameById(Long id);
}

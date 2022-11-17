package com.softuni.springdataautomappingexercise.domain.repositories;

import com.softuni.springdataautomappingexercise.domain.entities.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
    Optional<Game> findById(long id);

    @Transactional
    @Modifying
    String deleteGameById(Long id);

    Game findGameByTitle(String title);

    @Query("update Game g set g.price = :price, g.size = :size where g.id = :id")
    @Transactional
    @Modifying
    void editGameById(BigDecimal price, BigDecimal size,Long id);

    List<Game> findAll();
}

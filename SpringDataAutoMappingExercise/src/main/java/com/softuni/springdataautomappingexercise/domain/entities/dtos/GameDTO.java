package com.softuni.springdataautomappingexercise.domain.entities.dtos;

import com.softuni.springdataautomappingexercise.domain.entities.Game;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.time.LocalDate;

public class GameDTO {

    private String title;

    private String trailerId;

    private String imageURL;

    private BigDecimal size;

    private BigDecimal price;

    private String description;

    private LocalDate releaseDate;

    public Game toGame() {
        return new Game(title, trailerId, imageURL, size, price, description, releaseDate
        );
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTrailerId() {
        return trailerId;
    }

    public void setTrailerId(String trailerId) {
        this.trailerId = trailerId;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public BigDecimal getSize() {
        return size;
    }

    public void setSize(BigDecimal size) {
        this.size = size;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public GameDTO(String title, String trailerId, String imageURL, BigDecimal size, BigDecimal price, String description, LocalDate releaseDate) {
        this.title = title;
        this.trailerId = trailerId;
        this.imageURL = imageURL;
        this.size = size;
        this.price = price;
        this.description = description;
        this.releaseDate = releaseDate;
    }
}

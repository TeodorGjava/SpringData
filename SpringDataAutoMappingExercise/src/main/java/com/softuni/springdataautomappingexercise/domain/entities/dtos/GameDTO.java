package com.softuni.springdataautomappingexercise.domain.entities.dtos;

import com.softuni.springdataautomappingexercise.domain.entities.Game;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.sql.Struct;
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
        if (title.length() >= 3 && title.length() <= 100 && Character.isUpperCase(title.charAt(0))) {
            this.title = title;
        }
        throw new IllegalArgumentException("Invalid game title");
    }

    public String getTrailerId() {
        return trailerId;
    }

    public void setTrailerId(String trailerId) {
        boolean matches = trailerId.matches("^(https?://?www\\.youtube\\.com/watch\\?v=)[a-zA-Z]{11}");
        if (matches) {
            this.trailerId = trailerId;
        }
        throw new IllegalArgumentException("Invalid trailerId");
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        if (imageURL.startsWith("http://") && imageURL.startsWith("https://")) {
            this.imageURL = imageURL;
        }
        throw new IllegalArgumentException("Image URL invalid");
    }

    public BigDecimal getSize() {
        return size;
    }

    public void setSize(BigDecimal size) {
        if (size.compareTo(new BigDecimal(0)) < 1) {
            this.size = size;
        }
        throw new IllegalArgumentException("Invalid game size");
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        if (price.compareTo(new BigDecimal(0)) < 1) {
            this.price = price;
        }
        throw new IllegalArgumentException("Invalid game price");
    }

    public String getDescription() {

        return description;
    }

    public void setDescription(String description) {
        if (description.length() < 20) {
            throw new IllegalArgumentException("Description must be at least 20 symbols long.");
        }
        this.description = description;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public GameDTO(String title, String trailerId, String imageURL, BigDecimal size, BigDecimal price, String description, LocalDate releaseDate) {
        setTitle(title);
        setTrailerId(trailerId);
        setImageURL(imageURL);
        setSize(size);
        setPrice(price);
        setDescription(description);
        setReleaseDate(releaseDate);
    }
}

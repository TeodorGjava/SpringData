package com.softuni.jsonexercises;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class JsonExercisesApplication {

    public static void main(String[] args) {
        SpringApplication.run(JsonExercisesApplication.class, args);

    }

}

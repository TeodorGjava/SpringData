package com.softuni.usersystem;

import com.softuni.usersystem.domain.entities.User;
import com.softuni.usersystem.domain.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class ConsoleRunner implements CommandLineRunner {
    private UserService service;

    @Autowired
    public ConsoleRunner(UserService service) {

        this.service = service;
    }

    @Override
    public void run(String... args) throws Exception {
        if (service.getCount() == 0) {
            addUsers();
        }
        System.out.println("Number of records: " + service.getCount());
        getByEmailProvider();
        getUsersInAgeRange().forEach(System.out::println);
    }

    private List<String> getUsersInAgeRange() {
        return service.getUserNamesAndAgeByAgeRange(20,56);
    }

    private void getByEmailProvider() {
        service.getAllUsersByEmailProvider("mail.bg")
                .forEach(user -> System.out.println(user.getFullNameAndAge()));
    }

    private void addUsers() {
        for (int i = 1; i <= 1000; i++) {
            this.service.save(User.builder()
                    .userName("username" + i)
                    .password("pasSword%" + i)
                    .email("email" + i + "x@mail.bg")
                    .age(i % 120 + 1)
                    .firstName("First" + i)
                    .lastName("Last" + i)
                    .registeredOn(new Date())
                    .lastTimeLoggedIn(new Date())
                    .isDeleted(false)
                    .build());
        }
    }

}

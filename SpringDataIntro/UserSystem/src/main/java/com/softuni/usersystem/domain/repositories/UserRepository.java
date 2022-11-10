package com.softuni.usersystem.domain.repositories;

import com.softuni.usersystem.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAllByEmailEndingWith(String end);

    List<User> findAllByLastTimeLoggedInBefore(Date date);

    List<User> findAllByAgeBetweenOrderByAge(int low, int high);
}

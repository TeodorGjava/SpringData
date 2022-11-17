package com.softuni.springdataautomappingexercise.domain.repositories;

import com.softuni.springdataautomappingexercise.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findFirstByEmail(String email);

    Optional<User> findByEmail(String email);

    boolean existsUserByEmail(String email);

    Optional<User> findUserByFullName(String fullName);

    @Transactional
    @Modifying
    @Query("update User u set u.isOnline = true where u.email = :email")
    void setUserOnlineFindByEmail(String email);




}

package com.softuni.jsonexercises.domain.repositories;

import com.softuni.jsonexercises.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    @Query(value = "select * from `json`.users order by RAND() limit 1", nativeQuery = true)
    Optional<User> getRandomUser();
  //  @Query(value = "select * from `json`.users u, products p" +
   //         " where p.buyer_id is not null order by u.last_name,u.first_name", nativeQuery = true )
    Optional<List<User>> findAllBySellingProductsBuyerIsNotNullOrderBySellingProductsBuyerFirstName();
}

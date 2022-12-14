package com.softuni.xmlprocessing.domain.repositories;


import com.softuni.xmlprocessing.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "select * from `product-shop`.users order by RAND() limit 1", nativeQuery = true)
    Optional<User> getRandomUser();

    Optional<List<User>> findAllBySellingProductsBuyerIsNotNullOrderBySellingProductsBuyerFirstName();

    Optional<List<User>> findAllByOrderByLastNameAscFirstNameAsc();
}

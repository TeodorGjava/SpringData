package com.example.springinitdemo.repositories;

import com.example.springinitdemo.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}

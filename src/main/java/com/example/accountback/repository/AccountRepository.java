package com.example.accountback.repository;


import com.example.accountback.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

import static javax.persistence.LockModeType.PESSIMISTIC_READ;

public interface AccountRepository extends JpaRepository<Account, Long> {

    @Lock(PESSIMISTIC_READ)
    @Query("SELECT a FROM Account a WHERE a.no = ?1")
    Optional<Account> findByNoLocked(Long id);
}

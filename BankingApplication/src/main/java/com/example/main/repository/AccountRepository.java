package com.example.main.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.main.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

	public Optional<Account> findByUsername(String username);
}

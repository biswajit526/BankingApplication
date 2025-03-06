package com.example.main.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.main.entity.Transection;

public interface TransectionRepo extends JpaRepository<Transection, Long> {
	
	public List<Transection> findByAccountId(Long id);

}

package com.example.medconnect.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.medconnect.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{
	Optional<User> findByEmail(String email);
	
}

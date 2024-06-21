package com.sawan.exam.repository;

import java.util.Optional;

//import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.sawan.exam.models.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
	
	Optional<User> findByUsername(String username);
	Optional<User> findByEmailOrUsername(String email, String username);
	Boolean existsByEmail(String eamil);
	Boolean existsByUsername(String username);
	
}
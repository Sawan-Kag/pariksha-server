package com.sawan.exam.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sawan.exam.models.ERole;
import com.sawan.exam.models.Role;


public interface RoleRepository extends MongoRepository<Role, String> {
      
	Optional<Role> findById(String id);
	Optional<Role> findByName(ERole name);;
}

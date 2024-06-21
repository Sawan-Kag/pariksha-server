package com.sawan.exam.repository;


import org.springframework.data.mongodb.repository.MongoRepository;

import com.sawan.exam.models.Category;

public interface CategoryRepository extends MongoRepository<Category,String>{
}

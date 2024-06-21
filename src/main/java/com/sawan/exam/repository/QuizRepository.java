package com.sawan.exam.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sawan.exam.models.Category;
import com.sawan.exam.models.Quiz;

public interface QuizRepository extends MongoRepository<Quiz, String> {
    public List<Quiz> findBycategory(Category category);

    public List<Quiz> findByActive(Boolean b);

    public List<Quiz> findByCategoryAndActive(Category c, Boolean b);
    
}

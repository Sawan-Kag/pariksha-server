package com.sawan.exam.repository;

import java.util.Set;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sawan.exam.models.Question;
import com.sawan.exam.models.Quiz;

public interface QuestionRepository extends MongoRepository<Question, String> {
     public Set<Question> findByQuiz(Quiz quiz);
}

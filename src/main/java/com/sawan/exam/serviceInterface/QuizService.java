package com.sawan.exam.serviceInterface;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.sawan.exam.models.Category;
import com.sawan.exam.models.Quiz;

public interface QuizService {

    public Quiz addQuiz(Quiz quiz);

    public Quiz updateQuiz(Quiz quiz);

    public Set<Quiz> getQuizzes();

    public Optional<Quiz> getQuiz(String quizId);

    public void deleteQuiz(String quizId);


    public List<Quiz> getQuizzesOfCategory(Category category);

    public List<Quiz> getActiveQuizzes();

    public List<Quiz> getActiveQuizzesOfCategory(Category c);
}

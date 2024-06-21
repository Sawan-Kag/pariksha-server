package com.sawan.exam.serviceInterface;

import java.util.Optional;
import java.util.Set;

import com.sawan.exam.models.Question;
import com.sawan.exam.models.Quiz;

public interface QuestionService {

    public Question addQuestion(Question question);

    public Question updateQuestion(Question question);

    public Set<Question> getQuestions();

    public Optional<Question> getQuestion(String questionId);

    public Set<Question> getQuestionsOfQuiz(Quiz quiz);

    public void deleteQuestion(String quesId);

    public Optional<Question> get(String questionsId);

}

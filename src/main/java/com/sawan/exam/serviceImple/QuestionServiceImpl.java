package com.sawan.exam.serviceImple;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sawan.exam.models.Question;
import com.sawan.exam.models.Quiz;
import com.sawan.exam.repository.QuestionRepository;
import com.sawan.exam.serviceInterface.QuestionService;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Override
    public Question addQuestion(Question question) {
        return this.questionRepository.insert(question);
    }

    @Override
    public Question updateQuestion(Question question) {
        return this.questionRepository.insert(question);
    }

    @Override
    public Set<Question> getQuestions() {
        return new HashSet<>(this.questionRepository.findAll());
    }

    @Override
    public Optional<Question> getQuestion(String questionId) {
        return this.questionRepository.findById(questionId);
    }

    @Override
    public Set<Question> getQuestionsOfQuiz(Quiz quiz) {
        return this.questionRepository.findByQuiz(quiz);
    }

    @Override
    public void deleteQuestion(String quesId) {
        Question question = new Question();
        question.setQuesId(quesId);
        this.questionRepository.delete(question);
    }

    @Override
    public Optional<Question> get(String questionsId) {
       return this.questionRepository.findById(questionsId);
    }
}

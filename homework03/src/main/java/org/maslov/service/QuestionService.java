package org.maslov.service;

import org.maslov.model.Question;
import org.maslov.repository.QuestionRepositoryImpl;

import java.util.List;

public class QuestionService {

    private QuestionRepositoryImpl questionRepository;

    public QuestionService(QuestionRepositoryImpl questionRepository) {
        this.questionRepository = questionRepository;
    }

    public List<Question> findAll() {
        return questionRepository.findAll();
    }
}

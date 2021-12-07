package org.maslov.service;

import org.maslov.model.Question;
import org.maslov.repository.QuestionRepositoryImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {

    private QuestionRepositoryImpl questionRepository;

    public QuestionService(QuestionRepositoryImpl questionRepository) {
        this.questionRepository = questionRepository;
    }

    public List<Question> findAll() {
        return questionRepository.findAll();
    }

    public List<Question> findAllByLocaleCode(String localeCode) {
        return questionRepository.findAllByLocaleCode(localeCode);
    }
}

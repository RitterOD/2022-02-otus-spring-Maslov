package org.maslov.service;

import org.maslov.model.Question;
import org.maslov.repository.QuestionRepository;

import java.util.List;

public class QuestioningService {

    private QuestionRepository questionRepository;

    public QuestioningService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public List<Question> getQuestions() {
        System.out.println("Hello from getQuestions");
        return null;
    }
}

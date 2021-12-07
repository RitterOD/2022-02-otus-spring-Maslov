package org.maslov.repository;

import org.maslov.model.Question;

import java.util.List;

public interface QuestionRepository {
    List<Question> findAll();

    List<Question> findAllByLocaleCode(String localeCode);
}

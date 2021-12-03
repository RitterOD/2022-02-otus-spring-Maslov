package org.maslov.repository;

import org.maslov.model.Question;
import org.maslov.util.CsvLoader;

import java.util.List;


public class QuestionRepositoryImpl implements QuestionRepository{

    private final CsvLoader loader;

    public QuestionRepositoryImpl(CsvLoader loader) {
        this.loader = loader;
    }

    public List<Question> findAll() {
        return loader.findAll();
    }





}

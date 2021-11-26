package org.maslov.repository;

import com.opencsv.CSVReader;
import org.maslov.model.Question;
import org.maslov.util.CsvLoader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
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

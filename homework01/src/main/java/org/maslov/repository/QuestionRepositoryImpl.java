package org.maslov.repository;

import com.opencsv.CSVReader;
import org.maslov.model.Question;
import org.springframework.core.io.ClassPathResource;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class QuestionRepositoryImpl implements QuestionRepository{

    public List<Question> findAll() {
        return null;
    }


    private static List<String[]> readAll(Reader reader) throws Exception {
        CSVReader csvReader = new CSVReader(reader);
        List<String[]> list;
        list = csvReader.readAll();
        reader.close();
        csvReader.close();
        return list;
    }

    public static void main(String[] args) throws Exception {
            InputStream resource = new ClassPathResource(
                    "questions.csv").getInputStream();
            try ( BufferedReader reader = new BufferedReader(
                    new InputStreamReader(resource)) ) {
                List<String[]> rv = readAll(reader);
                for(String[] line: rv) {
                    for(String val: line) {
                        System.out.println(val + " ");
                    }
                    System.out.println();
                }
            }
    }
}

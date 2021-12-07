package org.maslov.util;

import com.opencsv.CSVReader;
import org.maslov.configuration.AppProperties;
import org.maslov.model.Question;
import org.maslov.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class CsvLoader implements QuestionRepository {

    private final String classPathResource;
    private final AppProperties appProperties;

    public CsvLoader(AppProperties appProperties) {
        this.appProperties = appProperties;
        this.classPathResource = appProperties.getSource();
    }

    private List<String[]> readAll() throws IOException {


        InputStream resource = new ClassPathResource(classPathResource).getInputStream();
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(resource))) {
                CSVReader csvReader = new CSVReader(reader);
                List<String[]> list;
                list = csvReader.readAll();
                reader.close();
                csvReader.close();
                return list;
            } catch (IOException e) {
                throw e;
            }

    }

    public static Question mapCsvRepresentationToQuestion(String[] csvRep) throws IOException {
        Question rv = new Question();
        try {
            rv.setQuestionText(csvRep[1]);

            int rightAnswerInd = 0;

            rightAnswerInd = Integer.parseInt(csvRep[csvRep.length - 1].trim());
            rv.setRightAnswerInd(rightAnswerInd);

            List<String> answers = IntStream.range(2, csvRep.length - 1).mapToObj(i -> csvRep[i].trim())
                    .collect(Collectors.toList());
            if (answers.size() <= rightAnswerInd || rightAnswerInd < 0) {
                throw new IOException("Wrong value of right answer field inside csv file");
            }
            rv.setAnswers(answers);
        } catch (NumberFormatException e) {
            throw new IOException("Wrong right answer field format inside csv file", e);
        } catch (IndexOutOfBoundsException e) {
            throw new IOException("During loading csv file", e);
        }
        return rv;
    }

    @Override
    public List<Question> findAll() {
        List<Question> rv = new ArrayList<>();
        try {
            List<String[]> readed = readAll();
            for(String[] rep: readed) {
                rv.add(mapCsvRepresentationToQuestion(rep));
            }
            return rv;
        } catch (IOException e) {
            return rv;
        }


    }
}

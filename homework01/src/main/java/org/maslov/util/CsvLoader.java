package org.maslov.util;

import com.opencsv.CSVReader;
import org.maslov.model.Question;
import org.maslov.repository.QuestionRepository;
import org.springframework.core.io.ClassPathResource;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CsvLoader implements QuestionRepository {

    private String classPathResource;

    public CsvLoader(String classPathResource) {
        this.classPathResource = classPathResource;
    }

    private List<String[]> readAll() throws IOException {
        InputStream resource = null;

            resource = new ClassPathResource(classPathResource).getInputStream();
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(resource))) {
                CSVReader csvReader = new CSVReader(reader);
                List<String[]> list;
                list = csvReader.readAll();
                reader.close();
                csvReader.close();
//            for (String[] line : list) {
//                for (String val : line) {
//                    System.out.println(val + " ");
//                }
//                System.out.println();
//            }
                return list;
            } catch (IOException e) {
                throw e;
            }

    }

    private static Question mapCsvRepresentationToQuestion(String[] csvRep) throws IOException
    {
        Question rv = new Question();
        rv.setQuestionText(csvRep[1]);
        rv.setRightAnswerInd(Integer.parseInt(csvRep[csvRep.length - 1].trim()));
        List<String> answers = IntStream.range(2, csvRep.length - 1).mapToObj(i -> csvRep[i].trim())
                .collect(Collectors.toList());
        rv.setAnswers(answers);
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

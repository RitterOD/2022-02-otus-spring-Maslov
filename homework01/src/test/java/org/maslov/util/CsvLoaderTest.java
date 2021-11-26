package org.maslov.util;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CsvLoaderTest {

    CsvLoader loader;

    @BeforeAll
    void initCsvLoader(){
        // FIXME after start using spring boot test
        loader = new CsvLoader("./src/main/resources/test_question.csv");
    }


    @Test
    void checkNullPointer() {
        assertThrows(NullPointerException.class, () -> {loader.mapCsvRepresentationToQuestion(null);});
    }

    @Test
    void wrongRightAnswerFieldFormat(){
        String[] testData = {
                "1",
                " What is the year of Russian empire foundation?",
                " 1720",
                " 1721",
                " 1722",
                " 1723",
                " la",
        };
        Throwable e =assertThrows(IOException.class, () -> {loader.mapCsvRepresentationToQuestion(testData);});
        assertEquals("Wrong right answer field format inside csv file", e.getMessage());

    }


    @Test
    void wrongRightAnswerFieldValue(){
        String[] testData = {
                "1",
                " What is the year of Russian empire foundation?",
                " 1720",
                " 1721",
                " 1722",
                " 1723",
                " 1000",
        };
        Throwable e =assertThrows(IOException.class, () -> {loader.mapCsvRepresentationToQuestion(testData);});
        assertEquals("Wrong value of right answer field inside csv file", e.getMessage());
    }
}
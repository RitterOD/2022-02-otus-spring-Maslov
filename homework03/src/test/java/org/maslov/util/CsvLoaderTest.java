package org.maslov.util;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.maslov.configuration.AppProperties;
import org.maslov.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class CsvLoaderTest {

    @Autowired
    CsvLoader loader;

    @Autowired
    AppProperties appProperties;



    @Test
    public void checkLoadAll() {
        List<Question> lst = loader.findAll();
        Assert.assertEquals(lst.size(), 10);
    }


    @Test
    public void checkLoadRu() {
        List<Question> lst = loader.findAllByLocaleCode(AppConstants.CODE_LOCALE_RU);
        Assert.assertEquals(lst.size(), 5);
    }

    @Test
    public void checkLoadEn() {
        List<Question> lst = loader.findAllByLocaleCode(AppConstants.CODE_LOCALE_EN);
        Assert.assertEquals(lst.size(), 5);
    }

}
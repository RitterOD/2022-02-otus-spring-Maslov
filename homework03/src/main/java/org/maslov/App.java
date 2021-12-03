package org.maslov;

import org.maslov.model.QuestioningResult;
import org.maslov.service.QuestioningPerformService;
import org.maslov.util.QuestioningResultRepresentation;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


@SpringBootApplication
public class App {
    public static void main(String[] args) {
        ApplicationContext context =SpringApplication.run(App.class, args);
        QuestioningPerformService service = context.getBean(QuestioningPerformService.class);
        QuestioningResult rv = service.performQuestioning();
        QuestioningResultRepresentation.printToSystemOut(rv);

    }
}

package org.maslov;

import org.maslov.model.QuestioningResult;
import org.maslov.service.QuestioningPerformService;
import org.maslov.util.QuestioningResultRepresentation;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml");

        QuestioningPerformService service = context.getBean(QuestioningPerformService.class);
        QuestioningResult rv = service.performQuestioning();
        QuestioningResultRepresentation.printToSystemOut(rv);

    }
}

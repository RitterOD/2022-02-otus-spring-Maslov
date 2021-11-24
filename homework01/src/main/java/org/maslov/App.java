package org.maslov;

import org.maslov.service.QuestioningService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
    public static void main(String[] args) {
        System.out.println("Hello world !");
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml");
        QuestioningService service = context.getBean(QuestioningService.class);
        service.getQuestions();
    }
}

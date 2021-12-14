package org.maslov.service;

import org.maslov.model.Question;
import org.maslov.model.QuestioningResult;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Scanner;

@Service
public class QuestioningPerformService {

    private final QuestionService questionService;
    private final MessageSourceService messageSourceService;
    private final CurrentLocaleInMemoryHolder currentLocaleInMemoryHolder;
    private final ConsoleService consoleService;


    public QuestioningPerformService(MessageSourceService messageSourceService, QuestionService questionService,
                                     CurrentLocaleInMemoryHolder currentLocaleInMemoryHolder, ConsoleService consoleService) {
        this.messageSourceService = messageSourceService;
        this.questionService = questionService;
        this.currentLocaleInMemoryHolder = currentLocaleInMemoryHolder;
        this.consoleService = consoleService;
    }


    private QuestioningResult performQuestioningImpl() {
        List<Question> questions = questionService.findAllByLocaleCode(currentLocaleInMemoryHolder.getCodeCurrenLocale());
        QuestioningResult result = new QuestioningResult(questions.size());
        consoleService.printlnLine(messageSourceService.getMessage("greeting"));
        for(Question q: questions) {
            consoleService.printlnLine(q.getQuestionText() + "\n");
            int numAnswers = q.getAnswers().size();
            for (int ind = 0; ind < numAnswers; ind++ ) {
                System.out.println(ind + 1 + ". " + q.getAnswers().get(ind));
            }
            int userAns = consoleService.readInt();
            if (userAns == q.getRightAnswerInd() + 1) {
                result.incrementRightAnswersNumber();
            }
        }

        return result;
    }

    public void performQuestioning() {
        QuestioningResult questioningResult = performQuestioningImpl();
        consoleService.printlnLine(messageSourceService.getMessage("result",
                new Object[]{questioningResult.getRightAnswersNumber(),
                        questioningResult.getQuestionNumber()}));
    }

}

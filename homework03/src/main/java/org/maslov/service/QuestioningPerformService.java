package org.maslov.service;

import org.maslov.model.Question;
import org.maslov.model.QuestioningResult;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

import static org.maslov.util.QuestioningResultRepresentation.printToSystemOut;

@Service
public class QuestioningPerformService {

    private final QuestionService questionService;
    private final MessageSourceService messageSourceService;
    public final InputStream in;
    public final PrintStream out;



    public QuestioningPerformService(MessageSourceService messageSourceService, QuestionService questionService,
                                     @Qualifier("systemIN") InputStream in,
                                     @Qualifier("systemOut") PrintStream out) {
        this.messageSourceService = messageSourceService;
        this.questionService = questionService;
        this.in = in;
        this.out = out;
    }


    private QuestioningResult performQuestioningImpl() {
        List<Question> questions = questionService.findAllByLocaleCode(messageSourceService.getCodeCurrenLocale());
        QuestioningResult result = new QuestioningResult(questions.size());
        System.out.println(messageSourceService.getMessage("greeting"));
        Scanner sc = new Scanner(System.in);
        for(Question q: questions) {
            System.out.println(q.getQuestionText() + "\n");
            int numAnswers = q.getAnswers().size();
            for (int ind = 0; ind < numAnswers; ind++ ) {
                System.out.println(ind + 1 + ". " + q.getAnswers().get(ind));
            }
            int userAns = sc.nextInt();
            if (userAns == q.getRightAnswerInd() + 1) {
                result.incrementRightAnswersNumber();
            }
        }

        return result;
    }

    public void performQuestioning() {
        QuestioningResult questioningResult = performQuestioningImpl();
        System.out.println(messageSourceService.getMessage("result",
                new Object[]{questioningResult.getRightAnswersNumber(),
                        questioningResult.getQuestionNumber()}));
    }

}

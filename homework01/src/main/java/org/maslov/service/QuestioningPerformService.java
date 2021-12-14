package org.maslov.service;

import org.maslov.model.Question;
import org.maslov.model.QuestioningResult;

import java.util.List;
import java.util.Scanner;

public class QuestioningPerformService {

    private final QuestionService questionService;


    public QuestioningPerformService(QuestionService questionService) {
        this.questionService = questionService;
    }


    public QuestioningResult performQuestioning() {
        List<Question> questions = questionService.findAll();
        QuestioningResult result = new QuestioningResult(questions.size());
        System.out.println("Please answer the questions\n");
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

}

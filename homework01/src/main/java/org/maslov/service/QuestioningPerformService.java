package org.maslov.service;

import org.maslov.model.QuestioningResult;

public class QuestioningPerformService {

    private final QuestionService questionService;


    public QuestioningPerformService(QuestionService questionService) {
        this.questionService = questionService;
    }


    public QuestioningResult performQuestioning() {
        return null;
    }

    public void representQuestioningResult(QuestioningResult result) {
        System.out.println("Number of right answer:" + result);
    }
}

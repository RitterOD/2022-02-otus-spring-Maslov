package org.maslov.model;

public class QuestioningResult {
    private int questionNumber;
    private int rightAnswersNumber;

    public QuestioningResult(int questionNumber) {
        this.questionNumber = questionNumber;
    }

    void incrementRightAnswersNumber() {
        ++rightAnswersNumber;
    }
}

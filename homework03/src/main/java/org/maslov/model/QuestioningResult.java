package org.maslov.model;

public class QuestioningResult {
    private int questionNumber;
    private int rightAnswersNumber;

    public QuestioningResult(int questionNumber) {
        this.questionNumber = questionNumber;
    }

    public int getQuestionNumber() {
        return questionNumber;
    }

    public int getRightAnswersNumber() {
        return rightAnswersNumber;
    }

    public void incrementRightAnswersNumber() {
        ++rightAnswersNumber;
    }

    @Override
    public String toString() {
        return "Your question result: " +
                rightAnswersNumber + " right answers from " + questionNumber
                + " questions";
    }
}

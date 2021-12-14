package org.maslov.model;

public class QuestioningResult {
    private Integer questionNumber;
    private Integer rightAnswersNumber;

    public QuestioningResult(int questionNumber) {
        this.questionNumber = questionNumber;
        this.rightAnswersNumber = 0;
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

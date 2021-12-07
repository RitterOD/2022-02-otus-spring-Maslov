package org.maslov.model;

import java.util.List;

public class Question {

    private String questionText;

    private String localeCode;

    private List<String> answers;

    private int rightAnswerInd;


    public Question(String questionText, List<String> answers, int rightAnswerInd) {
        this.questionText = questionText;
        this.answers = answers;
        this.rightAnswerInd = rightAnswerInd;
    }

    public Question() {
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }

    public int getRightAnswerInd() {
        return rightAnswerInd;
    }

    public void setRightAnswerInd(int rightAnswerInd) {
        this.rightAnswerInd = rightAnswerInd;
    }

    public String getLocaleCode() { return localeCode; }

    public void setLocaleCode(String localeCode) { this.localeCode = localeCode; }
}

package com.bionic.wrappers;

import java.util.Map;

/**
 * Created by c2411 on 27.01.2016.
 */
public class OpenQuestionWrapper {

    private String testName;
    private long questionId;
    private String question;
    private int numberOfUncheckedAnswers;

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(long questionId) {
        this.questionId = questionId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getNumberOfUncheckedAnswers() {
        return numberOfUncheckedAnswers;
    }

    public void setNumberOfUncheckedAnswers(int numberOfUncheckedAnswers) {
        this.numberOfUncheckedAnswers = numberOfUncheckedAnswers;
    }
}

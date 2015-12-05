package com.bionic.DTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rondo104 on 25.11.2015.
 */
public class UserAnswerDTO {

    private String answerText;
    private long questionId;
    private long answerId;


    public UserAnswerDTO() {
    }

    public UserAnswerDTO(String answerText, long questionId, long answerId) {
        this.answerText = answerText;
        this.questionId = questionId;
        this.answerId = answerId;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(long questionId) {
        this.questionId = questionId;
    }

    public long getAnswerId() {
        return answerId;
    }

    public void setAnswerId(long answerId) {
        this.answerId = answerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserAnswerDTO)) return false;

        UserAnswerDTO that = (UserAnswerDTO) o;

        if (questionId != that.questionId) return false;
        return answerId == that.answerId;

    }

    @Override
    public int hashCode() {
        int result = (int) (questionId ^ (questionId >>> 32));
        result = 31 * result + (int) (answerId ^ (answerId >>> 32));
        return result;
    }
}

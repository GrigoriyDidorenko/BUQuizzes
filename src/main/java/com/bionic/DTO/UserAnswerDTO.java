package com.bionic.DTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rondo104 on 25.11.2015.
 */
public class UserAnswerDTO {

    private long id;
    private String answerText;
    private long questionId;
    private List<String> answerId;
    private long resultId;
    private int counter;


    public UserAnswerDTO() {
    }

    public UserAnswerDTO(String answerText, long questionId, List<String> answerId) {
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

    public List<String> getAnswerId() {
        return answerId;
    }

    public void setAnswerId(List<String> answerId) {
        this.answerId = answerId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getResultId() {
        return resultId;
    }

    public void setResultId(long resultId) {
        this.resultId = resultId;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserAnswerDTO)) return false;

        UserAnswerDTO that = (UserAnswerDTO) o;

        if (questionId != that.questionId) return false;
        if (answerText != null ? !answerText.equals(that.answerText) : that.answerText != null) return false;
        return !(answerId != null ? !answerId.equals(that.answerId) : that.answerId != null);

    }

    @Override
    public int hashCode() {
        int result = answerText != null ? answerText.hashCode() : 0;
        result = 31 * result + (int) (questionId ^ (questionId >>> 32));
        result = 31 * result + (answerId != null ? answerId.hashCode() : 0);
        return result;
    }
}

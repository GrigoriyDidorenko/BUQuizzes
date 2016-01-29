package com.bionic.DTO;

import java.util.List;

/**
 * Created by rondo104 on 25.11.2015.
 */
public class UserAnswerDTO {

    private long id;
    private String answerText;
    private long questionId;
    private List<String> answersId;
    private long resultId;


    private int counter;
    private int mark;


    public UserAnswerDTO() {
    }

    public UserAnswerDTO(String answerText, long questionId, List<String> answersId) {
        this.answerText = answerText;
        this.questionId = questionId;
        this.answersId = answersId;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
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

    public List<String> getAnswersId() {
        return answersId;
    }

    public void setAnswersId(List<String> answersId) {
        this.answersId = answersId;
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
        return !(answersId != null ? !answersId.equals(that.answersId) : that.answersId != null);

    }

    @Override
    public int hashCode() {
        int result = answerText != null ? answerText.hashCode() : 0;
        result = 31 * result + (int) (questionId ^ (questionId >>> 32));
        result = 31 * result + (answersId != null ? answersId.hashCode() : 0);
        return result;
    }
}

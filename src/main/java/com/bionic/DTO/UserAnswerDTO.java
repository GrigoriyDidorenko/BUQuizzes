package com.bionic.DTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rondo104 on 19.11.2015.
 */
public class UserAnswerDTO {

    private String answerText;
    private long questionId;


    public List<UserAnswerDTO> userAnswerDTOs = new ArrayList<>();

    public List<UserAnswerDTO> addAnswerDTO(UserAnswerDTO userAnswerDTO) {
        userAnswerDTOs.add(userAnswerDTO);
        return userAnswerDTOs;
    }


    public UserAnswerDTO() {
    }

    public UserAnswerDTO(String answerText, long questionId) {
        this.answerText = answerText;
        this.questionId = questionId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserAnswerDTO)) return false;

        UserAnswerDTO that = (UserAnswerDTO) o;

        if (questionId != that.questionId) return false;
        return !(answerText != null ? !answerText.equals(that.answerText) : that.answerText != null);

    }

    @Override
    public int hashCode() {
        int result = answerText != null ? answerText.hashCode() : 0;
        result = 31 * result + (int) (questionId ^ (questionId >>> 32));
        return result;
    }

}

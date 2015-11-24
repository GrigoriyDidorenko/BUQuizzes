package com.bionic.DTO;

/**
 * package: com.bionic.DTO
 * project: TestDTO
 * class:
 *
 * @author: Grigoriy Didorenko
 * @date: 02.11.2015
 */
public class AnswerDTO {

    private String answerText;

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public AnswerDTO() {
    }

    public AnswerDTO(String answerText) {
        this.answerText = answerText;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AnswerDTO answerDTO = (AnswerDTO) o;

        return !(answerText != null ? !answerText.equals(answerDTO.answerText) : answerDTO.answerText != null);

    }

    @Override
    public int hashCode() {
        return answerText != null ? answerText.hashCode() : 0;
    }
}

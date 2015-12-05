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

    private long id;
    private String answerText;
    private int mark;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public AnswerDTO() {
    }

    public AnswerDTO(long id, String answerText) {
        this.id = id;
        this.answerText = answerText;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AnswerDTO answerDTO = (AnswerDTO) o;

        if (id != answerDTO.id) return false;
        if (mark != answerDTO.mark) return false;
        return answerText.equals(answerDTO.answerText);

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + answerText.hashCode();
        result = 31 * result + mark;
        return result;
    }
}

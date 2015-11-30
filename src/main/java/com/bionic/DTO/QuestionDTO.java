package com.bionic.DTO;

import java.util.Set;

/**
 * package: com.bionic.DTO
 * project: TestDTO
 * class:
 *
 * @author: Grigoriy Didorenko
 * @date: 02.11.2015
 */
public class QuestionDTO {

    private long id;
    private String question;
    private Set<AnswerDTO> answers;

    public QuestionDTO() {
    }

    public QuestionDTO(long id, String question, Set<AnswerDTO> answers) {
        this.id = id;
        this.question = question;
        this.answers = answers;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Set<AnswerDTO> getAnswers() {
        return answers;
    }

    public void setAnswers(Set<AnswerDTO> answers) {
        this.answers = answers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        QuestionDTO that = (QuestionDTO) o;

        if (!question.equals(that.question)) return false;
        return answers.equals(that.answers);

    }

    @Override
    public int hashCode() {
        int result = question.hashCode();
        result = 31 * result + answers.hashCode();
        return result;
    }
}

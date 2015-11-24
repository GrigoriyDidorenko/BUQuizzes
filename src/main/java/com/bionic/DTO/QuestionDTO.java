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

    public QuestionDTO() {
    }

    public QuestionDTO(String question, Set<AnswerDTO> answers) {
        this.question = question;
        this.answers = answers;
    }

    private String question;
    private Set<AnswerDTO> answers;

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

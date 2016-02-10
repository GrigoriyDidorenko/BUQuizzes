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
    private boolean isMultichoice;
    private boolean isOpen;
    private boolean archived;

    public QuestionDTO() {
    }

    public QuestionDTO(long id, String question, Set<AnswerDTO> answers, boolean isMultichoice, boolean isOpen) {
        this.id = id;
        this.question = question;
        this.answers = answers;
        this.isMultichoice = isMultichoice;
        this.isOpen = isOpen;
    }

    public QuestionDTO(long id, String question, Set<AnswerDTO> answers, boolean isMultichoice, boolean isOpen, boolean archived) {
        this.id = id;
        this.question = question;
        this.answers = answers;
        this.isMultichoice = isMultichoice;
        this.isOpen = isOpen;
        this.archived = archived;
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

    public boolean isMultichoice() {
        return isMultichoice;
    }

    public void setIsMultichoice(boolean isMultichoice) {
        this.isMultichoice = isMultichoice;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setIsOpen(boolean isOpen) {
        this.isOpen = isOpen;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        QuestionDTO that = (QuestionDTO) o;

        if (id != that.id) return false;
        if (isMultichoice != that.isMultichoice) return false;
        if (isOpen != that.isOpen) return false;
        if (!question.equals(that.question)) return false;
        return !(answers != null ? !answers.equals(that.answers) : that.answers != null);

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + question.hashCode();
        result = 31 * result + (answers != null ? answers.hashCode() : 0);
        result = 31 * result + (isMultichoice ? 1 : 0);
        result = 31 * result + (isOpen ? 1 : 0);
        return result;
    }
}

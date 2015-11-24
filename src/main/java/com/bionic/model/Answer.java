package com.bionic.model;

import javax.persistence.*;

/**
 * package: com.bionic.entities
 * project: TestDTO
 * class:
 *
 * @author: Grigoriy Didorenko
 * @date: 01.11.2015
 */
@Entity
@Table(name = "answer", catalog = "quizzes")
public class Answer {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "answer_text", nullable = false)
    private String answerText;
    @Column(name = "is_correct", nullable = false)
    private boolean isCorrect;
    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;
    @Column(name = "picture")
    private String picture;
    @Column(name = "archived", nullable = false)
    private boolean isArchived;

    public Answer(){
    }

    public Answer(String answerText, boolean isCorrect, Question question, String picture, boolean isArchived) {
        this.answerText = answerText;
        this.isCorrect = isCorrect;
        this.question = question;
        this.picture = picture;
        this.isArchived = isArchived;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public boolean getIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(boolean isCorrect) {
        this.isCorrect = isCorrect;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public boolean getIsArchived() {
        return isArchived;
    }

    public void setIsArchived(boolean archived) {
        this.isArchived = archived;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Answer)) return false;

        Answer answer = (Answer) o;

        if (getId() != answer.getId()) return false;
        if (isCorrect != answer.isCorrect) return false;
        if (isArchived != answer.isArchived) return false;
        if (getAnswerText() != null ? !getAnswerText().equals(answer.getAnswerText()) : answer.getAnswerText() != null)
            return false;
        return !(getPicture() != null ? !getPicture().equals(answer.getPicture()) : answer.getPicture() != null);

    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + (getAnswerText() != null ? getAnswerText().hashCode() : 0);
        result = 31 * result + (isCorrect ? 1 : 0);
        result = 31 * result + (getPicture() != null ? getPicture().hashCode() : 0);
        result = 31 * result + (isArchived ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "id=" + id +
                ", answerText='" + answerText + '\'' +
                ", isCorrect=" + isCorrect +
                ", picture='" + picture + '\'' +
                ", archived=" + isArchived +
                '}';
    }
}

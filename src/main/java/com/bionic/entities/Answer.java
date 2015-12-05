package com.bionic.entities;

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
    @Column(name = "mark", nullable = false)
    private int mark;
    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;
    @Column(name = "picture")
    private String picture;
    @Column(name = "archived", nullable = false)
    private boolean isArchived;

    public Answer(){
    }

    public Answer(String answerText, int mark, Question question, String picture, boolean isArchived) {
        this.answerText = answerText;
        this.mark = mark;
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

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
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
        if (o == null || getClass() != o.getClass()) return false;

        Answer answer = (Answer) o;

        if (id != answer.id) return false;
        if (mark != answer.mark) return false;
        if (isArchived != answer.isArchived) return false;
        if (answerText != null ? !answerText.equals(answer.answerText) : answer.answerText != null) return false;
        return !(picture != null ? !picture.equals(answer.picture) : answer.picture != null);

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (answerText != null ? answerText.hashCode() : 0);
        result = 31 * result + mark;
        result = 31 * result + (picture != null ? picture.hashCode() : 0);
        result = 31 * result + (isArchived ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "id=" + id +
                ", answerText='" + answerText + '\'' +
                ", mark=" + mark +
                ", question=" + question +
                ", picture='" + picture + '\'' +
                ", isArchived=" + isArchived +
                '}';
    }
}

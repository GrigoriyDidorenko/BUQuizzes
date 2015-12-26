package com.bionic.entities;

import javax.persistence.*;

/**
 * Created by rondo104 on 25.11.2015.
 */
@Entity
@Table(catalog = "quizzes")
public class UserAnswer {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "resultId", nullable = false)
    private long resultId;
    @Column(name = "userAnswer", nullable = true)
    private String userAnswer;
    @Column(name = "questionId", nullable = false)
    private long questionId;
    @Column(name = "answerId", nullable = true)
    private long answerId;


    public UserAnswer() {
    }

    public UserAnswer(long resultId, String userAnswer, long questionId, long questionId1, long answerId) {
        this.resultId = resultId;
        this.userAnswer = userAnswer;
        this.questionId = questionId;
        questionId = questionId1;
        this.answerId = answerId;
    }

    public long getId() {
        return id;
    }

    public long getResultId() {
        return resultId;
    }

    public void setResultId(long resultId) {
        this.resultId = resultId;
    }

    public String getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(String userAnswer) {
        this.userAnswer = userAnswer;
    }

    public long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(long questionId) {
        this.questionId = questionId;
    }

    public long getAnswerId() {
        return answerId;
    }

    public void setAnswerId(long answerId) {
        this.answerId = answerId;
    }


}

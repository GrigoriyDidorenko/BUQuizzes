package com.bionic.entities;

import javax.persistence.*;

/**
 * Created by rondo104 on 25.11.2015.
 */
@Entity
@NamedNativeQueries({
        @NamedNativeQuery(name = "getUncheckedTests",
        query = "SELECT t.test_name, q.question, @counter \\:= COUNT(ua.is_checked=0) AS uncheckedAnswers FROM user_answer ua" +
                " JOIN result r on r.id = ua.result_id" +
                " JOIN test t on r.test_id = t.id" +
                " JOIN question q on ua.question_id = q.id" +
                " WHERE r.is_checked = false AND q.is_open = true AND ua.is_checked = false" +
                " AND r.test_id IN (SELECT t.id FROM result r" +
                " JOIN test t on t.id = r.test_id" +
                " JOIN user u on u.id = r.user_id" +
                " WHERE r.permission = 2 AND u.id = :userId) and r.permission = 1 and @counter > 0" +
                " GROUP BY ua.question_id")
})
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
    @Column(name = "isChecked", nullable = false)
    private boolean isChecked;


    public UserAnswer() {
    }

    public UserAnswer(long resultId, String userAnswer, long questionId, long answerId, boolean isChecked) {
        this.resultId = resultId;
        this.userAnswer = userAnswer;
        this.questionId = questionId;
        this.answerId = answerId;
        this.isChecked = isChecked;
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

    public boolean isChecked() {
        return isChecked;
    }

    public void setIsChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }
}

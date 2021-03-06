package com.bionic.entities;

import com.bionic.wrappers.OpenQuestionWrapper;

import javax.persistence.*;

/**
 * Created by rondo104 on 25.11.2015.
 */
@Entity
@NamedNativeQueries({
        @NamedNativeQuery(name = "getUncheckedTests",
        query = "SELECT t.test_name AS test_name, q.id AS questionId, q.question AS question, COUNT(ua.is_checked=0) AS uncheckedAnswers FROM user_answer ua" +
                "                 JOIN result r on r.id = ua.result_id " +
                "                 JOIN test t on r.test_id = t.id" +
                "                 JOIN question q on ua.question_id = q.id" +
                "                 WHERE r.is_checked = false AND q.is_open = true AND ua.is_checked = false" +
                "                 AND r.test_id IN (SELECT t.id FROM result r" +
                "                 JOIN test t on t.id = r.test_id" +
                "                 JOIN user u on u.id = r.user_id" +
                "                 WHERE r.permission = 1 AND u.id = :userId) and r.permission = 0" +
                "                 GROUP BY ua.question_id" +
                "                 HAVING COUNT(ua.is_checked=0) > 0"),
        @NamedNativeQuery(name = "getUncheckedAnswersForCurrentQuestion",
        query = "SELECT ua.id, ua.result_id, ua.user_answer, a.amount FROM user_answer ua, (" +
                "SELECT  COUNT(*) as amount FROM user_answer as ua" +
                "                WHERE ua.is_checked = false AND ua.question_id = :questionId) as a" +
                "                WHERE ua.is_checked = false AND ua.question_id = :questionId")
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

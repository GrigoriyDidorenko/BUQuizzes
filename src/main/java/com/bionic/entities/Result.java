package com.bionic.entities;

import javax.persistence.*;
import java.util.Date;

/**
 * package: com.bionic.services
 *
 * @author: Balitsky Alexandr
 * @date: 12.11.2015
 */
@Entity
@NamedQueries({
        @NamedQuery(name = "getCurrentTestById",
                query = "SELECT test From Result result JOIN result.test test JOIN result.user user " +
                        "JOIN test.questions question JOIN question.answers answer WHERE test.id = :testId " +
                        "AND user.id = :userId AND result.permission.id = :permissionId"),
        @NamedQuery(name = "getAvailableTestsNames",
                query = "SELECT test.id, test.testName, test.duration FROM Result result " +
                        "JOIN result.test test " +
                        "JOIN result.user user " +
                        "where user.id = :userId AND result.submited = false")
})
@NamedNativeQueries({
        @NamedNativeQuery(name = "getResultByIds", query = "SELECT result.id FROM Result result " +
                "WHERE result.test_id = :testId AND result.user_id = :userId "),
//        @NamedNativeQuery(name = "getCurrentTestById",
//                query = "SELECT t.id, t.test_name, t.duration, q.id as qId, " +
//                        "q.question as question, q.is_multichoice as is_multichoice, " +
//                        "q.is_open as is_open, a.id as aId, a.answer_text as answer_text  FROM result r " +
//                        "JOIN test t ON r.test_id = t.id " +
//                        "JOIN user u ON r.user_id = u.id " +
//                        "JOIN question q ON t.id = q.test_id JOIN answer a ON q.id = a.question_id " +
//                        "WHERE t.id = :testId AND u.id = :userId AND r.permission = :permissionId " +
//                        "AND q.archived = false AND a.archived = false", resultClass = com.bionic.entities.Test.class)
})
@Table(catalog = "quizzes")
public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "is_checked", nullable = false)
    private boolean isChecked;
    @Column(name = "submited", nullable = false)
    private boolean submited;
    @Column(name = "mark")
    private Integer mark;
    @Column(name = "begin_time")
    private Date beginTime;
    @Column(name = "pass_time")
    private Date passTime;
    @Column(name = "feedback")
    private String feedback;
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "permission", nullable = false)
    private Permission permission;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @ManyToOne
    @JoinColumn(name = "test_id", nullable = false)
    private Test test;

    public Result() {
    }

    public Result(boolean isChecked, boolean submited, Permission permission, User user, Test test) {
        this.isChecked = isChecked;
        this.submited = submited;
        this.permission = permission;
        this.user = user;
        this.test = test;
    }

    public long getId() {
        return id;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setIsChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }

    public boolean isSubmited() {
        return submited;
    }

    public void setSubmited(boolean submited) {
        this.submited = submited;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getPassTime() {
        return passTime;
    }

    public void setPassTime(Date passTime) {
        this.passTime = passTime;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    public Integer getMark() {
        return mark;
    }

    public void setMark(Integer mark) {
        this.mark = mark;
    }

    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Result{");
        sb.append("id=").append(id);
        sb.append(", isChecked=").append(isChecked);
        sb.append(", submited=").append(submited);
        sb.append(", mark=").append(mark);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Result result = (Result) o;

        if (id != result.id) return false;
        if (isChecked != result.isChecked) return false;
        if (submited != result.submited) return false;
        if (mark != result.mark) return false;
        if (beginTime != null ? !beginTime.equals(result.beginTime) : result.beginTime != null) return false;
        if (passTime != null ? !passTime.equals(result.passTime) : result.passTime != null) return false;
        if (feedback != null ? !feedback.equals(result.feedback) : result.feedback != null) return false;
        if (user != null ? !user.equals(result.user) : result.user != null) return false;
        return !(test != null ? !test.equals(result.test) : result.test != null);

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (isChecked ? 1 : 0);
        result = 31 * result + (submited ? 1 : 0);
        result = 31 * result + mark;
        result = 31 * result + (beginTime != null ? beginTime.hashCode() : 0);
        result = 31 * result + (passTime != null ? passTime.hashCode() : 0);
        result = 31 * result + (feedback != null ? feedback.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (test != null ? test.hashCode() : 0);
        return result;
    }
}

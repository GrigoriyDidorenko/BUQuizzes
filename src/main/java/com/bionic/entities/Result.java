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
        @NamedQuery(name = "getCurrentTestByIdForStudent",
                query = "SELECT test From Result result JOIN result.test test JOIN result.user user " +
                        "WHERE test.id = :testId " +
                        "AND user.id = :userId AND result.permission = :permission " +
                        "AND test.archived = false AND result.accessBegin < current_date AND current_date < result.accessEnd"),
        @NamedQuery(name = "getCurrentTestByIdForTrainer",
                query = "SELECT test From Result result JOIN result.test test JOIN result.user user " +
                        "WHERE test.id = :testId " +
                        "AND user.id = :userId AND result.permission = :permission "),
        @NamedQuery(name = "getAvailableTestsNamesForStudent",
                query = "SELECT test.id, test.testName, test.duration FROM Result result " +
                        "JOIN result.test test " +
                        "JOIN result.user user " +
                        "where user.id = :userId AND result.submited = false AND result.permission = :permission"),
        @NamedQuery(name = "getAvailableTestsNamesForTrainer",
                query = "SELECT test.id, test.testName, test.duration FROM Result result " +
                        "JOIN result.test test " +
                        "JOIN result.user user " +
                        "where user.id = :userId AND result.permission = :permission"),
        @NamedQuery(name = "getPassTests",
                query = "SELECT test.id, test.testName, result.mark, result.isChecked FROM Result result JOIN result.test test JOIN result.user user where user.id = :userId and result.submited = TRUE " ),
        @NamedQuery(name = "getTestsForUserId",
        query = "SELECT test.id, test.testName, result.mark, result.isChecked, result.submited, result.id, test.duration FROM Result result JOIN result.test test JOIN result.user user where user.id = :userId" )
               
})
@NamedNativeQueries({
        @NamedNativeQuery(name = "getResultByIds", query = "SELECT result.id FROM Result result " +
                "WHERE result.test_id = :testId AND result.user_id = :userId "),
        @NamedNativeQuery(name = "countCurrentTestGivenToUser",
        query = "SELECT count(*) " +
                "FROM result r " +
                "JOIN user u on r.user_id = u.id " +
                "JOIN test t on r.test_id = t.id " +
                "WHERE r.test_id = :testId AND r.user_id = :userId AND r.permission = :permission AND r.begin_time IS NULL"),
        @NamedNativeQuery(name = "hasUncheckedAnswers",
        query = "SELECT COUNT(*) from result r" +
                "                JOIN user_answer ua ON r.id = ua.result_id" +
                "                WHERE r.id = :resultId AND ua.is_checked = false")
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
    @Column(name = "access_begin")
    private Date accessBegin;
    @Column(name = "access_end")
    private Date accessEnd;
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

    public Result(boolean isChecked, boolean submited, Date accessBegin, Date accessEnd, Permission permission, User user, Test test) {
        this.isChecked = isChecked;
        this.submited = submited;
        this.accessBegin = accessBegin;
        this.accessEnd = accessEnd;
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
        return (mark==null) ? 0 : mark;
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

    public Date getAccessBegin() {
        return accessBegin;
    }

    public void setAccessBegin(Date accessBegin) {
        this.accessBegin = accessBegin;
    }

    public Date getAccessEnd() {
        return accessEnd;
    }

    public void setAccessEnd(Date accessEnd) {
        this.accessEnd = accessEnd;
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

package com.bionic.model;

import javax.persistence.*;
import java.util.Date;

/**
 * package: com.bionic.services
 *
 * @author: Balitsky Alexandr
 * @date: 12.11.2015
 */
@Entity
@NamedQuery(name = "getCurrentTestById",
        query = "SELECT test From Result result JOIN result.test test JOIN result.user user WHERE result.test.id = :testId AND result.user.id = :userId" )
@Table(catalog = "quizzes")
public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "is_checked")
    private boolean isChecked;
    @Column(name = "submited")
    private boolean submited;
    // Default 0 in DB/ If mark null is error
    @Column(name = "mark")
    private int mark;
    @Column(name = "begin_time")
    private Date beginTime;
    @Column(name = "pass_time")
    private Date passTime;
    @Column(name = "feedback")
    private String feedback;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @ManyToOne
    @JoinColumn(name = "test_id", nullable = false)
    private Test test;

    public Result() {
    }

    public Result(User user, Test test) {
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

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
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
        if (!(o instanceof Result)) return false;

        Result result = (Result) o;

        if (id != result.id) return false;
        if (isChecked != result.isChecked) return false;
        if (submited != result.submited) return false;
        if (mark != result.mark) return false;
        if (beginTime != null ? !beginTime.equals(result.beginTime) : result.beginTime != null) return false;
        if (passTime != null ? !passTime.equals(result.passTime) : result.passTime != null) return false;
        if (feedback != null ? !feedback.equals(result.feedback) : result.feedback != null) return false;
        if (user != null ? !user.equals(result.user) : result.user != null) return false;
        if (test != null ? !test.equals(result.test) : result.test != null) return false;

        return true;
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

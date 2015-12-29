package com.bionic.entities;

import javax.persistence.*;

/**
 * Created by rondo104 on 23.12.2015.
 */
@Entity
@Table(name = "one_time_test", catalog = "quizzes")
public class OneTimeTest {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "nickname", nullable = false)
    private String name;
    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "mark", nullable = false)
    private Integer mark;
    @Column(name = "userId", nullable = false )
    private String userId;
    @Column(name = "testId", nullable = false)
    private long testId;


    public OneTimeTest() {
    }

    public long getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getMark() {
        return mark;
    }

    public void setMark(Integer mark) {
        this.mark = mark;
    }

    public long getTestId() {
        return testId;
    }

    public void setTestId(long testId) {
        this.testId = testId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OneTimeTest)) return false;

        OneTimeTest that = (OneTimeTest) o;

        if (id != that.id) return false;
        if (testId != that.testId) return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        if (!name.equals(that.name)) return false;
        if (!email.equals(that.email)) return false;
        return mark != null ? mark.equals(that.mark) : that.mark == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + name.hashCode();
        result = 31 * result + email.hashCode();
        result = 31 * result + (mark != null ? mark.hashCode() : 0);
        result = 31 * result + (int) (testId ^ (testId >>> 32));
        return result;
    }
}

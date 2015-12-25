package com.bionic.entities;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * package: com.bionic.entities
 * project: TestDTO
 * class:
 *
 * @author: Grigoriy Didorenko
 * @date: 01.11.2015
 */
@Entity
@NamedQueries({
        @NamedQuery(name = "getUnarchivedOneTimeTests",
                query = "SELECT t.id, t.testName, t.duration, t1.categoryName   FROM Test t, CategoryTest t1  WHERE t.archived = FALSE and t.oneTime = TRUE and t.categoryTest = t1")
})
@NamedNativeQuery(name = "getUnarchivedTestsNames",
        query = "SELECT t.id, t.test_name FROM Test t WHERE t.archived = FALSE")
@Table(name = "test", catalog = "quizzes")
public class Test {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "duration", nullable = false)
    private int duration;
    @Column(name = "test_name", nullable = false, unique = true)
    private String testName;
    @Column(name = "archived", nullable = false)
    private boolean archived;
    @Column(name = "one_time", nullable = false)
    private boolean oneTime;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "test", fetch = FetchType.EAGER)
    private Set<Question> questions;
    @ManyToMany(mappedBy = "tests")
    private Set<User> users;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private CategoryTest categoryTest;

    public CategoryTest getCategoryTest() {
        return categoryTest;
    }

    public void setCategoryTest(CategoryTest categoryTest) {
        this.categoryTest = categoryTest;
    }

    public Test() {
    }

    public Test(String name) {
        this.testName = name;
    }

    public Test(int duration, String testName, boolean archived, Set<Question> questions) {
        this.duration = duration;
        this.testName = testName;
        this.archived = archived;
        this.questions = questions;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public Set<Question> getQuestions() {
        return questions;
    }

    public boolean isOneTime() {
        return oneTime;
    }

    public void setOneTime(boolean oneTime) {
        this.oneTime = oneTime;
    }

    public Set<Question> getQuestionsNotArchived() {
        Set<Question> notArchivedQuestions = new HashSet<>();
        for (Question question : questions)
            if (!question.getIsArchived())
                notArchivedQuestions.add(question);
        if(notArchivedQuestions.isEmpty() && !questions.isEmpty())
            this.setArchived(true);
        return notArchivedQuestions;
    }

    public void setQuestions(Set<Question> questions) {
        this.questions = questions;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Test)) return false;

        Test test = (Test) o;

        if (getId() != test.getId()) return false;
        if (getDuration() != test.getDuration()) return false;
        if (isArchived() != test.isArchived()) return false;
        if (getTestName() != null ? !getTestName().equals(test.getTestName()) : test.getTestName() != null)
            return false;
        return !(getQuestions() != null ? !getQuestions().equals(test.getQuestions()) : test.getQuestions() != null);

    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + getDuration();
        result = 31 * result + (getTestName() != null ? getTestName().hashCode() : 0);
        result = 31 * result + (isArchived() ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Test{" +
                "id=" + id +
                ", duration=" + duration +
                ", testName='" + testName + '\'' +
                '}';
    }

}

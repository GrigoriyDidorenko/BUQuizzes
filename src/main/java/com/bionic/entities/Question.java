package com.bionic.entities;

import javax.persistence.*;
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
@Table(name = "question", catalog = "quizzes")
public class Question {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "is_multichoice", nullable = false)
    private boolean isMultichoice;
    @Column(name = "is_open", nullable = false)
    private boolean isOpen;
    @Column(name = "mark", nullable = false)
    private float mark;
    @Column(name = "picture")
    private String picture;
    @Column(name = "question")
    private String question;
    @ManyToOne
    @JoinColumn(name = "test_id", nullable = false)
    private Test test;
    @Column(name = "archived", nullable = false)
    private boolean isArchived;
    @OneToMany(mappedBy = "question", fetch = FetchType.EAGER)
    private Set<Answer> answers;

    public Question() {
    }

    public Question(boolean isMultichoice, boolean isOpen, float mark,
                    String picture, String question, Test test, boolean isArchived, Set<Answer> answers) {
        this.isMultichoice = isMultichoice;
        this.isOpen = isOpen;
        this.mark = mark;
        this.picture = picture;
        this.question = question;
        this.test = test;
        this.isArchived = isArchived;
        this.answers = answers;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean getIsMultichoice() {
        return isMultichoice;
    }

    public void setIsMultichoice(boolean isMultichoice) {
        this.isMultichoice = isMultichoice;
    }

    public boolean getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(boolean isOpen) {
        this.isOpen = isOpen;
    }

    public float getMark() {
        return mark;
    }

    public void setMark(float mark) {
        this.mark = mark;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    public boolean getIsArchived() {
        return isArchived;
    }

    public void setIsArchived(boolean archived) {
        this.isArchived = archived;
    }

    public Set<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(Set<Answer> answers) {
        this.answers = answers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Question)) return false;

        Question question1 = (Question) o;

        if (getId() != question1.getId()) return false;
        if (isMultichoice != question1.isMultichoice) return false;
        if (isOpen != question1.isOpen) return false;
        if (getMark() != question1.getMark()) return false;
        if (isArchived != question1.isArchived) return false;
        if (getPicture() != null ? !getPicture().equals(question1.getPicture()) : question1.getPicture() != null)
            return false;
        if (getQuestion() != null ? !getQuestion().equals(question1.getQuestion()) : question1.getQuestion() != null)
            return false;
        return !(getAnswers() != null ? !getAnswers().equals(question1.getAnswers()) : question1.getAnswers() != null);

    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + (isMultichoice ? 1 : 0);
        result = 31 * result + (isOpen ? 1 : 0);
        result = (int) (31 * result + getMark());
        result = 31 * result + (getPicture() != null ? getPicture().hashCode() : 0);
        result = 31 * result + (getQuestion() != null ? getQuestion().hashCode() : 0);
        result = 31 * result + (isArchived ? 1 : 0);
        result = 31 * result + (getAnswers() != null ? getAnswers().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", isMultichoice=" + isMultichoice +
                ", isOpen=" + isOpen +
                ", mark=" + mark +
                ", picture='" + picture + '\'' +
                ", question='" + question + '\'' +
                ", archived=" + isArchived +
                '}';
    }
}

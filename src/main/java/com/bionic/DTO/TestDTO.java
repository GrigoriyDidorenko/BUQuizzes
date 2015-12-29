package com.bionic.DTO;

import java.util.Date;
import java.util.Set;

/**
 * package: com.bionic.DTO
 * project: TestDTO
 * class:
 *
 * @author: Grigoriy Didorenko
 * @date: 02.11.2015
 */

public class TestDTO {

    // General filds
    private long id;
    private String testName;
    //Filds for Avaliable Tests
    private int duration;
    private Set<QuestionDTO> questions;
    //Filds for Submited Tests
    private boolean isChecked;
    private int mark;
    private boolean submited;
    private boolean oneTime ;
    private long categoryTestId;
    private String categoryTestName;


    public TestDTO() {
    }

    public TestDTO(String categoryTestName, long id, String testName, int duration ) {
        this.id = id;
        this.testName = testName;
        this.duration = duration;
        this.categoryTestName = categoryTestName;
    }

    public TestDTO(long id, String testName, int duration) {
        this.id = id;
        this.duration = duration;
        this.testName = testName;
    }

    public TestDTO(long id, String testName, int duration, Set<QuestionDTO> questions) {
        this.id = id;
        this.duration = duration;
        this.testName = testName;
        this.questions = questions;
    }

    public TestDTO(long id, String testName, int mark, boolean isChecked) {
        this.id = id;
        this.testName = testName;
        this.mark = mark;
        this.isChecked = isChecked;
    }

    public TestDTO(long id, String testName, int mark, boolean isChecked, boolean submited, int duration) {
        this.id = id;
        this.testName = testName;
        this.mark = mark;
        this.isChecked = isChecked;
        this.submited = submited;
        this.duration = duration;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setIsChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public TestDTO(String testName) {
        this.testName = testName;
    }

    public String getTestName() {
        return testName;
    }

    public long getId() {
        return id;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Set<QuestionDTO> getQuestions() {
        return questions;
    }

    public void setQuestions(Set<QuestionDTO> questions) {
        this.questions = questions;
    }

    public boolean isSubmited() {
        return submited;
    }

    public void setSubmited(boolean submited) {
        this.submited = submited;
    }

    public boolean isOneTime() {
        return oneTime;
    }

    public void setOneTime(boolean oneTime) {
        this.oneTime = oneTime;
    }

    public long getCategoryTestId() {
        return categoryTestId;
    }

    public void setCategoryTestId(long categoryTestId) {
        this.categoryTestId = categoryTestId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TestDTO)) return false;

        TestDTO testDTO = (TestDTO) o;

        if (id != testDTO.id) return false;
        if (duration != testDTO.duration) return false;
        if (isChecked != testDTO.isChecked) return false;
        if (mark != testDTO.mark) return false;
        if (submited != testDTO.submited) return false;
        if (!testName.equals(testDTO.testName)) return false;
        return !(questions != null ? !questions.equals(testDTO.questions) : testDTO.questions != null);

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + testName.hashCode();
        result = 31 * result + duration;
        result = 31 * result + (questions != null ? questions.hashCode() : 0);
        result = 31 * result + (isChecked ? 1 : 0);
        result = 31 * result + mark;
        result = 31 * result + (submited ? 1 : 0);
        return result;
    }

}

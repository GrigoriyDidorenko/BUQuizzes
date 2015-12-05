package com.bionic.DTO;

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

    private long id;
    private String testName;
    private int duration;
    private Set<QuestionDTO> questions;
    private long resultId;

    public TestDTO() {
    }

    public TestDTO(String testName, int duration) {
        this.duration = duration;
        this.testName = testName;
    }

    public TestDTO(long id,String testName, int duration, Set<QuestionDTO> questions) {
        this.id = id;
        this.duration = duration;
        this.testName = testName;
        this.questions = questions;
    }

    public long getResultId() {
        return resultId;
    }

    public void setResultId(long resultId) {
        this.resultId = resultId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TestDTO testDTO = (TestDTO) o;

        if (id != testDTO.id) return false;
        if (duration != testDTO.duration) return false;
        if (!testName.equals(testDTO.testName)) return false;
        return !(questions != null ? !questions.equals(testDTO.questions) : testDTO.questions != null);

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + testName.hashCode();
        result = 31 * result + duration;
        result = 31 * result + (questions != null ? questions.hashCode() : 0);
        return result;
    }
}

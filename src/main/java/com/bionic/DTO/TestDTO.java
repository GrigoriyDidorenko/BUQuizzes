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

        if (duration != testDTO.duration) return false;
        if (!testName.equals(testDTO.testName)) return false;
        return questions.equals(testDTO.questions);

    }


}

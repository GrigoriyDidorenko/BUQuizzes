package com.bionic.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
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

    // General fields
    private long id;
    private String testName;
    //Fields for Available Tests
    private int duration;
    private Set<QuestionDTO> questions;
    //Fields for Submitted Tests
    private boolean isChecked;
    private Integer mark;
    private boolean submitted;
    private boolean oneTime ;
    private long categoryTestId;
    private String categoryTestName;

    private List<TestToGroup> testsToGroups;

    //TODO: CHECK IT

    public static class TestToGroup{

        private String groupName;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        private Date accessBegin;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        private Date accessEnd;

        public TestToGroup(){}

        public TestToGroup(String groupName, Date accessBegin, Date accessEnd) throws ParseException {
            this.groupName = groupName;
            this.accessBegin = accessBegin;
            this.accessEnd = accessEnd;
        }

        public String getGroupName() {
            return groupName;
        }

        public void setGroupName(String groupName) {
            this.groupName = groupName;
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
    }

    public TestDTO() {
    }

    public TestDTO(String testName, int duration, boolean oneTime, String categoryTestName, List<TestToGroup> testsToGroups, Set<QuestionDTO> questions) {
        this.testName = testName;
        this.duration = duration;
        this.oneTime = oneTime;
        this.categoryTestName = categoryTestName;
        this.testsToGroups = testsToGroups;
        this.questions = questions;
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

    public TestDTO(long id, String testName, int mark, boolean isChecked, boolean submitted, int duration) {
        this.id = id;
        this.testName = testName;
        this.mark = mark;
        this.isChecked = isChecked;
        this.submitted = submitted;
        this.duration = duration;
    }



    public boolean isChecked() {
        return isChecked;
    }

    public void setIsChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }

    public int getMark() {
        return (mark==null) ? 0 : mark;
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

    public boolean isSubmitted() {
        return submitted;
    }

    public void setSubmitted(boolean submitted) {
        this.submitted = submitted;
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

    public String getCategoryTestName() {
        return categoryTestName;
    }

    public void setCategoryTestName(String categoryTestName) {
        this.categoryTestName = categoryTestName;
    }

    public List<TestToGroup> getTestsToGroups() {
        return testsToGroups;
    }

    public void setTestsToGroups(List<TestToGroup> testsToGroups) {
        this.testsToGroups = testsToGroups;
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
        if (submitted != testDTO.submitted) return false;
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
        result = 31 * result + (submitted ? 1 : 0);
        return result;
    }

}

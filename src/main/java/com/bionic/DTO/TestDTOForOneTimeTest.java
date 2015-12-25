package com.bionic.DTO;

import java.util.Set;

/**
 * Created by rondo104 on 25.12.2015.
 */
public class TestDTOForOneTimeTest {

    private long id;
    private String testName;
    private int duration;
    private String categoryTestName;

    public TestDTOForOneTimeTest() {
    }

    public TestDTOForOneTimeTest(long id, String testName, int duration, String categoryTestName) {
        this.id = id;
        this.testName = testName;
        this.duration = duration;
        this.categoryTestName = categoryTestName;
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

    public String getCategoryTestName() {
        return categoryTestName;
    }

    public void setCategoryTestName(String categoryTestName) {
        this.categoryTestName = categoryTestName;
    }
}

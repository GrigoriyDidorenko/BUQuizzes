package com.bionic.wrappers;

/**
 * Created by c2411 on 24.11.2015.
 */
public class TestWrapper {

    private String name;
    private int duration;

    public TestWrapper(String name, int duration) {
        this.name = name;
        this.duration = duration;
    }

    public TestWrapper(){
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}

package com.bionic.DTO;

/**
 * Created by rondo104 on 02.12.2015.
 */
public class ResultDTO {

    int mark;
    String checkStatus;

    public ResultDTO() {
    }

    public ResultDTO(int mark, String checkStatus) {
        this.mark = mark;
        this.checkStatus = checkStatus;
    }


    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public String getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(String checkStatus) {
        this.checkStatus = checkStatus;
    }
}

package com.bionic.DTO;

/**
 * Created by rondo104 on 02.12.2015.
 */
public class ResultDTO {

    int mark;
    String checkStatus;

    public ResultDTO(int mark, String statusPerevirki) {
        this.mark = mark;
        this.checkStatus = statusPerevirki;
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

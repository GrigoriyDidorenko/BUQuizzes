package com.bionic.wrappers;

import java.util.List;

/**
 * Created by c2411 on 29.12.2015.
 */
public class NickMarkWrapper {

    private List<NickMark> nickMarks;
    private long pageCount;

    public NickMarkWrapper(List<NickMark> nickMarks, long pageCount) {
        this.nickMarks = nickMarks;
        this.pageCount = pageCount;
    }

    public List<NickMark> getNickMarks() {
        return nickMarks;
    }

    public void setNickMarks(List<NickMark> nickMarks) {
        this.nickMarks = nickMarks;
    }

    public long getPageCount() {
        return pageCount;
    }

    public void setPageCount(long pageCount) {
        this.pageCount = pageCount;
    }

    public class NickMark{

    private String nickname;
    private int mark;

    public NickMark(){}

    public NickMark(String nickname, int mark) {
        this.nickname = nickname;
        this.mark = mark;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }
    }
}

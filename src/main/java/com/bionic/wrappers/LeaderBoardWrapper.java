package com.bionic.wrappers;

import java.util.List;

/**
 * Created by c2411 on 29.12.2015.
 */
public class LeaderBoardWrapper {

    private List<NickMark> nickMarks;
    private long currentPageNumber;
    private long pageNumber;

    public LeaderBoardWrapper(List<NickMark> nickMarks, long pageNumber) {
        this.nickMarks = nickMarks;
        this.pageNumber = pageNumber;
    }

    public LeaderBoardWrapper(List<NickMark> nickMarks, long pageNumber, long currentPageNumber) {
        this.nickMarks = nickMarks;
        this.pageNumber = pageNumber;
        this.currentPageNumber = currentPageNumber;
    }

    public long getCurrentPageNumber() {
        return currentPageNumber;
    }

    public void setCurrentPageNumber(long currentPageNumber) {
        this.currentPageNumber = currentPageNumber;
    }

    public List<NickMark> getNickMarks() {
        return nickMarks;
    }

    public void setNickMarks(List<NickMark> nickMarks) {
        this.nickMarks = nickMarks;
    }

    public long getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(long pageNumber) {
        this.pageNumber = pageNumber;
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

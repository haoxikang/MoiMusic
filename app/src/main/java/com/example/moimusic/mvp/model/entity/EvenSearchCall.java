package com.example.moimusic.mvp.model.entity;

/**
 * Created by 康颢曦 on 2016/4/4.
 */
public class EvenSearchCall {
    private String searchString;

    public EvenSearchCall(String searchString) {
        this.searchString = searchString;
    }

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }
}

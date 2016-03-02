package com.example.moimusic.mvp.model.entity;

/**
 * Created by qqq34 on 2016/2/28.
 */
public class EvenMusicListChangeCall {
    private String musicId;

    public EvenMusicListChangeCall(String musicId) {
        this.musicId = musicId;
    }

    public String getMusicId() {
        return musicId;
    }

    public void setMusicId(String musicId) {
        this.musicId = musicId;
    }
}

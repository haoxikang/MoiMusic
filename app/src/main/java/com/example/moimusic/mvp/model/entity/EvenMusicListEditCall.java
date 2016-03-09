package com.example.moimusic.mvp.model.entity;

/**
 * Created by Administrator on 2016/3/9.
 */
public class EvenMusicListEditCall {
    private MusicList musicList;

    public EvenMusicListEditCall(MusicList musicList) {
        this.musicList = musicList;
    }

    public MusicList getMusicList() {
        return musicList;
    }

    public void setMusicList(MusicList musicList) {
        this.musicList = musicList;
    }
}

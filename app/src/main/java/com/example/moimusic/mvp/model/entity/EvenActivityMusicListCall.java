package com.example.moimusic.mvp.model.entity;

import java.util.List;

/**
 * Created by qqq34 on 2016/2/24.
 */
public class EvenActivityMusicListCall {
    private boolean isEnable;
    private List<Music> musicList;

    public EvenActivityMusicListCall(boolean isEnable, List<Music> musicList) {
        this.isEnable = isEnable;
        this.musicList = musicList;
    }

    public List<Music> getMusicList() {
        return musicList;
    }

    public void setMusicList(List<Music> musicList) {
        this.musicList = musicList;
    }

    public boolean isEnable() {
        return isEnable;
    }

    public void setEnable(boolean enable) {
        isEnable = enable;
    }
}

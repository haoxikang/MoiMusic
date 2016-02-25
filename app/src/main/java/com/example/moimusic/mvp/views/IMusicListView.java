package com.example.moimusic.mvp.views;

import android.content.Intent;

import com.example.moimusic.mvp.model.entity.MusicList;

/**
 * Created by qqq34 on 2016/2/23.
 */
public interface IMusicListView {
    Intent GetIntent();
    void showView(MusicList musicList);
    void finish();
    void SetPlayButtonEnable(boolean isEnable);
    void startActivity(Intent intent);
}

package com.example.moimusic.mvp.views;

import com.example.moimusic.adapter.MusicListContentViewAdapter;

/**
 * Created by qqq34 on 2016/2/24.
 */
public interface IMusicListContentFragmentView {
    void ShowList(MusicListContentViewAdapter musicListContentViewAdapter);
    void ShowSnackBar(String s);
}

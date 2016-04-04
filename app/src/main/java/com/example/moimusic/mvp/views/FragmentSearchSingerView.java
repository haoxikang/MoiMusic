package com.example.moimusic.mvp.views;

import com.example.moimusic.adapter.SearchMusicAdapter;

/**
 * Created by 康颢曦 on 2016/4/4.
 */
public interface FragmentSearchSingerView {
    void showSnackbar(String s);
    void showList(SearchMusicAdapter adapter);
    void showAndHideSwip(boolean isShow);
}

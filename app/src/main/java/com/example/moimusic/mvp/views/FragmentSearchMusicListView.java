package com.example.moimusic.mvp.views;

import com.example.moimusic.adapter.SearchMusicAdapter;
import com.example.moimusic.adapter.SearchMusicListAdapter;

/**
 * Created by 康颢曦 on 2016/4/7.
 */
public interface FragmentSearchMusicListView {
    void showSnackbar(String s);
    void showList(SearchMusicListAdapter adapter);
    void showAndHideSwip(boolean isShow);
}

package com.example.moimusic.mvp.views;

import com.example.moimusic.adapter.SearchMusicAdapter;
import com.example.moimusic.mvp.model.entity.Music;

import java.util.List;

/**
 * Created by 康颢曦 on 2016/4/4.
 */
public interface FragmentSearchMusicView {
    void showSnackbar(String s);
    void showList(SearchMusicAdapter adapter);
    void showAndHideSwip(boolean isShow);
}

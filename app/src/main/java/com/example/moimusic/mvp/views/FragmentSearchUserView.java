package com.example.moimusic.mvp.views;

import com.example.moimusic.adapter.SearchMusicAdapter;
import com.example.moimusic.mvp.model.entity.MoiUser;

/**
 * Created by 康颢曦 on 2016/4/8.
 */
public interface FragmentSearchUserView {
    void showSnackbar(String s);
    void showView(MoiUser moiUser);
    void showAndHideSwip(boolean isShow);
}

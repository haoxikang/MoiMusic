package com.example.moimusic.mvp.views;

import com.example.moimusic.adapter.MusicListViewAdapter;

/**
 * Created by qqq34 on 2016/2/21.
 */
public interface FragmentFavouriteMusicListView  {
    void setAdapter(MusicListViewAdapter adapter);
    void hideSwipe(boolean isHide);
    void showSnackBar(String s);
    void updataList();
}

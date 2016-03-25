package com.example.moimusic.mvp.views;

import com.example.moimusic.adapter.HomeMusicAdapter;
import com.example.moimusic.mvp.model.entity.Music;
import com.example.moimusic.mvp.model.entity.Recommend;

import java.util.List;

/**
 * Created by qqq34 on 2016/3/17.
 */
public interface FragmentGroomView {
    void initPage(List<Recommend> recommends);
    void ShowSnackBar(String s);
    void onDatafetched(List<Music> musics);
    void ShowSwipe(boolean isShow);
}

package com.example.moimusic.mvp.views;

import com.example.moimusic.mvp.model.entity.Music;
import com.example.moimusic.mvp.model.entity.Recommend;

import java.util.List;

/**
 * Created by Administrator on 2016/3/23.
 */
public interface FragmentAniMusicView {
    void initPage(List<Recommend> recommends);
    void ShowSnackBar(String s);
    void onDatafetched(List<Music> musics);
}

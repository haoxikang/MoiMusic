package com.example.moimusic.play;

import com.example.moimusic.mvp.model.entity.Music;

import java.util.List;

/**
 * Created by qqq34 on 2016/2/2.
 */
public abstract class APlaySequence {


    public abstract List<Music> initList(List<Music> musicList);
    public abstract int nextMusic(int position,List<Music> musicList);

    public abstract int previous(int position,List<Music> musicList);
}

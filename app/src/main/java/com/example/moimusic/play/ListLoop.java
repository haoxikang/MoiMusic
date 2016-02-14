package com.example.moimusic.play;

import com.example.moimusic.mvp.model.entity.Music;

import java.util.List;

/**
 * Created by qqq34 on 2016/2/2.
 */
public class ListLoop extends APlaySequence {
    @Override
    public List<Music> initList(List<Music> musicList) {
            return musicList;
    }

    @Override
    public int nextMusic(int position,List<Music> musicList) {
        if (position==(musicList.size()-1)){
            return 0;
        }else {
            position++;
            return position;
        }
    }

    @Override
    public int previous(int position,List<Music> musicList) {
        if (position==0){
            return musicList.size()-1;
        }else {
            position--;
            return position;
        }
    }
}

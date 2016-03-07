package com.example.moimusic.mvp.model.biz;

import com.example.moimusic.AppApplication;
import com.example.moimusic.mvp.model.entity.Music;
import com.example.moimusic.mvp.model.entity.MusicList;

import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by qqq34 on 2016/3/5.
 */
public  class IncreaseBiz extends DataBiz {
    public static void musicIncreace(String musicId){
        Music music = new Music();
        music.increment("PlayNum");
        music.update(AppApplication.context, musicId, new UpdateListener() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailure(int i, String s) {

            }
        });
    }
    public static void musicListIncreace(String listId){
        MusicList musicList = new MusicList();
        musicList.increment("PlayNum");
        musicList.update(AppApplication.context, listId, new UpdateListener() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailure(int i, String s) {

            }
        });
    }
}

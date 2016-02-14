package com.example.moimusic.mvp.model.entity;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * Created by qqq34 on 2016/1/27.
 */
public class MusicList extends BmobObject{
    private String Name;
    private BmobRelation Music;
    private boolean isRelease;
    private Integer CollegeNum;
    private Integer PlayNum;
    private String ListImageUri;

    @Override
    public String toString() {
        return "MusicList{" +
                "Name='" + Name + '\'' +
                ", Music=" + Music +
                ", isRelease=" + isRelease +
                ", CollegeNum=" + CollegeNum +
                ", PlayNum=" + PlayNum +
                ", ShareTime="  +
                ", ListImageUri='" + ListImageUri + '\'' +
                '}';
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public BmobRelation getMusic() {
        return Music;
    }

    public void setMusic(BmobRelation music) {
        Music = music;
    }

    public boolean isRelease() {
        return isRelease;
    }

    public void setRelease(boolean release) {
        isRelease = release;
    }

    public Integer getCollegeNum() {
        return CollegeNum;
    }

    public void setCollegeNum(Integer collegeNum) {
        CollegeNum = collegeNum;
    }

    public Integer getPlayNum() {
        return PlayNum;
    }

    public void setPlayNum(Integer playNum) {
        PlayNum = playNum;
    }


    public String getListImageUri() {
        return ListImageUri;
    }

    public void setListImageUri(String listImageUri) {
        ListImageUri = listImageUri;
    }
}

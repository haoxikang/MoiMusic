package com.example.moimusic.mvp.model.entity;

import java.util.Date;

import cn.bmob.v3.BmobObject;

/**
 * Created by qqq34 on 2016/1/27.
 */
public class Animation extends BmobObject{
    private MoiUser User;
    private Integer CollegeNum;
    private Integer PlayNum;
    private Date SellTime;
    private String AniImageUri;
    private  String Name;

    public MoiUser getUser() {
        return User;
    }

    public void setUser(MoiUser user) {
        User = user;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAniImageUri() {
        return AniImageUri;
    }

    public void setAniImageUri(String aniImageUri) {
        AniImageUri = aniImageUri;
    }

    public Date getSellTime() {
        return SellTime;
    }

    public void setSellTime(Date sellTime) {
        SellTime = sellTime;
    }

    public Integer getPlayNum() {
        return PlayNum;
    }

    public void setPlayNum(Integer playNum) {
        PlayNum = playNum;
    }

    public Integer getCollegeNum() {
        return CollegeNum;
    }

    public void setCollegeNum(Integer collegeNum) {
        CollegeNum = collegeNum;
    }

    @Override
    public String toString() {
        return "Animation{" +
                "User=" + User +
                ", CollegeNum=" + CollegeNum +
                ", PlayNum=" + PlayNum +
                ", SellTime=" + SellTime +
                ", AniImageUri='" + AniImageUri + '\'' +
                ", Name='" + Name + '\'' +
                '}';
    }
}

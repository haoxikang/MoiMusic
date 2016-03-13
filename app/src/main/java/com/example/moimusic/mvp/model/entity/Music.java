package com.example.moimusic.mvp.model.entity;

import java.util.Date;

import cn.bmob.v3.BmobObject;

/**
 * Created by qqq34 on 2016/1/27.
 */
public class Music extends BmobObject{
    private MoiUser User;
    private String MusicName;
    private String wangyiID;
    private String MusicUri;
    private String MusicImageUri;
    private Integer CollegeNum;
    private Integer PlayNum;
    private Date SellTime;
    private String LyricUri;
private String album;
    private String singer;

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public MoiUser getUser() {
        return User;
    }

    public void setUser(MoiUser user) {
        User = user;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getLyricUri() {
        return LyricUri;
    }

    public void setLyricUri(String lyricUri) {
        LyricUri = lyricUri;
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

    public String getMusicImageUri() {
        return MusicImageUri;
    }

    public void setMusicImageUri(String musicImageUri) {
        MusicImageUri = musicImageUri;
    }

    public String getMusicUri() {
        return MusicUri;
    }

    public void setMusicUri(String musicUri) {
        MusicUri = musicUri;
    }



    public String getMusicName() {
        return MusicName;
    }

    public void setMusicName(String musicName) {
        MusicName = musicName;
    }

    public String getWangyiID() {
        return wangyiID;
    }

    public void setWangyiID(String wangyiID) {
        this.wangyiID = wangyiID;
    }

    @Override
    public String toString() {
        return "Music{" +
                "User=" + User +
                ", MusicName='" + MusicName + '\'' +
                ", wangyiID='" + wangyiID + '\'' +
                ", MusicUri='" + MusicUri + '\'' +
                ", MusicImageUri='" + MusicImageUri + '\'' +
                ", CollegeNum=" + CollegeNum +
                ", PlayNum=" + PlayNum +
                ", SellTime=" + SellTime +
                ", LyricUri='" + LyricUri + '\'' +
                ", album='" + album + '\'' +
                ", singer='" + singer + '\'' +
                '}';
    }
}

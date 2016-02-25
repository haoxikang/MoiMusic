package com.example.moimusic.mvp.views;

import android.content.Intent;

import com.example.moimusic.mvp.model.entity.MoiUser;

/**
 * Created by qqq34 on 2016/2/19.
 */
public interface IUserCenterActivity {
    Intent GetIntent();
    void finishActivity();
    void ToNextActivity(Intent intent);
    void updataView(MoiUser  moiUser,int like,int followed);
    void ShowSnackBar(String s);
}

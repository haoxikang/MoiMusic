package com.example.moimusic.mvp.views;

import android.content.Intent;
import android.net.Uri;

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
    void updataImageAndName(String uri,String name);
    void hideFloatButton(boolean ishide);
    void setButtonBK(boolean isFollow);
    void setButtonEnable(boolean isEnable);
}

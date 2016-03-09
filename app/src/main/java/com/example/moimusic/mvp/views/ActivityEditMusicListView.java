package com.example.moimusic.mvp.views;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;

/**
 * Created by Administrator on 2016/3/9.
 */
public interface ActivityEditMusicListView {
    void showImage(Uri uri);
    void showProgress(boolean isShow);
    void setEnable(boolean isEnable);
    Editable getText();
    void ShowError(boolean isError);
    void ShowSnackBar(String s);
    Intent GetIntent();
    void finish();
}

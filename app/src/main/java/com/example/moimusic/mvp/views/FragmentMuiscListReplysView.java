package com.example.moimusic.mvp.views;

import com.a.a.a.V;
import com.example.moimusic.adapter.MusicListContentViewAdapter;
import com.example.moimusic.adapter.MusicListReplyAdapter;

/**
 * Created by qqq34 on 2016/2/25.
 */
public interface FragmentMuiscListReplysView {
    void ShowList(MusicListReplyAdapter adapter);
    void ShowSnackBar(String s);
    void setViewEnable(boolean isEnable);
    void updataList();
}

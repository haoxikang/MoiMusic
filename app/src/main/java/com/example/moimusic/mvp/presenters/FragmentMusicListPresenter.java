package com.example.moimusic.mvp.presenters;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;

import com.example.moimusic.R;
import com.example.moimusic.adapter.MusicListViewAdapter;
import com.example.moimusic.factorys.DataBizFactory;
import com.example.moimusic.factorys.Factory;
import com.example.moimusic.mvp.model.ApiService;
import com.example.moimusic.mvp.model.biz.MusicListBiz;
import com.example.moimusic.mvp.model.entity.MoiUser;
import com.example.moimusic.mvp.model.entity.MusicList;
import com.example.moimusic.mvp.views.FragmentMusicListView;
import com.example.moimusic.mvp.views.FragmentSignInView;
import com.example.moimusic.ui.activity.ActivityMusicList;
import com.example.moimusic.ui.activity.LogActivity;
import com.rey.material.app.Dialog;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;
import de.greenrobot.event.EventBus;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by qqq34 on 2016/2/20.
 */
public class FragmentMusicListPresenter extends BasePresenterImpl {
    private Factory factory;
    private FragmentMusicListView fragmentMusicListView;
    private MusicListViewAdapter musicListViewAdapter;
    private int page = 1;
    private String id;
    private FragmentActivity fragmentActivity;

    public FragmentMusicListPresenter(ApiService apiService) {
        factory = new DataBizFactory();
    }

    public void attach(FragmentMusicListView fragmentMusicListView, String id, FragmentActivity fragmentActivity) {
        this.fragmentMusicListView = fragmentMusicListView;
        this.id = id;
        this.fragmentActivity = fragmentActivity;
    }

    private boolean isCurrentUser() {

        if (id.equals(BmobUser.getCurrentUser(fragmentActivity, MoiUser.class).getObjectId())) {
            return true;
        } else
            return false;
    }

    public void getMusicLists() {

        MusicListBiz musicListBiz = factory.createBiz(MusicListBiz.class);
        mSubscriptions.add(musicListBiz.getMyMusic(page, id.equals(BmobUser.getCurrentUser(fragmentActivity, MoiUser.class).getObjectId()), id)
                .subscribe(musicLists -> {
                    if (page == 1) {
                        musicListViewAdapter = new MusicListViewAdapter(musicLists, fragmentActivity,isCurrentUser());
                        fragmentMusicListView.setAdapter(musicListViewAdapter);

                    } else if (page != 1) {
                        musicListViewAdapter.addData(musicLists);
                    }
                    fragmentMusicListView.hideSwipe(true);
                    page++;
                }, throwable -> {
                    fragmentMusicListView.showSnackBar(throwable.getMessage());
                    fragmentMusicListView.hideSwipe(true);
                }));

    }
    public void onDeleteClick(View v,String id){
        Dialog dialog = new Dialog(fragmentActivity);
        dialog.setTitle(fragmentActivity.getResources().getString(R.string.sureTODeleteList));
        dialog.negativeAction(fragmentActivity.getResources().getString(R.string.cancel));
        dialog.positiveAction(fragmentActivity.getResources().getString(R.string.OK));
        dialog.negativeActionClickListener(v1->dialog.dismiss());
        dialog.positiveActionClickListener(v1 -> {
            dialog.cancelable(false);
            MusicListBiz musicListBiz = factory.createBiz(MusicListBiz.class);
            mSubscriptions.add(musicListBiz.deleteList(id, true)
                    .subscribe(musicList -> {
                        fragmentMusicListView.showSnackBar("删除歌单成功");
                        fragmentMusicListView.updateList();
                        setPage(1);
            getMusicLists();
                        dialog.dismiss();

                    },throwable -> {
                        fragmentMusicListView.showSnackBar(throwable.getMessage());
                        dialog.dismiss();
                    }));
        });
        dialog.show();
    }
    public void setPage(int page) {
        this.page = page;
    }

}

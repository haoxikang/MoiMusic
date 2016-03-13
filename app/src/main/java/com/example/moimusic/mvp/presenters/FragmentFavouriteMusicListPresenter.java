package com.example.moimusic.mvp.presenters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.example.moimusic.R;
import com.example.moimusic.adapter.MusicListViewAdapter;
import com.example.moimusic.factorys.DataBizFactory;
import com.example.moimusic.factorys.Factory;
import com.example.moimusic.mvp.model.ApiService;
import com.example.moimusic.mvp.model.biz.MusicListBiz;
import com.example.moimusic.mvp.model.entity.MoiUser;
import com.example.moimusic.mvp.views.FragmentFavouriteMusicListView;
import com.example.moimusic.mvp.views.FragmentMusicListView;
import com.rey.material.app.Dialog;

import cn.bmob.v3.BmobUser;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by qqq34 on 2016/2/21.
 */
public class FragmentFavouriteMusicListPresenter extends BasePresenterImpl {
    private Factory factory;
    private FragmentFavouriteMusicListView fragmentFavouriteMusicListView;
    private MusicListViewAdapter musicListViewAdapter;
    private FragmentActivity fragmentActivity;
    private int page = 1;

    public FragmentFavouriteMusicListPresenter(ApiService apiService) {
        factory = new DataBizFactory();
    }

    public void attach(FragmentFavouriteMusicListView fragmentFavouriteMusicListView, FragmentActivity fragmentActivity) {
        this.fragmentFavouriteMusicListView = fragmentFavouriteMusicListView;
        this.fragmentActivity = fragmentActivity;
    }

    public void getMusicLists() {

        MusicListBiz musicListBiz = factory.createBiz(MusicListBiz.class);
        mSubscriptions.add(musicListBiz.getCollegeMusic(page, false).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(musicLists -> {
                    if (page == 1) {
                        fragmentFavouriteMusicListView.hideSwipe(false);
                        musicListViewAdapter = new MusicListViewAdapter(musicLists, fragmentActivity, true);
                        fragmentFavouriteMusicListView.setAdapter(musicListViewAdapter);

                    } else if (musicListViewAdapter != null) {
                        musicListViewAdapter.addData(musicLists);
                    }
                    fragmentFavouriteMusicListView.hideSwipe(true);
                    page++;
                }, throwable -> {
                    fragmentFavouriteMusicListView.showSnackBar(throwable.getMessage());
                    fragmentFavouriteMusicListView.hideSwipe(true);
                }));
    }

    public void setPage(int page) {
        this.page = page;
    }

    public void onDeleteClick(View v, String id) {
        Dialog dialog = new Dialog(fragmentActivity);
        dialog.setTitle(fragmentActivity.getResources().getString(R.string.sureTODeleteList));
        dialog.negativeAction(fragmentActivity.getResources().getString(R.string.cancel));
        dialog.positiveAction(fragmentActivity.getResources().getString(R.string.OK));
        dialog.negativeActionClickListener(v1 -> dialog.dismiss());
        dialog.positiveActionClickListener(v1 -> {
            dialog.cancelable(false);
            MusicListBiz musicListBiz = factory.createBiz(MusicListBiz.class);
            mSubscriptions.add(musicListBiz.deleteList(id, false).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(musicList -> {
                        fragmentFavouriteMusicListView.showSnackBar("删除歌单成功");
                        fragmentFavouriteMusicListView.updataList();
                        setPage(1);
                        getMusicLists();
                        dialog.dismiss();

                    },throwable -> {
                        fragmentFavouriteMusicListView.showSnackBar(throwable.getMessage());
                        dialog.dismiss();
                    }));
        });
        dialog.show();
    }
}

package com.example.moimusic.mvp.presenters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.example.moimusic.adapter.MusicListViewAdapter;
import com.example.moimusic.factorys.DataBizFactory;
import com.example.moimusic.factorys.Factory;
import com.example.moimusic.mvp.model.ApiService;
import com.example.moimusic.mvp.model.biz.MusicListBiz;
import com.example.moimusic.mvp.model.entity.MoiUser;
import com.example.moimusic.mvp.views.FragmentFavouriteMusicListView;
import com.example.moimusic.mvp.views.FragmentMusicListView;

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
        mSubscriptions.add(musicListBiz.getCollegeMusic(page,false).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(musicLists -> {
                    if (page == 1) {
                        fragmentFavouriteMusicListView.hideSwipe(false);
                        musicListViewAdapter = new MusicListViewAdapter(musicLists,fragmentActivity);
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
}

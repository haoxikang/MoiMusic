package com.example.moimusic.mvp.presenters;

import android.content.Context;

import com.example.moimusic.adapter.MusicListViewAdapter;
import com.example.moimusic.factorys.DataBizFactory;
import com.example.moimusic.factorys.Factory;
import com.example.moimusic.mvp.model.ApiService;
import com.example.moimusic.mvp.model.biz.MusicListBiz;
import com.example.moimusic.mvp.model.entity.MoiUser;
import com.example.moimusic.mvp.model.entity.MusicList;
import com.example.moimusic.mvp.views.FragmentMusicListView;
import com.example.moimusic.mvp.views.FragmentSignInView;

import java.util.ArrayList;

import cn.bmob.v3.BmobUser;
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
    private  String id;
    private Context context;

    public FragmentMusicListPresenter(ApiService apiService) {
        factory = new DataBizFactory();
    }

    public void attach(FragmentMusicListView fragmentMusicListView,String id,Context context) {
        this.fragmentMusicListView = fragmentMusicListView;
        this.id=id;
        this.context = context;
    }

    public void getMusicLists() {

        MusicListBiz musicListBiz = factory.createBiz(MusicListBiz.class);
        mSubscriptions.add(musicListBiz.getMyMusic(page,id.equals(BmobUser.getCurrentUser(context, MoiUser.class).getObjectId())).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(musicLists -> {
                    if (page == 1) {
                        fragmentMusicListView.hideSwipe(false);
                        musicListViewAdapter = new MusicListViewAdapter(musicLists);
                        fragmentMusicListView.setAdapter(musicListViewAdapter);

                    } else if (musicListViewAdapter != null) {
                        musicListViewAdapter.addData(musicLists);
                    }
                    fragmentMusicListView.hideSwipe(true);
                    page++;
                }, throwable -> {
                    fragmentMusicListView.showSnackBar(throwable.getMessage());
                    fragmentMusicListView.hideSwipe(true);
                }));
    }

    public void setPage(int page) {
        this.page = page;
    }

}

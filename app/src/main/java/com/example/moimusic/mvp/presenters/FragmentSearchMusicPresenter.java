package com.example.moimusic.mvp.presenters;

import android.content.Context;

import com.example.moimusic.adapter.SearchMusicAdapter;
import com.example.moimusic.factorys.DataBizFactory;
import com.example.moimusic.factorys.Factory;
import com.example.moimusic.mvp.model.ApiService;
import com.example.moimusic.mvp.model.biz.MusicBiz;
import com.example.moimusic.mvp.model.entity.EvenSearchCall;
import com.example.moimusic.mvp.views.FragmentSearchMusicView;

import de.greenrobot.event.EventBus;

/**
 * Created by 康颢曦 on 2016/4/4.
 */
public class FragmentSearchMusicPresenter extends BasePresenterImpl {

    private SearchMusicAdapter adapter;
    private Factory factory;
    private Context context;
    private FragmentSearchMusicView view;
    private String s;
    private int page  =1;
    public FragmentSearchMusicPresenter(ApiService apiService) {
    }
    public void attach(Context context,FragmentSearchMusicView view){
        EventBus.getDefault().register(this);
        this.context = context;
        this.view = view;
        factory = new DataBizFactory();
    }
    public void getSearchMusic(){
        MusicBiz biz = factory.createBiz(MusicBiz.class);
        mSubscriptions.add(biz.searchMusic(s,page).subscribe(musics -> {
            if (page==1){
                adapter = new SearchMusicAdapter(musics,1,s);
                view.showList(adapter);
            }else {
                adapter.addAndUpData(musics);
            }
            page++;
            view.showAndHideSwip(false);
        },throwable -> {
            view.showSnackbar(throwable.getMessage());
            view.showAndHideSwip(false);
        }));
    }
    public void onEventMainThread(EvenSearchCall call) {
        if (call.getSearchString()!=null&&call.getSearchString()!=""){
            view.showAndHideSwip(true);
            page=1;
            s = call.getSearchString();
            getSearchMusic();
        }

    }
    public void setString(String str){
        s=str;
    }
}

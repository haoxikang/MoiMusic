package com.example.moimusic.mvp.presenters;

import android.content.Context;
import android.content.Intent;

import com.example.moimusic.factorys.DataBizFactory;
import com.example.moimusic.factorys.Factory;
import com.example.moimusic.mvp.model.ApiService;
import com.example.moimusic.mvp.model.biz.MusicListBiz;
import com.example.moimusic.mvp.model.entity.EvenActivityMusicListCall;
import com.example.moimusic.mvp.model.entity.EvenCall;
import com.example.moimusic.mvp.model.entity.EvenMusicListContentAdapterCall;
import com.example.moimusic.mvp.model.entity.Music;
import com.example.moimusic.mvp.model.entity.MusicList;
import com.example.moimusic.mvp.views.IMainView;
import com.example.moimusic.mvp.views.IMusicListView;
import com.example.moimusic.play.PlayListSingleton;
import com.example.moimusic.ui.activity.UserCenterActivity;

import java.util.List;

import de.greenrobot.event.EventBus;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by qqq34 on 2016/2/23.
 */
public class ActivityMusicListPresenter extends BasePresenterImpl {
    private IMusicListView iMusicListView;
    private Context context;
    private Factory factory;
    private String id;
    private List<Music> musicList ;
    private MusicList List;
    public ActivityMusicListPresenter(ApiService apiService) {
        factory = new DataBizFactory();
    }
    public void attach(IMusicListView iMusicListView, Context context){
        EventBus.getDefault().register(this);
        this.iMusicListView = iMusicListView;
        this.context  =context;
        id=iMusicListView.GetIntent().getStringExtra("musiclistid");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        MusicListBiz musicListBiz = factory.createBiz(MusicListBiz.class);
        if (id!=null&&!id.equals("")){
            mSubscriptions.add( musicListBiz.getMusicList(id).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
            .subscribe(musicList -> {
                List = musicList;
                iMusicListView.showView(musicList);
            }));
        }

    }
    public String getId(){
        return id;
    }
    public void finishActivity(){
        iMusicListView.finish();
    }
    public void onEventMainThread(EvenActivityMusicListCall evenActivityMusicListCall){
       iMusicListView.SetPlayButtonEnable(evenActivityMusicListCall.isEnable());
        musicList=evenActivityMusicListCall.getMusicList();
    }
    public void floatClick(){
        PlayListSingleton.INSTANCE.setMusicList(musicList);
        PlayListSingleton.INSTANCE.setCurrentPosition(0);
        EvenCall evenCall = new EvenCall();
        evenCall.setCurrentOrder(EvenCall.PLAY);
        EventBus.getDefault().post(evenCall);
        EvenMusicListContentAdapterCall call = new EvenMusicListContentAdapterCall();
        EventBus.getDefault().post(call);
    }
    public void ImageViewClicked(){
        Intent intent = new Intent(context, UserCenterActivity.class);
        intent.putExtra("userID",List.getMoiUser().getObjectId());
        iMusicListView.startActivity(intent);
    }
}

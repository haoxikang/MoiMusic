package com.example.moimusic.mvp.presenters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.MenuItem;

import com.example.moimusic.R;
import com.example.moimusic.adapter.MusicListContentViewAdapter;
import com.example.moimusic.factorys.DataBizFactory;
import com.example.moimusic.factorys.Factory;
import com.example.moimusic.mvp.model.ApiService;
import com.example.moimusic.mvp.model.biz.IncreaseBiz;
import com.example.moimusic.mvp.model.biz.MusicBiz;
import com.example.moimusic.mvp.model.biz.MusicListBiz;
import com.example.moimusic.mvp.model.biz.UserBiz;
import com.example.moimusic.mvp.model.entity.EvenActivityMusicListCall;
import com.example.moimusic.mvp.model.entity.EvenCall;
import com.example.moimusic.mvp.model.entity.EvenMusicListContentAdapterCall;
import com.example.moimusic.mvp.model.entity.EvenMusicListEditCall;
import com.example.moimusic.mvp.model.entity.MoiUser;
import com.example.moimusic.mvp.model.entity.Music;
import com.example.moimusic.mvp.model.entity.MusicList;
import com.example.moimusic.mvp.views.IMainView;
import com.example.moimusic.mvp.views.IMusicListView;
import com.example.moimusic.play.PlayListSingleton;
import com.example.moimusic.ui.activity.ActivityEditMusicList;
import com.example.moimusic.ui.activity.ActivityPlayNow;
import com.example.moimusic.ui.activity.UserCenterActivity;
import com.rey.material.app.Dialog;

import java.util.List;

import cn.bmob.v3.BmobUser;
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
    private List<Music> musicList;
    private MusicList List;
    private boolean isCurrentUser;
    private boolean isAnimal;
    private boolean isReleas;

    public ActivityMusicListPresenter(ApiService apiService) {
        factory = new DataBizFactory();
    }

    public void attach(IMusicListView iMusicListView, Context context) {
        EventBus.getDefault().register(this);
        this.iMusicListView = iMusicListView;
        this.context = context;
        id = iMusicListView.GetIntent().getStringExtra("musiclistid");

    }

    @Override
    public void onCreate() {
        super.onCreate();
        MusicListBiz musicListBiz = factory.createBiz(MusicListBiz.class);
        MusicBiz musicBiz = factory.createBiz(MusicBiz.class);
        if (id != null && !id.equals("")) {
            mSubscriptions.add(musicListBiz.getMusicList(id)
                    .subscribe(musicList -> {
                        List = musicList;
                        iMusicListView.showView(musicList);
                        isAnimal = musicList.isAnimal();
                        isReleas = musicList.isRelease();
                        if (musicList.getMoiUser().getObjectId().equals(BmobUser.getCurrentUser(context, MoiUser.class).getObjectId())) {
                            isCurrentUser = true;
                        }
                    }, throwable1 -> {
                        iMusicListView.ShowSnackBar(throwable1.getMessage());
                    }));
            mSubscriptions.add(musicBiz.getMusic(id)
                    .subscribe(musicList -> {
                        iMusicListView.setupViewPager(musicList);
                        IncreaseBiz.musicListIncreace(id);
                        EvenActivityMusicListCall evenActivityMusicListCall = new EvenActivityMusicListCall(true, musicList);
                        EventBus.getDefault().post(evenActivityMusicListCall);
                    }, throwable -> {
                        iMusicListView.ShowSnackBar(throwable.getMessage());
                    }));
        }

    }

    public String getId() {
        return id;
    }

    public void finishActivity() {
        iMusicListView.finish();
    }

    public void onEventMainThread(EvenActivityMusicListCall evenActivityMusicListCall) {
        iMusicListView.SetPlayButtonEnable(evenActivityMusicListCall.isEnable());
        musicList = evenActivityMusicListCall.getMusicList();
    }

    public void onEventMainThread(EvenMusicListEditCall evenMusicListEditCall) {
        iMusicListView.UpdataImageAndName(evenMusicListEditCall.getMusicList());
        iMusicListView.ShowSnackBar("歌单信息已经更新");
    }

    public void floatClick() {
        PlayListSingleton.INSTANCE.setMusicList(musicList);
        PlayListSingleton.INSTANCE.setCurrentPosition(0);
        EvenCall evenCall = new EvenCall();
        evenCall.setCurrentOrder(EvenCall.PLAY);
        EventBus.getDefault().post(evenCall);
        EvenMusicListContentAdapterCall call = new EvenMusicListContentAdapterCall();
        EventBus.getDefault().post(call);
        iMusicListView.startActivity(new Intent(context, ActivityPlayNow.class));
    }

    public void ImageViewClicked() {
        if (List!=null){
            Intent intent = new Intent(context, UserCenterActivity.class);
            intent.putExtra("userID", List.getMoiUser().getObjectId());
            iMusicListView.startActivity(intent);
        }

    }

    public void menuClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.release: {
                if (!isAnimal && isCurrentUser && !isReleas) {
                    MusicListBiz musicListBiz = factory.createBiz(MusicListBiz.class);
                    Dialog dialog = new Dialog(context);
                    dialog.setTitle("正在发布歌单");
                    dialog.cancelable(false);
                    dialog.show();
                    mSubscriptions.add(musicListBiz.ReleaceList(id)
                            .subscribe(musicList1 -> {
                                dialog.dismiss();
                                iMusicListView.ShowSnackBar("歌单发布成功");
                            },throwable -> {
                                iMusicListView.ShowSnackBar(throwable.getMessage());
                            }));
                } else {
                    Log.d("菜单", isAnimal + "    " + isCurrentUser + "     " + isReleas);
                    iMusicListView.ShowSnackBar(context.getResources().getString(R.string.thisIsListYouNotRelease));
                }
                break;
            }
            case R.id.edit: {
                if (!isAnimal && isCurrentUser) {
                    Log.d("TAG", "答应id" + id);
                    Intent intent = new Intent(context, ActivityEditMusicList.class);
                    intent.putExtra("editlistid", id);
                    context.startActivity(intent);
                } else {
                    iMusicListView.ShowSnackBar(context.getResources().getString(R.string.thisIsListYouNotEdit));
                }
                break;
            }
            case R.id.favorite: {
                UserBiz userBiz = factory.createBiz(UserBiz.class);
                if (!isCurrentUser) {
                    if (isAnimal) {
                        
                    } else {
                        mSubscriptions.add(userBiz.CollegeList(id)
                                .subscribe(moiUser -> {
                                    iMusicListView.ShowSnackBar(context.getResources().getString(R.string.CollegeSuccess));
                                }, throwable -> {
                                    iMusicListView.ShowSnackBar(throwable.getMessage());
                                }));
                    }
                } else {
                    iMusicListView.ShowSnackBar(context.getResources().getString(R.string.ownListNotCollege));
                }

                break;
            }

        }
    }
}

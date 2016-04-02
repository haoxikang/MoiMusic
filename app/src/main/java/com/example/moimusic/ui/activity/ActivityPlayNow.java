package com.example.moimusic.ui.activity;


import android.net.Uri;
import android.os.Bundle;

import android.os.SystemClock;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;


import com.balysv.materialripple.MaterialRippleLayout;
import com.example.moimusic.R;
import com.example.moimusic.factorys.DataBizFactory;
import com.example.moimusic.mvp.model.biz.MusicReplysBiz;
import com.example.moimusic.mvp.model.entity.Music;
import com.example.moimusic.mvp.presenters.ActivityPlayNowPresenter;
import com.example.moimusic.mvp.views.IActivityPlayNowView;
import com.example.moimusic.play.PlayListSingleton;
import com.example.moimusic.reject.components.AppComponent;
import com.example.moimusic.reject.components.DaggerActivityMusicListComponent;
import com.example.moimusic.reject.components.DaggerActivityPlayNowComponent;
import com.example.moimusic.reject.models.ActivityMusicListModule;
import com.example.moimusic.reject.models.ActivityPlayNowModule;
import com.example.moimusic.ui.fragment.FragmentMuiscListReplys;
import com.example.moimusic.ui.fragment.MusicListContentFragment;
import com.example.moimusic.utils.Utils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.github.clans.fab.FloatingActionButton;
import com.rey.material.widget.Slider;

import javax.inject.Inject;


/**
 * Created by qqq34 on 2016/2/26.
 */
public class ActivityPlayNow extends BaseActivity implements IActivityPlayNowView{
    private Toolbar mToolbar;
    private ImageView musicListView, musicReplys;
    private SimpleDraweeView simpleDraweeView;
    private ViewPager viewPager;
    private MaterialRippleLayout rippleLoop,ripplePre,rippleNext,rippleMix,rippleList,rippleReplys,rippleMore;
    private TextView Name,Singer,nowText,allText;
    private FloatingActionButton playButton;
    private Slider slider;
    private boolean isTouch=false;
    private int now;
@Inject
    ActivityPlayNowPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("TAG", "加载");
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_play_now);
        presenter.attach(this,this);
        initView();
        initClick();
    }

    void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("");
        mToolbar.setNavigationIcon(R.mipmap.ic_arrow_back_white_24dp);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Name = (TextView)findViewById(R.id.musicName);
        Singer = (TextView)findViewById(R.id.musicSinger);
        Name.setText(PlayListSingleton.INSTANCE.getCurrent().getMusicName());
        Singer.setText(PlayListSingleton.INSTANCE.getCurrent().getSinger());
        rippleMore= (MaterialRippleLayout)findViewById(R.id.more);
        rippleLoop = (MaterialRippleLayout)findViewById(R.id.rippleLoop);
        ripplePre = (MaterialRippleLayout)findViewById(R.id.ripplePre);
        rippleNext = (MaterialRippleLayout)findViewById(R.id.rippleNext);
        rippleList = (MaterialRippleLayout)findViewById(R.id.rippleList);
        rippleReplys = (MaterialRippleLayout)findViewById(R.id.rippleReplys);
        rippleMix = (MaterialRippleLayout)findViewById(R.id.rippleMix);
        musicReplys = (ImageView) findViewById(R.id.musicReplys);
        musicListView = (ImageView) findViewById(R.id.musiclist);
        simpleDraweeView = (SimpleDraweeView) findViewById(R.id.coverImage);
        playButton = (FloatingActionButton)findViewById(R.id.play_fab);
        nowText = (TextView)findViewById(R.id.nowText);
        allText = (TextView)findViewById(R.id.allText);
        slider = (Slider)findViewById(R.id.slider);
        if (PlayListSingleton.INSTANCE.getCurrent().getMusicImageUri()!=null) {
            Uri uri = Uri.parse(PlayListSingleton.INSTANCE.getCurrent().getMusicImageUri());
            Utils.reSizeImage(500,500,uri,simpleDraweeView);
        }
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        FragmentManager fm = getSupportFragmentManager();
        viewPager.setAdapter(new FragmentPagerAdapter(fm) {
            @Override
            public Fragment getItem(int position) {
                if (position == 0) {
                    return MusicListContentFragment.newInstance(PlayListSingleton.INSTANCE.getMusicList(), "ActivityPlayNow");
                } else if (position == 1) {
                    return FragmentMuiscListReplys.newInstance(new DataBizFactory().createBiz(MusicReplysBiz.class), PlayListSingleton.INSTANCE.getCurrentMusicId(),false);
                } else {
                    return null;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        });
        if (PlayListSingleton.INSTANCE.getAll()!=0){
            allText.setText(Utils.getTime(PlayListSingleton.INSTANCE.getAll()));
            nowText.setText(Utils.getTime(PlayListSingleton.INSTANCE.getNow()));
            slider.setValue(PlayListSingleton.INSTANCE.getNow()*100/PlayListSingleton.INSTANCE.getAll(),true);
        }

    }

    public void initClick() {
        mToolbar.setNavigationOnClickListener(v1 -> finish());
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    musicListView.setColorFilter(getResources().getColor(R.color.colorAccent));
                    musicReplys.setColorFilter(getResources().getColor(R.color.black));
                } else {
                    musicListView.setColorFilter(getResources().getColor(R.color.black));
                    musicReplys.setColorFilter(getResources().getColor(R.color.colorAccent));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        rippleList.setOnClickListener(v -> viewPager.setCurrentItem(0));
        rippleReplys.setOnClickListener(v -> viewPager.setCurrentItem(1));
        playButton.setOnClickListener(v -> presenter.Play());
        rippleNext.setOnClickListener(v -> presenter.Next());
        ripplePre.setOnClickListener(v -> presenter.Pre());
        slider.setOnPositionChangeListener((view, fromUser, oldPos, newPos, oldValue, newValue) -> {
            if (fromUser){
               nowText.setText(presenter.getnowTime(newValue));
                now=newValue;
                isTouch=true;
            }

        });
        slider.setOnTouchListener((v, event) -> {
            if(event.getAction()==MotionEvent.ACTION_UP){
                isTouch=false;
                presenter.seekTo(now);
            }
            return false;
        });
        rippleMore.setOnClickListener(v -> presenter.showMore());
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerActivityPlayNowComponent.builder()
                .appComponent(appComponent)
                .activityPlayNowModule(new ActivityPlayNowModule(this))
                .build()
                //这里的inject 会把 MainActivity 所有标注了注解的成员 给动态实例化了
                .inject(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter!=null){
            presenter.onDestroy();
        }
    }

    @Override
    public void setPlayButton(boolean ispause) {
        if (ispause) {
            playButton .setImageResource(R.mipmap.ic_pause_white_36dp);
        } else {

        }
    }

    @Override
    public void updataPlayView() {
        Music music = PlayListSingleton.INSTANCE.getCurrent();
        if (music.getMusicImageUri() != null && !music.getMusicImageUri().equals("")) {
            simpleDraweeView.setImageURI(Uri.parse(music.getMusicImageUri()));
        }
        if (music.getMusicName() != null) {
            Name.setText(music.getMusicName());
        }
        if (music.getSinger() != null) {
            Singer.setText(music.getSinger());
        }
        if (PlayListSingleton.INSTANCE.isUnderPlay) {
            playButton.setImageResource(R.mipmap.ic_pause_white_36dp);
        } else {
            playButton.setImageResource(R.mipmap.ic_play_arrow_white_36dp);
        }
    }

    @Override
    public void setProgressbar(int current, int all) {
        if (!isTouch){
            int now=slider.getMaxValue()*current/all;
            if (current==0){
                slider.setValue(now,true);
            }else {
                slider.setValue(now,false);
            }
            if (all!=100){
                allText.setText(Utils.getTime(all));
                nowText.setText(Utils.getTime(current));
            }
        }

    }
}

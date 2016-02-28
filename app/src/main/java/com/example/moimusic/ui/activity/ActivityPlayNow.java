package com.example.moimusic.ui.activity;


import android.os.Bundle;

import android.os.SystemClock;
import android.support.design.widget.CollapsingToolbarLayout;
import android.util.Log;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.ImageView;


import com.example.moimusic.R;
import com.example.moimusic.reject.components.AppComponent;
import com.facebook.drawee.view.SimpleDraweeView;


/**
 * Created by qqq34 on 2016/2/26.
 */
public class ActivityPlayNow extends BaseActivity {
   private CollapsingToolbarLayout collapsingToolbarLayout;
    private ImageView musicListView;
    private SimpleDraweeView simpleDraweeView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_play_now);
        collapsingToolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.collapsing_toolbar);
        musicListView = (ImageView)findViewById(R.id.musiclist);
        simpleDraweeView = (SimpleDraweeView)findViewById(R.id.coverImage);
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }


}

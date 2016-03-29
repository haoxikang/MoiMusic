package com.example.moimusic.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.example.moimusic.R;
import com.example.moimusic.mvp.presenters.ActivityNewTrendsPresenter;
import com.example.moimusic.mvp.views.ActivityNewTrendsView;
import com.example.moimusic.reject.components.AppComponent;
import com.example.moimusic.reject.components.DaggerActivityMusicListComponent;
import com.example.moimusic.reject.components.DaggerActivityNewTrendsComponent;
import com.example.moimusic.reject.models.ActivityMusicListModule;
import com.example.moimusic.reject.models.ActivityNewTrendsModule;
import com.example.moimusic.utils.SoftKeyboardUtil;
import com.facebook.drawee.view.SimpleDraweeView;
import com.rey.material.widget.ProgressView;

import javax.inject.Inject;

/**
 * Created by Administrator on 2016/3/27.
 */
public class ActivityNewTrends extends BaseActivity implements ActivityNewTrendsView {
    private Toolbar mToolbar;
private RelativeLayout relativeLayout,deleteLayout,bkLayout;
    private EditText editText;
    private SimpleDraweeView draweeView;
    private TextView name,singer;
    private ProgressView progressView;
    private MaterialRippleLayout Rimage,Rface;
  private int height;
    private boolean isFrist=true;
    private int KeybardHeight;
    @Inject
    ActivityNewTrendsPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_new_trends);
        initview();
        initClick();
        presenter.attach(this,this);
        presenter.onCreate();
    }

    private void initClick(){
        mToolbar.setNavigationOnClickListener(v -> finish());
         SoftKeyboardUtil.observeSoftKeyboard(this, (softKeybardHeight, visible)->{
             RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(  RelativeLayout.LayoutParams.MATCH_PARENT,  RelativeLayout.LayoutParams.MATCH_PARENT);
             params.addRule(RelativeLayout.BELOW,R.id.shadow);
           if (visible){
               params.height = height-softKeybardHeight+KeybardHeight;
           }else {
              KeybardHeight = softKeybardHeight;
           }
             SoftKeyboardUtil.isKeyboardEven=false;
             relativeLayout.setLayoutParams(params);
             Log.d("键盘",softKeybardHeight+""+visible);
        });
    }
    private void initview() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("新动态");
        mToolbar.setTitleTextColor(Color.WHITE);
        mToolbar.setNavigationIcon(R.mipmap.ic_arrow_back_white_24dp);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        relativeLayout = (RelativeLayout)findViewById(R.id.inputLayout);
        bkLayout = (RelativeLayout)findViewById(R.id.bklayout);
        deleteLayout= (RelativeLayout)findViewById(R.id.delete);
        editText=(EditText) findViewById(R.id.text);
        draweeView=(SimpleDraweeView)findViewById(R.id.image);
        name = (TextView) findViewById(R.id.musicName);
        singer= (TextView)findViewById(R.id.musicSinger);
        progressView=(ProgressView)findViewById(R.id.progress);
        progressView.setVisibility(View.INVISIBLE);
         Rimage = (MaterialRippleLayout)findViewById(R.id.rippleImage);
                 Rface = (MaterialRippleLayout)findViewById(R.id.rippleface);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.check: {
                showSnackbar("点击了勾勾");
                break;
            }


        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerActivityNewTrendsComponent.builder()
                .appComponent(appComponent)
                .activityNewTrendsModule(new ActivityNewTrendsModule(this))
                .build()
                //这里的inject 会把 MainActivity 所有标注了注解的成员 给动态实例化了
                .inject(this);
    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus&&isFrist) {
            height= relativeLayout.getHeight();
            Log.d("键盘",height+"");
            isFrist=false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter!=null){
            presenter.onDestroy();
        }
    }

    @Override
    public void showBlackShare() {
        draweeView.setVisibility(View.INVISIBLE);
        deleteLayout.setVisibility(View.INVISIBLE);
        name.setVisibility(View.INVISIBLE);
        singer.setVisibility(View.INVISIBLE);
        bkLayout.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showMusicShare() {
        draweeView.setVisibility(View.VISIBLE);
        deleteLayout.setVisibility(View.VISIBLE);
        name.setVisibility(View.VISIBLE);
        singer.setVisibility(View.VISIBLE);
        bkLayout.setVisibility(View.VISIBLE);
    }


    @Override
    public void showSnackbar(String s) {
        Snackbar.make(draweeView,s,Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showProgress(boolean isShow) {
            if (isShow){
                progressView.setVisibility(View.VISIBLE);
            }else {
                progressView.setVisibility(View.INVISIBLE);
            }
    }

    @Override
    public String getEditText() {
       return editText.getText().toString();
    }
}

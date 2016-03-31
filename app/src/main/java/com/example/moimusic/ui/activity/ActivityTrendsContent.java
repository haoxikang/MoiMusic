package com.example.moimusic.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.moimusic.R;
import com.example.moimusic.mvp.model.entity.MoiUser;
import com.example.moimusic.mvp.model.entity.Trends;
import com.example.moimusic.mvp.presenters.ActivityTrendsContentPresenter;
import com.example.moimusic.mvp.views.ActivityTrendsContentView;
import com.example.moimusic.reject.components.ActivityTrendsContentComponent;
import com.example.moimusic.reject.components.AppComponent;
import com.example.moimusic.reject.components.DaggerActivityNewTrendsComponent;
import com.example.moimusic.reject.components.DaggerActivityTrendsContentComponent;
import com.example.moimusic.reject.models.ActivityNewTrendsModule;
import com.example.moimusic.reject.models.ActivityTrendsContentModule;
import com.example.moimusic.utils.DensityUtil;
import com.example.moimusic.utils.SoftKeyboardUtil;
import com.facebook.drawee.view.SimpleDraweeView;
import com.vanniktech.emoji.EmojiEditText;
import com.vanniktech.emoji.EmojiPopup;
import com.vanniktech.emoji.EmojiTextView;
import com.vanniktech.emoji.emoji.Emoji;
import com.vanniktech.emoji.listeners.OnEmojiBackspaceClickListener;
import com.vanniktech.emoji.listeners.OnEmojiClickedListener;
import com.vanniktech.emoji.listeners.OnEmojiPopupDismissListener;
import com.vanniktech.emoji.listeners.OnEmojiPopupShownListener;
import com.vanniktech.emoji.listeners.OnSoftKeyboardCloseListener;
import com.vanniktech.emoji.listeners.OnSoftKeyboardOpenListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import cn.bmob.v3.BmobUser;

/**
 * Created by Administrator on 2016/3/31.
 */
public class ActivityTrendsContent extends BaseActivity implements ActivityTrendsContentView {
    private RecyclerView recyclerView;
    private EmojiEditText editText;
    private ImageView imageView;
    private EmojiPopup emojiPopup;
    private ViewGroup     rootView;
    private RelativeLayout layout;
    private int KeybardHeight;
    protected TextView name, time, replysNumber;
    protected EmojiTextView content;
    protected SimpleDraweeView userImage;
    protected LinearLayout linearLayout;
    private TextView ListName, ListSinger;
    private SimpleDraweeView ListImage;
    private RelativeLayout areaMusic;
    @Inject
    ActivityTrendsContentPresenter presenter;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_trends_content_layout);
        initview();
        initClick();
        presenter.attach(this,this);
        presenter.onCreate();

    }
    private void initClick(){
        userImage.setOnClickListener(v1 -> presenter.onUserImageClick());
        imageView.setOnClickListener(v -> emojiPopup.toggle());
        SoftKeyboardUtil.observeSoftKeyboard(this, (softKeybardHeight, visible) -> {
            CoordinatorLayout.LayoutParams params = new CoordinatorLayout.LayoutParams(CoordinatorLayout.LayoutParams.MATCH_PARENT, DensityUtil.dip2px(this,48));
            params.gravity= Gravity.BOTTOM;
            if (visible) {

                params.bottomMargin=  softKeybardHeight - KeybardHeight;
            } else {
                KeybardHeight = softKeybardHeight;
            }
            SoftKeyboardUtil.isKeyboardEven = false;
            layout.setLayoutParams(params);
            Log.d("键盘", softKeybardHeight + "" + visible);
        });
    }
    private void initview(){
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        editText = (EmojiEditText)findViewById(R.id.edit);
        imageView = (ImageView)findViewById(R.id.imageview);
        rootView = (ViewGroup) findViewById(R.id.main_activity_root_view);
        layout = (RelativeLayout)findViewById(R.id.inputview) ;
        areaMusic = (RelativeLayout)findViewById(R.id.areaMusic);
        ListName = (TextView) findViewById(R.id.musicName);
        ListSinger = (TextView)findViewById(R.id.musicSinger);
        ListImage = (SimpleDraweeView) findViewById(R.id.musicImage);
        name = (TextView) findViewById(R.id.name);
        time = (TextView) findViewById(R.id.time);
        content = (EmojiTextView) findViewById(R.id.content);
        replysNumber = (TextView) findViewById(R.id.replysNum);
        userImage = (SimpleDraweeView) findViewById(R.id.userImage);
        linearLayout = (LinearLayout)findViewById(R.id.linearlayout);
        setUpEmojiPopup();
    }
    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerActivityTrendsContentComponent.builder()
                .appComponent(appComponent)
                .activityTrendsContentModule(new ActivityTrendsContentModule(this))
                .build()
                //这里的inject 会把 MainActivity 所有标注了注解的成员 给动态实例化了
                .inject(this);
    }



    private void setUpEmojiPopup() {
        emojiPopup = EmojiPopup.Builder.fromRootView(rootView).setOnEmojiBackspaceClickListener(v ->
                Log.d("MainActivity", "Clicked on Backspace")).setOnEmojiClickedListener(emoji ->
                Log.d("MainActivity", "Clicked on emoji")).setOnEmojiPopupShownListener(() ->
                imageView.setImageResource(R.mipmap.ic_keyboard_grey600_48dp)).setOnSoftKeyboardOpenListener(keyBoardHeight ->
                Log.d("MainActivity", "Opened soft keyboard")).setOnEmojiPopupDismissListener(() ->
                imageView.setImageResource(R.mipmap.ic_tag_faces_grey600_48dp)).setOnSoftKeyboardCloseListener(() ->
                emojiPopup.dismiss()).build(editText);
    }
    @Override
    public void onBackPressed() {
        if (emojiPopup != null && emojiPopup.isShowing()) {
            emojiPopup.dismiss();
        } else {
            super.onBackPressed();
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
    public void ShowView(Trends trends) {
        name.setText(trends.getUserid().getName());
        time.setText(trends.getCreatedAt());
        content.setText(trends.getContent());

        if (trends.getUserid().getImageFile() != null) {
            userImage.setImageURI(Uri.parse(trends.getUserid().getImageFile().getFileUrl(this)));
        }else {
            userImage.setImageURI(Uri.parse("null"));
        }

        if (trends.getReplysNum() != null) {
            replysNumber.setText(trends.getReplysNum() + "");
        } else {
            replysNumber.setText(0 + "");
        }
        ListName.setVisibility(View.GONE);
        ListSinger.setVisibility(View.GONE);
        ListImage.setVisibility(View.GONE);
        areaMusic.setVisibility(View.GONE);
        if (trends.getType().equals("1")){

        }else if (trends.getType().equals("2")){
            ListImage.setVisibility(View.VISIBLE);
            ListImage.setImageURI(Uri.parse(trends.getImage().getFileUrl(this)));
        }else if (trends.getType().equals("3")){
            ListName.setVisibility(View.VISIBLE);
            ListSinger.setVisibility(View.VISIBLE);
            ListImage.setVisibility(View.VISIBLE);
            areaMusic.setVisibility(View.VISIBLE);
            ListImage.setImageURI(Uri.parse(trends.getSongid().getMusicImageUri()));
            ListName.setText(trends.getSongid().getMusicName());
            ListSinger.setText(trends.getSongid().getSinger());
        }else if (trends.getType().equals("4")){
            ListName.setVisibility(View.VISIBLE);
            ListSinger.setVisibility(View.VISIBLE);
            ListImage.setVisibility(View.VISIBLE);
            areaMusic.setVisibility(View.VISIBLE);
            if (trends.getListid().getListImageUri() != null) {
              ListImage.setImageURI(Uri.parse(trends.getListid().getListImageUri()));
            }

      ListName.setText(trends.getListid().getName());
     ListSinger.setText(trends.getListid().getMoiUser().getName());
        }
    }


}

package com.example.moimusic.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.moimusic.R;
import com.example.moimusic.reject.components.AppComponent;

/**
 * Created by qqq34 on 2016/2/4.
 */
public class FragmentMusicList extends BaseFragment {
    public  static final  String EXTRA_USER_ID ="com.example.moimusic.Userid";
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String id =getArguments().getString(EXTRA_USER_ID);
        Log.d("TAG","传入fragment的id是："+id);
    }

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragme_music_list, container, false);
        return v;
    }
    public static FragmentMusicList newInstance(String id){
        Bundle args = new Bundle();
        args.putString(EXTRA_USER_ID,id);
        FragmentMusicList fragmentMusicList = new FragmentMusicList();
        fragmentMusicList.setArguments(args);
        return fragmentMusicList;
    }
}

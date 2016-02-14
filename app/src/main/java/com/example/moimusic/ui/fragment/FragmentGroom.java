package com.example.moimusic.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.moimusic.R;
import com.example.moimusic.mvp.model.entity.EvenCall;

import de.greenrobot.event.EventBus;

/**
 * Created by qqq34 on 2016/1/30.
 */
public class FragmentGroom extends Fragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_groom, container, false);
        v.findViewById(R.id.button).setOnClickListener(v1 -> {
            EventBus.getDefault().post(0);
        });
        return v;
    }
}

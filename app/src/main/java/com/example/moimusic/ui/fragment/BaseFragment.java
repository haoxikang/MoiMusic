package com.example.moimusic.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.example.moimusic.AppApplication;
import com.example.moimusic.reject.components.AppComponent;

/**
 * Created by qqq34 on 2016/2/16.
 */
public abstract class BaseFragment extends Fragment{
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupFragmentComponent(AppApplication.get(getActivity()).getAppComponent());
    }

    protected abstract  void setupFragmentComponent(AppComponent appComponent);
}

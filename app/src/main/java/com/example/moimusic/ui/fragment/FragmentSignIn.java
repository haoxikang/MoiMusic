package com.example.moimusic.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.moimusic.R;
import com.example.moimusic.mvp.presenters.FragmentSignInPresenter;
import com.example.moimusic.reject.components.AppComponent;
import com.example.moimusic.reject.components.DaggerFragmentSignInComponent;
import com.example.moimusic.reject.models.FragmentSignInModule;

import javax.inject.Inject;

/**
 * Created by qqq34 on 2016/2/14.
 */
public class FragmentSignIn extends BaseFragment {
    @Inject
    FragmentSignInPresenter presenter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {
        DaggerFragmentSignInComponent.builder()
                .appComponent(appComponent)
                .fragmentSignInModule(new FragmentSignInModule(this))
                .build()
                .inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_sign_in, container, false);
        return v;
    }
}

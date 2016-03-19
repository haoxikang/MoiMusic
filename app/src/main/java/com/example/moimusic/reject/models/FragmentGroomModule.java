package com.example.moimusic.reject.models;

import com.example.moimusic.mvp.model.ApiService;
import com.example.moimusic.mvp.presenters.FragmentFavouriteMusicListPresenter;
import com.example.moimusic.mvp.presenters.FragmentGroomPresenter;
import com.example.moimusic.reject.ActivityScope;
import com.example.moimusic.ui.fragment.FragmentFavouriteMusicList;
import com.example.moimusic.ui.fragment.FragmentGroom;

import dagger.Module;
import dagger.Provides;

/**
 * Created by qqq34 on 2016/3/17.
 */@Module
public class FragmentGroomModule {
    private FragmentGroom fragmentGroom;

    public FragmentGroomModule(FragmentGroom fragmentGroom) {
        this.fragmentGroom = fragmentGroom;
    }
    @Provides
    @ActivityScope
    FragmentGroom provideFragmentGroom() {
        return fragmentGroom;
    }
    @Provides
    @ ActivityScope
    FragmentGroomPresenter provideFragmentGroomPresenter(ApiService apiService) {
        return new FragmentGroomPresenter( apiService);
    }
}

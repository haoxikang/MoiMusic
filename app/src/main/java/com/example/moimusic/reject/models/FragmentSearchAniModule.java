package com.example.moimusic.reject.models;

import com.example.moimusic.mvp.model.ApiService;
import com.example.moimusic.mvp.presenters.FragmentSearchAniPresenter;
import com.example.moimusic.reject.ActivityScope;
import com.example.moimusic.ui.fragment.FragmentSearchAni;


import dagger.Module;
import dagger.Provides;

/**
 * Created by 康颢曦 on 2016/4/4.
 */
@Module
public class FragmentSearchAniModule {
    private FragmentSearchAni fragmentSearchAni;

    public FragmentSearchAniModule(FragmentSearchAni fragmentSearchAni) {
        this.fragmentSearchAni = fragmentSearchAni;
    }
    @Provides
    @ActivityScope
    FragmentSearchAni provideFragmentSearchAni() {
        return fragmentSearchAni;
    }
    @Provides
    @ ActivityScope
    FragmentSearchAniPresenter provideFragmentSearchAniPresenter(ApiService apiService) {
        return new FragmentSearchAniPresenter( apiService);
    }
}

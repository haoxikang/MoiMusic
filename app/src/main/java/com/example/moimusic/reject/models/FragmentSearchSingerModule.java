package com.example.moimusic.reject.models;

import com.example.moimusic.mvp.model.ApiService;
import com.example.moimusic.mvp.presenters.FragmentSearchSingerPresenter;
import com.example.moimusic.reject.ActivityScope;
import com.example.moimusic.ui.fragment.FragmentSearchSinger;

import dagger.Module;
import dagger.Provides;

/**
 * Created by 康颢曦 on 2016/4/4.
 */
@Module
public class FragmentSearchSingerModule {
    private FragmentSearchSinger fragmentSearchSinger;

    public FragmentSearchSingerModule(FragmentSearchSinger fragmentSearchSinger) {
        this.fragmentSearchSinger = fragmentSearchSinger;
    }
    @Provides
    @ActivityScope
    FragmentSearchSinger provideFragmentSearchSinger() {
        return fragmentSearchSinger;
    }
    @Provides
    @ ActivityScope
    FragmentSearchSingerPresenter provideFragmentSearchSingerPresenter(ApiService apiService) {
        return new FragmentSearchSingerPresenter( apiService);
    }
}

package com.example.moimusic.reject.models;

import com.example.moimusic.mvp.model.ApiService;
import com.example.moimusic.mvp.presenters.FragmentSearchMusicPresenter;
import com.example.moimusic.reject.ActivityScope;
import com.example.moimusic.ui.fragment.FragmentSearchMusic;

import dagger.Module;
import dagger.Provides;

/**
 * Created by 康颢曦 on 2016/4/4.
 */
@Module
public class FragmentSearchMusicModule {
    private FragmentSearchMusic fragmentSearchMusic;

    public FragmentSearchMusicModule(FragmentSearchMusic fragmentSearchMusic) {
        this.fragmentSearchMusic = fragmentSearchMusic;
    }
    @Provides
    @ActivityScope
    FragmentSearchMusic provideFragmentSearchMusic() {
        return fragmentSearchMusic;
    }
    @Provides
    @ ActivityScope
    FragmentSearchMusicPresenter provideFragmentSearchMusicPresenter(ApiService apiService) {
        return new FragmentSearchMusicPresenter( apiService);
    }
}

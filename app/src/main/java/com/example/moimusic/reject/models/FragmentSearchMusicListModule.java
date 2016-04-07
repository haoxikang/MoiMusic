package com.example.moimusic.reject.models;

import com.example.moimusic.mvp.model.ApiService;
import com.example.moimusic.mvp.presenters.FragmentSearchMusicListPresenter;
import com.example.moimusic.mvp.presenters.FragmentSearchMusicPresenter;
import com.example.moimusic.reject.ActivityScope;
import com.example.moimusic.ui.fragment.FragmentSearchMusic;
import com.example.moimusic.ui.fragment.FragmentSearchMusicList;

import dagger.Module;
import dagger.Provides;

/**
 * Created by 康颢曦 on 2016/4/7.
 */
@Module
public class FragmentSearchMusicListModule {
    private FragmentSearchMusicList fragmentSearchMusicList;

    public FragmentSearchMusicListModule(FragmentSearchMusicList fragmentSearchMusicList) {
        this.fragmentSearchMusicList = fragmentSearchMusicList;
    }
    @Provides
    @ActivityScope
    FragmentSearchMusicList provideFragmentSearchMusicList() {
        return fragmentSearchMusicList;
    }
    @Provides
    @ ActivityScope
    FragmentSearchMusicListPresenter provideFragmentSearchMusicListPresenter(ApiService apiService) {
        return new FragmentSearchMusicListPresenter( apiService);
    }
}

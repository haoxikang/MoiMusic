package com.example.moimusic.reject.models;

import com.example.moimusic.mvp.model.ApiService;
import com.example.moimusic.mvp.presenters.FragmentSignUpPresenter;
import com.example.moimusic.mvp.presenters.MusicListContentFragmentPresenter;
import com.example.moimusic.reject.ActivityScope;
import com.example.moimusic.ui.fragment.FragmentSignUp;
import com.example.moimusic.ui.fragment.MusicListContentFragment;

import dagger.Module;
import dagger.Provides;

/**
 * Created by qqq34 on 2016/2/24.
 */
@Module
public class MusicListContentFragmentModule {
    private  MusicListContentFragment musicListContentFragment;

    public  MusicListContentFragmentModule( MusicListContentFragment musicListContentFragment) {
        this.musicListContentFragment = musicListContentFragment;
    }
    @Provides
    @ActivityScope
    MusicListContentFragment provideMusicListContentFragment(){
        return musicListContentFragment;
    }
    @Provides
    @ ActivityScope
    MusicListContentFragmentPresenter provideMusicListContentFragmentPresenter(ApiService apiService) {
        return new MusicListContentFragmentPresenter( apiService);
    }
}

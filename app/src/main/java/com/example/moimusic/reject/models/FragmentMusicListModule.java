package com.example.moimusic.reject.models;

import com.example.moimusic.mvp.model.ApiService;
import com.example.moimusic.mvp.presenters.FragmentMusicListPresenter;
import com.example.moimusic.mvp.presenters.FragmentSignInPresenter;
import com.example.moimusic.reject.ActivityScope;
import com.example.moimusic.ui.fragment.FragmentMusicList;
import com.example.moimusic.ui.fragment.FragmentSignIn;

import dagger.Module;
import dagger.Provides;

/**
 * Created by qqq34 on 2016/2/20.
 */
@Module
public class FragmentMusicListModule {
    private FragmentMusicList fragmentMusicList;

    public FragmentMusicListModule(FragmentMusicList fragmentMusicList) {
        this.fragmentMusicList = fragmentMusicList;
    }
    @Provides
    @ActivityScope
    FragmentMusicList provideFragmentMusicList() {
        return fragmentMusicList;
    }
    @Provides
    @ ActivityScope
    FragmentMusicListPresenter provideFragmentMusicListPresenter(ApiService apiService) {
        return new FragmentMusicListPresenter( apiService);
    }
}

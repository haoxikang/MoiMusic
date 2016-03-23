package com.example.moimusic.reject.models;

import com.example.moimusic.mvp.model.ApiService;
import com.example.moimusic.mvp.presenters.FragmentAniMusicPresenter;
import com.example.moimusic.reject.ActivityScope;
import com.example.moimusic.ui.fragment.FragmentAniMusic;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2016/3/23.
 */
@Module
public class FragmentAniMusicModule {
    private FragmentAniMusic fragmentAniMusic;

    public FragmentAniMusicModule(FragmentAniMusic fragmentAniMusic) {
        this.fragmentAniMusic = fragmentAniMusic;
    }
    @Provides
    @ActivityScope
    FragmentAniMusic provideFragmentAniMusic() {
        return fragmentAniMusic;
    }
    @Provides
    @ ActivityScope
    FragmentAniMusicPresenter provideFragmentAniMusicPresenter(ApiService apiService) {
        return new FragmentAniMusicPresenter( apiService);
    }
}

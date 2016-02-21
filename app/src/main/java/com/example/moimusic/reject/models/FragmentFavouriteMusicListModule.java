package com.example.moimusic.reject.models;

import com.example.moimusic.mvp.model.ApiService;
import com.example.moimusic.mvp.presenters.FragmentFavouriteMusicListPresenter;
import com.example.moimusic.reject.ActivityScope;
import com.example.moimusic.ui.fragment.FragmentFavouriteMusicList;

import dagger.Module;
import dagger.Provides;

/**
 * Created by qqq34 on 2016/2/21.
 */
@Module
public class FragmentFavouriteMusicListModule {
    private FragmentFavouriteMusicList fragmentFavouriteMusicList;

    public FragmentFavouriteMusicListModule(FragmentFavouriteMusicList fragmentFavouriteMusicList) {
        this.fragmentFavouriteMusicList = fragmentFavouriteMusicList;
    }
    @Provides
    @ActivityScope
    FragmentFavouriteMusicList provideFragmentFavouriteMusicList() {
        return fragmentFavouriteMusicList;
    }
    @Provides
    @ ActivityScope
    FragmentFavouriteMusicListPresenter provideFragmentFavouriteMusicListPresenter(ApiService apiService) {
        return new FragmentFavouriteMusicListPresenter( apiService);
    }
}

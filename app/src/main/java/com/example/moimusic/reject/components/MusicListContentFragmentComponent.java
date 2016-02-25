package com.example.moimusic.reject.components;

import com.example.moimusic.reject.ActivityScope;
import com.example.moimusic.reject.models.FragmentFavouriteMusicListModule;
import com.example.moimusic.reject.models.MusicListContentFragmentModule;
import com.example.moimusic.ui.fragment.FragmentFavouriteMusicList;
import com.example.moimusic.ui.fragment.MusicListContentFragment;

import dagger.Component;

/**
 * Created by qqq34 on 2016/2/24.
 */
@ActivityScope
@Component(modules =  MusicListContentFragmentModule.class,dependencies = AppComponent.class)
public interface MusicListContentFragmentComponent {
    MusicListContentFragment inject(MusicListContentFragment musicListContentFragment);
}
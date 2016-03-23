package com.example.moimusic.reject.components;

import com.example.moimusic.reject.ActivityScope;
import com.example.moimusic.reject.models.FragmentAniMusicModule;
import com.example.moimusic.reject.models.FragmentFavouriteMusicListModule;
import com.example.moimusic.ui.fragment.FragmentAniMusic;
import com.example.moimusic.ui.fragment.FragmentFavouriteMusicList;

import dagger.Component;

/**
 * Created by Administrator on 2016/3/23.
 */
@ActivityScope
@Component(modules =  FragmentAniMusicModule.class,dependencies = AppComponent.class)
public interface FragmentAniMusicComponent {
    FragmentAniMusic inject(FragmentAniMusic fragmentAniMusic);
}

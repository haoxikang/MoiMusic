package com.example.moimusic.reject.components;

import com.example.moimusic.reject.ActivityScope;
import com.example.moimusic.reject.models.FragmentFavouriteMusicListModule;
import com.example.moimusic.reject.models.FragmentMusicListModule;
import com.example.moimusic.ui.fragment.FragmentFavouriteMusicList;
import com.example.moimusic.ui.fragment.FragmentMusicList;

import dagger.Component;

/**
 * Created by qqq34 on 2016/2/21.
 */
@ActivityScope
@Component(modules =  FragmentFavouriteMusicListModule.class,dependencies = AppComponent.class)
public interface FragmentFavouriteMusicListComponent {
    FragmentFavouriteMusicList inject(FragmentFavouriteMusicList fragmentFavouriteMusicList);
}

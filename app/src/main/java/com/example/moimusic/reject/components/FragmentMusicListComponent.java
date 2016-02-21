package com.example.moimusic.reject.components;

import com.example.moimusic.reject.ActivityScope;
import com.example.moimusic.reject.models.FragmentMusicListModule;
import com.example.moimusic.ui.fragment.FragmentMusicList;

import dagger.Component;

/**
 * Created by qqq34 on 2016/2/20.
 */
@ActivityScope
@Component(modules =  FragmentMusicListModule.class,dependencies = AppComponent.class)
public interface FragmentMusicListComponent {
    FragmentMusicList inject(FragmentMusicList fragmentMusicList);
}

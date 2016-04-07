package com.example.moimusic.reject.components;

import com.example.moimusic.reject.ActivityScope;
import com.example.moimusic.reject.models.FragmentSearchMusicListModule;
import com.example.moimusic.ui.fragment.FragmentSearchMusicList;

import dagger.Component;

/**
 * Created by 康颢曦 on 2016/4/7.
 */
@ActivityScope
@Component(modules = FragmentSearchMusicListModule.class,dependencies = AppComponent.class)
public interface FragmentSearchMusicListComponent {
    FragmentSearchMusicList inject(FragmentSearchMusicList fragmentSearchMusicList);
}

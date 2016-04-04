package com.example.moimusic.reject.components;

import com.example.moimusic.reject.ActivityScope;
import com.example.moimusic.reject.models.FragmentSearchMusicModule;
import com.example.moimusic.ui.fragment.FragmentSearchMusic;

import dagger.Component;

/**
 * Created by 康颢曦 on 2016/4/4.
 */
@ActivityScope
@Component(modules = FragmentSearchMusicModule.class,dependencies = AppComponent.class)
public interface FragmentSearchMusicComponent {
    FragmentSearchMusic inject(FragmentSearchMusic fragmentSearchMusic);
}

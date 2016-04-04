package com.example.moimusic.reject.components;

import com.example.moimusic.reject.ActivityScope;
import com.example.moimusic.reject.models.FragmentSearchSingerModule;
import com.example.moimusic.ui.fragment.FragmentSearchSinger;

import dagger.Component;

/**
 * Created by 康颢曦 on 2016/4/4.
 */
@ActivityScope
@Component(modules = FragmentSearchSingerModule.class,dependencies = AppComponent.class)
public interface FragmentSearchSingerComponent {
    FragmentSearchSinger inject(FragmentSearchSinger fragmentSearchSinger);
}

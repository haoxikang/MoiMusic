package com.example.moimusic.reject.components;

import com.example.moimusic.reject.ActivityScope;
import com.example.moimusic.reject.models.FragmentFavouriteMusicListModule;
import com.example.moimusic.reject.models.FragmentGroomModule;
import com.example.moimusic.ui.fragment.FragmentGroom;
import com.example.moimusic.ui.fragment.FragmentMuiscListReplys;

import dagger.Component;

/**
 * Created by qqq34 on 2016/3/17.
 */
@ActivityScope
@Component(modules =  FragmentGroomModule.class,dependencies = AppComponent.class)
public interface FragmentGroomComponent {
    FragmentGroom inject(FragmentGroom fragmentGroom);
}

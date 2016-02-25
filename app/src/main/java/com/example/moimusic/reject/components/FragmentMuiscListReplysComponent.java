package com.example.moimusic.reject.components;

import com.example.moimusic.reject.ActivityScope;
import com.example.moimusic.reject.models.FragmentFavouriteMusicListModule;
import com.example.moimusic.reject.models.FragmentMuiscListReplysModule;
import com.example.moimusic.ui.fragment.FragmentFavouriteMusicList;
import com.example.moimusic.ui.fragment.FragmentMuiscListReplys;

import dagger.Component;

/**
 * Created by qqq34 on 2016/2/25.
 */
@ActivityScope
@Component(modules =  FragmentMuiscListReplysModule.class,dependencies = AppComponent.class)
public interface FragmentMuiscListReplysComponent {
    FragmentMuiscListReplys inject(FragmentMuiscListReplys fragmentMuiscListReplys);
}

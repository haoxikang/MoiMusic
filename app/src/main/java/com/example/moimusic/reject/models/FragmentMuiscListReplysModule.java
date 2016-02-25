package com.example.moimusic.reject.models;

import com.example.moimusic.mvp.model.ApiService;
import com.example.moimusic.mvp.presenters.FragmentMuiscListReplysPresenter;
import com.example.moimusic.mvp.presenters.MusicListContentFragmentPresenter;
import com.example.moimusic.reject.ActivityScope;
import com.example.moimusic.ui.fragment.FragmentMuiscListReplys;
import com.example.moimusic.ui.fragment.MusicListContentFragment;

import dagger.Module;
import dagger.Provides;

/**
 * Created by qqq34 on 2016/2/25.
 */
@Module
public class FragmentMuiscListReplysModule {
    private FragmentMuiscListReplys fragmentMuiscListReplys;

    public  FragmentMuiscListReplysModule( FragmentMuiscListReplys fragmentMuiscListReplys) {
        this.fragmentMuiscListReplys = fragmentMuiscListReplys;
    }
    @Provides
    @ActivityScope
    FragmentMuiscListReplys provideFragmentMuiscListReplys(){
        return fragmentMuiscListReplys;
    }
    @Provides
    @ ActivityScope
    FragmentMuiscListReplysPresenter provideFragmentMuiscListReplysPresenter(ApiService apiService) {
        return new FragmentMuiscListReplysPresenter( apiService);
    }
}

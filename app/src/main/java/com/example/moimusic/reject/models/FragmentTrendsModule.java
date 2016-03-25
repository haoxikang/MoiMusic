package com.example.moimusic.reject.models;

import com.example.moimusic.mvp.model.ApiService;

import com.example.moimusic.mvp.presenters.FragmentTrendsPresenter;
import com.example.moimusic.reject.ActivityScope;

import com.example.moimusic.ui.fragment.FragmentTrends;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2016/3/25.
 */
@Module
public class FragmentTrendsModule  {
    private FragmentTrends fragmentTrends;

    public FragmentTrendsModule(FragmentTrends fragmentTrends) {
        this.fragmentTrends = fragmentTrends;
    }
    @Provides
    @ActivityScope
    FragmentTrends provideFragmentTrends(){
        return fragmentTrends;
    }
    @Provides
    @ ActivityScope
    FragmentTrendsPresenter provideFragmentTrendsPresenter(ApiService apiService) {
        return new FragmentTrendsPresenter( apiService);
    }
}

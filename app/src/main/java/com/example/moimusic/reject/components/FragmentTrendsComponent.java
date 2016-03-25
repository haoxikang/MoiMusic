package com.example.moimusic.reject.components;

import com.example.moimusic.reject.ActivityScope;
import com.example.moimusic.reject.models.FragmentTrendsModule;
import com.example.moimusic.ui.fragment.FragmentTrends;

import dagger.Component;

/**
 * Created by Administrator on 2016/3/25.
 */
@ActivityScope
@Component(modules = FragmentTrendsModule.class,dependencies = AppComponent.class)
public interface FragmentTrendsComponent {
    FragmentTrends inject(FragmentTrends fragmentTrends);
}

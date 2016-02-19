package com.example.moimusic.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.moimusic.R;
import com.example.moimusic.adapter.FragmentAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qqq34 on 2016/1/30.
 */
public class MainFragment extends Fragment {
    private ViewPager viewPager;
    private TabLayout tabs;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        initView(v);
        return v;
    }
    private void initView(View v){
        viewPager = (ViewPager)v.findViewById(R.id.viewpager);
        tabs = (TabLayout)v. findViewById(R.id.tabs);
        setupViewPager();
        viewPager.setOffscreenPageLimit(3);

    }
    private void setupViewPager() {
        List<String> titles = new ArrayList<>();
        titles.add(getResources().getString(R.string.groom));
        titles.add(getResources().getString(R.string.animation));
        titles.add(getResources().getString(R.string.original));
        titles.add(getResources().getString(R.string.trends));
        tabs.addTab(tabs.newTab().setText(titles.get(0)));
        tabs.addTab(tabs.newTab().setText(titles.get(1)));
        tabs.addTab(tabs.newTab().setText(titles.get(2)));
        tabs.addTab(tabs.newTab().setText(titles.get(3)));
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new FragmentGroom());
        fragments.add(new FragmentAniMusic());
        fragments.add(new FragmentOriginal());
        fragments.add(new FragmentTrends());
        FragmentAdapter adapter =
                new FragmentAdapter(getActivity().getSupportFragmentManager(), fragments, titles);
        viewPager.setAdapter(adapter);
        tabs.setupWithViewPager(viewPager);
        tabs.setTabsFromPagerAdapter(adapter);
    }
}

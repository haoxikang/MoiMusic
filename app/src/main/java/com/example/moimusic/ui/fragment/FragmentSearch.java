package com.example.moimusic.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.moimusic.R;
import com.example.moimusic.adapter.FragmentAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qqq34 on 2016/2/4.
 */
public class FragmentSearch extends Fragment {
    public final static String EXTRA_SEARCH_STRING= "com.moimusic.fragment.search";
    private ViewPager viewPager;
    private TabLayout tabs;
    private String searchStr;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        searchStr = getArguments().getString(EXTRA_SEARCH_STRING);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search, container, false);
        initView(v);
        return v;
    }
    private void initView(View v){
        viewPager = (ViewPager)v.findViewById(R.id.view_pager);
        tabs = (TabLayout)v. findViewById(R.id.tabs);
        setupViewPager();
        viewPager.setOffscreenPageLimit(4);

    }
    private void setupViewPager() {
        List<String> titles = new ArrayList<>();
        titles.add(getResources().getString(R.string.music));
        titles.add(getResources().getString(R.string.animation));


        titles.add(getResources().getString(R.string.singer));
        titles.add(getResources().getString(R.string.music_list));
        titles.add(getResources().getString(R.string.user));

        tabs.addTab(tabs.newTab().setText(titles.get(0)));
        tabs.addTab(tabs.newTab().setText(titles.get(1)));
        tabs.addTab(tabs.newTab().setText(titles.get(2)));
        tabs.addTab(tabs.newTab().setText(titles.get(3)));
        tabs.addTab(tabs.newTab().setText(titles.get(4)));
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(FragmentSearchMusic.newInstance(searchStr));
        fragments.add(FragmentSearchAni.newInstance(searchStr));
        fragments.add(FragmentSearchSinger.newInstance(searchStr));
        fragments.add(new FragmentSearchMusicList());
        fragments.add(new FragmentSearchUser());
        FragmentAdapter adapter =
                new FragmentAdapter(getActivity().getSupportFragmentManager(), fragments, titles);
        viewPager.setAdapter(adapter);
        tabs.setupWithViewPager(viewPager);
    }
    public static   FragmentSearch newInstance(String searchStr){
        Bundle args = new Bundle();
        args.putString(EXTRA_SEARCH_STRING,searchStr);
        FragmentSearch fragmentSearch = new FragmentSearch();
        fragmentSearch.setArguments(args);
        return fragmentSearch;
    }
}

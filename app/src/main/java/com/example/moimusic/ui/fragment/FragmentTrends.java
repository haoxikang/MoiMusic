package com.example.moimusic.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.moimusic.R;
import com.example.moimusic.adapter.TrendsListAdapter;
import com.example.moimusic.mvp.model.entity.Music;
import com.example.moimusic.mvp.model.entity.Trends;
import com.example.moimusic.mvp.presenters.FragmentTrendsPresenter;
import com.example.moimusic.mvp.views.FragmentTrendsView;
import com.example.moimusic.reject.components.AppComponent;
import com.example.moimusic.reject.components.DaggerFragmentSignUpComponent;
import com.example.moimusic.reject.components.DaggerFragmentTrendsComponent;
import com.example.moimusic.reject.models.FragmentSignUpModule;
import com.example.moimusic.reject.models.FragmentTrendsModule;
import com.example.moimusic.ui.customview.DividerItemDecoration;
import com.gc.materialdesign.views.ButtonFlat;
import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by qqq34 on 2016/1/30.
 */
public class FragmentTrends extends BaseFragment implements FragmentTrendsView {
    private RecyclerView list;
    private List<String> mDatas;
    private ButtonFlat title1, title2;
    private View Title;
    private FloatingActionButton fab;
    private SwipeRefreshLayout swipeRefreshLayout;
    @Inject
    FragmentTrendsPresenter presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {
        DaggerFragmentTrendsComponent.builder()
                .appComponent(appComponent)
                .fragmentTrendsModule(new FragmentTrendsModule(this))
                .build()
                .inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_trends, container, false);
        initView(v);
        initClick();
        presenter.attach(getContext(),this);
        presenter.onCreate();
        return v;
    }

    private void initView(View view) {
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark);
        swipeRefreshLayout.setEnabled(false);
        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        list = (RecyclerView) view.findViewById(R.id.trendsList);
        fab.attachToRecyclerView(list);
        Title =  LayoutInflater.from(getContext()).inflate(R.layout.home_title_view, null);
        title1 = (ButtonFlat)Title.findViewById(R.id.text1) ;
        title1.setText(getContext().getResources().getString(R.string.hotTrends));
        title2 =(ButtonFlat)Title.findViewById(R.id.text2) ;
        title2.setText(getContext().getResources().getString(R.string.friendTrends));
        list.setLayoutManager(new LinearLayoutManager(getContext()));
        list.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL_LIST));
        title1 = (ButtonFlat) view.findViewById(R.id.text1);
        title2 = (ButtonFlat) view.findViewById(R.id.text2);
        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                view.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                swipeRefreshLayout.setRefreshing(true);
            }
        });
    }

    private void initClick() {
        swipeRefreshLayout.setOnRefreshListener(() -> {
            presenter.getTrends();
            swipeRefreshLayout.setEnabled(false);
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.onDestroy();
        }
    }

    @Override
    public void ShowSnackBar(String s) {
        Snackbar.make(fab, s, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void ShowSwipe(boolean isShow) {
        swipeRefreshLayout.setRefreshing(isShow);
        swipeRefreshLayout.setEnabled(true);
    }

    @Override
    public void onDatafetched(List<Trends> trendses) {
        TrendsListAdapter adapter = new TrendsListAdapter(trendses);
        adapter.setTitleView(Title);
        list.setAdapter(adapter);
    }
}

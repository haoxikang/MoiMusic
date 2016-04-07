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

import com.example.moimusic.R;
import com.example.moimusic.adapter.SearchMusicAdapter;
import com.example.moimusic.mvp.presenters.BasePresenterImpl;
import com.example.moimusic.mvp.presenters.FragmentSearchAniPresenter;
import com.example.moimusic.mvp.views.FragmentSearchAniView;
import com.example.moimusic.reject.components.AppComponent;
import com.example.moimusic.reject.components.DaggerFragmentSearchAniComponent;
import com.example.moimusic.reject.components.DaggerFragmentSearchMusicComponent;
import com.example.moimusic.reject.models.FragmentSearchAniModule;
import com.example.moimusic.reject.models.FragmentSearchMusicModule;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Created by 康颢曦 on 2016/4/3.
 */
public class FragmentSearchAni  extends BaseFragment implements FragmentSearchAniView {
    public final static String EXTRA_SEARCH_STRING= "com.moimusic.fragment.searchAni";
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private String searchStr;
    @Inject
    FragmentSearchAniPresenter presenter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        searchStr = getArguments().getString(EXTRA_SEARCH_STRING);

    }

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {
        DaggerFragmentSearchAniComponent.builder()
                .appComponent(appComponent)
                .fragmentSearchAniModule(new FragmentSearchAniModule(this))
                .build()
                .inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search_ani, container, false);
        presenter.attach(getContext(),this);
        presenter.setString(searchStr);
        presenter.getSearchAni();
        initview(v);
        return v;
    }
    private void initview(View v){
        swipeRefreshLayout = (SwipeRefreshLayout)v.findViewById(R.id.swipe);
        swipeRefreshLayout.setEnabled(false);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark);
        recyclerView = (RecyclerView)v.findViewById(R.id.search_music_list);
        recyclerView.setAdapter(new SearchMusicAdapter(new ArrayList<>(),3,""));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        v.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                v.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                if (searchStr!=""){
                    swipeRefreshLayout.setRefreshing(true);
                }

            }
        });
    }
    public static   FragmentSearchAni newInstance(String searchStr){
        Bundle args = new Bundle();
        args.putString(EXTRA_SEARCH_STRING,searchStr);
        FragmentSearchAni fragmentSearchAni = new FragmentSearchAni();
        fragmentSearchAni.setArguments(args);
        return fragmentSearchAni;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (presenter!=null){
            presenter.onDestroy();
        }
    }

    @Override
    public void showSnackbar(String s) {
        Snackbar.make(recyclerView,s,Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showList(SearchMusicAdapter adapter) {
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener(){
            //用来标记是否正在向最后一个滑动，既是否向右滑动或向下滑动
            boolean isSlidingToLast = false;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                LinearLayoutManager manager = (LinearLayoutManager)recyclerView.getLayoutManager();
                // 当不滚动时
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int count = recyclerView.getAdapter().getItemCount();
                    //获取最后一个完全显示的ItemPosition
                    int lastVisibleItem = manager.findLastCompletelyVisibleItemPosition();
                    int totalItemCount = manager.getItemCount();

                    // 判断是否滚动到底部，并且是向右滚动
                    if (lastVisibleItem == (totalItemCount -1) && isSlidingToLast) {
                        //加载更多功能的代码
                        presenter.getSearchAni();
                    }
                }

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //dx用来判断横向滑动方向，dy用来判断纵向滑动方向
                if(dy> 0){
                    //大于0表示，正在向右滚动
                    isSlidingToLast = true;
                }else{
                    //小于等于0 表示停止或向左滚动
                    isSlidingToLast = false;
                }

            }
        });
    }

    @Override
    public void showAndHideSwip(boolean isShow) {
        swipeRefreshLayout.setRefreshing(isShow);
    }
}

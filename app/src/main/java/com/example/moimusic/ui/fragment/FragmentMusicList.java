package com.example.moimusic.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.example.moimusic.R;
import com.example.moimusic.adapter.MusicListViewAdapter;
import com.example.moimusic.mvp.model.entity.MusicList;
import com.example.moimusic.mvp.presenters.FragmentMusicListPresenter;
import com.example.moimusic.mvp.views.FragmentMusicListView;
import com.example.moimusic.reject.components.AppComponent;
import com.example.moimusic.reject.components.DaggerFragmentMusicListComponent;
import com.example.moimusic.reject.components.DaggerFragmentSignInComponent;
import com.example.moimusic.reject.models.FragmentMusicListModule;
import com.example.moimusic.reject.models.FragmentSignInModule;
import com.rey.material.widget.RelativeLayout;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Created by qqq34 on 2016/2/4.
 */
public class FragmentMusicList extends BaseFragment implements FragmentMusicListView {
    public static final String EXTRA_USER_ID = "com.example.moimusic.Userid";
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    @Inject
    FragmentMusicListPresenter presenter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String id = getArguments().getString(EXTRA_USER_ID);
        presenter.attach(this,id,getActivity());
    }

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {
        DaggerFragmentMusicListComponent.builder()
                .appComponent(appComponent)
                .fragmentMusicListModule(new FragmentMusicListModule(this))
                .build()
                .inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragme_music_list, container, false);
        initView(v);
        presenter.getMusicLists();
        initClick();

        return v;
    }

    private void initView(View v) {
        recyclerView = (RecyclerView) v.findViewById(R.id.my_music_recyclerView);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        swipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.music_list_swipe);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark);
        v.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                v.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                swipeRefreshLayout.setRefreshing(true);
            }
        });

    }
private void initClick(){
    swipeRefreshLayout.setOnRefreshListener(() ->{
        presenter.setPage(1);
        presenter.getMusicLists();

    });

}
    public static FragmentMusicList newInstance(String id) {
        Bundle args = new Bundle();
        args.putString(EXTRA_USER_ID, id);
        FragmentMusicList fragmentMusicList = new FragmentMusicList();
        fragmentMusicList.setArguments(args);
        return fragmentMusicList;
    }

    @Override
    public void setAdapter(MusicListViewAdapter adapter) {
        recyclerView.setAdapter(adapter);
        adapter.setOnDeleteClickLitener((view, id) -> {
            presenter.onDeleteClick(view,id);
        });
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener(){
            //用来标记是否正在向最后一个滑动，既是否向右滑动或向下滑动
            boolean isSlidingToLast = false;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                StaggeredGridLayoutManager manager = (StaggeredGridLayoutManager)recyclerView.getLayoutManager();
                // 当不滚动时
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int count = recyclerView.getAdapter().getItemCount();
                    //获取最后一个完全显示的ItemPosition
                    int[] lastItems = new int[2];
                    int[] lastVisibleItem = manager.findLastCompletelyVisibleItemPositions(lastItems);
                    int totalItemCount = manager.getItemCount();
                    int lastItem = Math.max(lastItems[0], lastItems[1]);

                    // 判断是否滚动到底部，并且是向右滚动
                    if (lastItem == (count -1) && isSlidingToLast) {
                        //加载更多功能的代码
                        presenter.getMusicLists();
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
    public void hideSwipe(boolean isHide) {

swipeRefreshLayout.setRefreshing(!isHide);
        swipeRefreshLayout.setEnabled(isHide);
    }

    @Override
    public void showSnackBar(String s) {
        Snackbar.make(recyclerView, s, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void StartActivty(Intent intent) {
        startActivity(intent);
    }

    @Override
    public void updateList() {
       recyclerView.setAdapter(new MusicListViewAdapter(new ArrayList<MusicList>(),getActivity(),true));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (presenter!=null){
            presenter.onDestroy();
        }
    }
}

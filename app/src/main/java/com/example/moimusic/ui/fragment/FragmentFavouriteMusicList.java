package com.example.moimusic.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.moimusic.R;
import com.example.moimusic.adapter.MusicListViewAdapter;
import com.example.moimusic.mvp.presenters.FragmentFavouriteMusicListPresenter;
import com.example.moimusic.mvp.views.FragmentFavouriteMusicListView;
import com.example.moimusic.reject.components.AppComponent;
import com.example.moimusic.reject.components.DaggerFragmentFavouriteMusicListComponent;
import com.example.moimusic.reject.models.FragmentFavouriteMusicListModule;

import javax.inject.Inject;


/**
 * Created by qqq34 on 2016/2/19.
 */
public class FragmentFavouriteMusicList extends BaseFragment implements FragmentFavouriteMusicListView{
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    @Inject
    FragmentFavouriteMusicListPresenter presenter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.attach(this,getContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_favourite_music_list, container, false);
        initView(v);
        initClick();
        return v;
    }
    private void initClick(){
        swipeRefreshLayout.setOnRefreshListener(() ->{
            presenter.setPage(1);
            presenter.getMusicLists();

        });
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
    public void setAdapter(MusicListViewAdapter adapter) {
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
    }

    @Override
    public void hideSwipe(boolean isHide) {


        swipeRefreshLayout.post(() -> swipeRefreshLayout.setRefreshing(!isHide));
        swipeRefreshLayout.setEnabled(isHide);
    }

    @Override
    public void showSnackBar(String s) {
        Snackbar.make(recyclerView, s, Snackbar.LENGTH_SHORT).show();
    }

    private void initView(View v) {
        recyclerView = (RecyclerView) v.findViewById(R.id.my_music_recyclerView);
        swipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.music_list_swipe);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark);
        presenter.getMusicLists();

    }
    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {
        DaggerFragmentFavouriteMusicListComponent.builder()
                .appComponent(appComponent)
                .fragmentFavouriteMusicListModule(new FragmentFavouriteMusicListModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (presenter!=null){
            presenter.onDestroy();
        }
    }
}

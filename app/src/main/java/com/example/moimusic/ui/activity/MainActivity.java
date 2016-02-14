package com.example.moimusic.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.balysv.materialripple.MaterialRippleLayout;
import com.example.moimusic.AppApplication;
import com.example.moimusic.R;
import com.example.moimusic.Servers.PlayService;
import com.example.moimusic.mvp.model.entity.EvenReCall;
import com.example.moimusic.mvp.model.entity.Music;
import com.example.moimusic.mvp.presenters.MainActivityPresenter;
import com.example.moimusic.mvp.views.IMainView;
import com.example.moimusic.play.PlayListSingleton;
import com.example.moimusic.reject.components.AppComponent;
import com.example.moimusic.reject.components.DaggerMainActivityComponent;
import com.example.moimusic.reject.models.MainActivityModule;
import com.example.moimusic.ui.fragment.FragmentFollow;
import com.example.moimusic.ui.fragment.FragmentHistory;
import com.example.moimusic.ui.fragment.FragmentMusicList;
import com.example.moimusic.ui.fragment.MainFragment;
import com.facebook.drawee.view.SimpleDraweeView;
import com.github.clans.fab.FloatingActionButton;
import com.lapism.searchview.adapter.SearchAdapter;
import com.lapism.searchview.adapter.SearchItem;
import com.lapism.searchview.history.SearchHistoryTable;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.lapism.searchview.view.SearchView;
import com.rey.material.app.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class MainActivity extends BaseActivity implements IMainView {
    private SystemBarTintManager tintManager;
    private FragmentManager fm;
    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private NavigationView navigationView;
    private SimpleDraweeView titleImage;
    private MaterialRippleLayout nextButton, prevButton, imageButton;
    private FloatingActionButton playFloat;
    private TextView HeadTitletv, HeadSingertv;
    private ProgressBar HeadProgressBar;
    private PlayListSingleton playListSingleton;
    private SimpleDraweeView headImageView;
    private List<SearchItem> mSuggestionsList;
    private SearchHistoryTable db;
    private FragmentFollow fragmentFollow;
    private FragmentHistory fragmentHistory;
    private FragmentMusicList fragmentMusicList;
    private MainFragment mainFragment;
    private Fragment isFragment; //当前fragment

    private SearchAdapter mSearchAdapter;
    private SearchView searchView;

    @Inject
    MainActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_main);
        initData();
        initFragment(savedInstanceState);
        initView();
        initClick();
        presenter.onCreate();
        presenter.attach(this,this);
        presenter.initUnderMusicList();

    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerMainActivityComponent.builder()
                .appComponent(appComponent)
                .mainActivityModule(new MainActivityModule(this))
                .build()
                //这里的inject 会把 MainActivity 所有标注了注解的成员 给动态实例化了
                .inject(this);
    }

    private void initData() {
        playListSingleton = PlayListSingleton.INSTANCE;
        db = new SearchHistoryTable(this);
        mSuggestionsList = new ArrayList<>();
        List<SearchItem> mResultsList = new ArrayList<>();
        mSearchAdapter= new SearchAdapter(this, mResultsList, mSuggestionsList, SearchView.THEME_LIGHT);
//        fm = getSupportFragmentManager();
//        Fragment fragment = fm.findFragmentById(R.id.frameLayout);
//        if (fragment == null) {
//            fragment = new MainFragment();
//            fm.beginTransaction()
//                    .add(R.id.frameLayout, fragment)
//                    .commit();
//        }
    }

    private void initView() {
        tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);

        if (android.os.Build.VERSION.SDK_INT == 19) {
            tintManager.setStatusBarTintColor(0x802896a6);
        } else {
            tintManager.setStatusBarTintColor(0x002896a6);
        }
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("MOI");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.draw_open,
                R.string.draw_close);
        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        navigationView = (NavigationView) findViewById(R.id.nv_main_navigation);
        nextButton = (MaterialRippleLayout) navigationView.getHeaderView(0).findViewById(R.id.header_next);
        prevButton = (MaterialRippleLayout) navigationView.getHeaderView(0).findViewById(R.id.header_prev);
        imageButton = (MaterialRippleLayout) navigationView.getHeaderView(0).findViewById(R.id.header_image);
        playFloat = (FloatingActionButton) navigationView.getHeaderView(0).findViewById(R.id.header_fab);
        HeadTitletv = (TextView) navigationView.getHeaderView(0).findViewById(R.id.header_titletext);
        HeadSingertv = (TextView) navigationView.getHeaderView(0).findViewById(R.id.header_singertext);
        HeadProgressBar = (ProgressBar) navigationView.getHeaderView(0).findViewById(R.id.header_progress);
        headImageView = (SimpleDraweeView) navigationView.getHeaderView(0).findViewById(R.id.header_image_view);
        searchView = (SearchView)findViewById(R.id.search_view);
        if (Build.VERSION.SDK_INT==Build.VERSION_CODES.KITKAT){
            searchView.setPadding(0,getStatusBarHeight(),0,0);
        }
        searchView.setVersion(SearchView.VERSION_MENU_ITEM);
        searchView.setStyle(SearchView.STYLE_CLASSIC);
        searchView.setTheme(SearchView.THEME_LIGHT);
        searchView.setDivider(false);
        searchView.setHint("搜索");
        searchView.setHintSize(getResources().getDimension(R.dimen.search_text_medium));
        searchView.setAnimationDuration(360);
        setPlayViewEnable(false);
        searchView.setAdapter(mSearchAdapter);
    }

    public void initClick() {
        playFloat.setOnClickListener(v -> presenter.musicPlay());
        nextButton.setOnClickListener(v -> presenter.nextMusic());
        prevButton.setOnClickListener(v -> presenter.prevMusic());
        navigationView.setNavigationItemSelectedListener(menuItem -> {
                 mDrawerLayout.closeDrawers();
                 switch (menuItem.getItemId()){
                     case R.id.usercenter:{
                         break;
                     }
                     case R.id.home:{
                         if(mainFragment==null) {
                             mainFragment = new MainFragment();
                         }
                         switchContent(isFragment,mainFragment);
                         getSupportActionBar().setTitle(menuItem.getTitle());
                         break;
                     }
                     case R.id.history:{
                         if(fragmentHistory==null) {
                             fragmentHistory = new FragmentHistory();
                         }
                         switchContent(isFragment,fragmentHistory);
                         getSupportActionBar().setTitle(menuItem.getTitle());
                         break;
                     }
                     case R.id.musiclist:{
                         if(fragmentMusicList==null) {
                             fragmentMusicList = new FragmentMusicList();
                         }
                         switchContent(isFragment,fragmentMusicList);
                         getSupportActionBar().setTitle(menuItem.getTitle());
                         break;
                     }
                     case R.id.follow:{
                         if(fragmentFollow==null) {
                             fragmentFollow = new FragmentFollow();
                         }
                         switchContent(isFragment,fragmentFollow);
                         getSupportActionBar().setTitle(menuItem.getTitle());
                         break;
                     }
                     case R.id.seting:{
                         break;
                     }
                     case R.id.exit:{
                         stopService(new Intent(MainActivity.this, PlayService.class));
                        finish();
                         break;
                     }
                 }
                 return true;
             });
        mSearchAdapter.setOnItemClickListener((view, position) -> {
            searchView.closeSearchView(false);
            TextView textView = (TextView) view.findViewById(R.id.textView_item_text);
            CharSequence text = textView.getText();
            db.addItem(new SearchItem(text));
            Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.closeSearchView(false);
                db.addItem(new SearchItem(query));
                Toast.makeText(getApplicationContext(), query, Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        searchView.setOnSearchViewListener(new SearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
            }

            @Override
            public void onSearchViewClosed() {
            }
        });
    }
    public void initFragment(Bundle savedInstanceState)
    {
        //判断activity是否重建，如果不是，则不需要重新建立fragment.
        if(savedInstanceState==null) {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            if(mainFragment==null) {
                mainFragment = new MainFragment();
            }
            isFragment = mainFragment;
            ft.replace(R.id.frameLayout, mainFragment).commit();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_search: {
                showSearchView();
                return true;
            }
            case R.id.underPlayList:{
                presenter.ShowMusicList();
            }
            default:
                return super.onOptionsItemSelected(item);
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.onDestroy();
        }
    }

    @Override
    public void setPlayViewEnable(boolean isEnable) {
        nextButton.setEnabled(isEnable);
        prevButton.setEnabled(isEnable);
        imageButton.setEnabled(isEnable);
        playFloat.setEnabled(isEnable);
        HeadTitletv.setEnabled(isEnable);
        HeadSingertv.setEnabled(isEnable);
        HeadProgressBar.setEnabled(isEnable);
    }

    @Override
    public void updataPlayView() {
        int currentMusic = playListSingleton.getCurrentPosition();
        Music music = playListSingleton.getMusicList().get(currentMusic);
        if (music.getMusicImageUri() != null && !music.getMusicImageUri().equals("")) {
            headImageView.setImageURI(Uri.parse(music.getMusicImageUri()));
        }
        if (music.getMusicName() != null) {
            HeadTitletv.setText(music.getMusicName());
        }
        if (music.getSinger() != null) {
            HeadSingertv.setText(music.getSinger());
        }
        if (playListSingleton.isUnderPlay) {
            playFloat.setImageResource(R.mipmap.ic_pause_black_18dp);
        } else {
            playFloat.setImageResource(R.mipmap.ic_play_arrow_black_18dp);
        }

    }

    @Override
    public void setPlayButton(boolean ispause) {
        if (ispause) {
            playFloat.setImageResource(R.mipmap.ic_pause_black_18dp);
        } else {

        }
    }

    @Override
    public void setProgressbar(int current, int all) {
        HeadProgressBar.setMax(all);
        HeadProgressBar.setProgress(current);
    }

    public void switchContent(Fragment from, Fragment to) {
        if (isFragment != to) {
            isFragment = to;
            FragmentManager fm = getSupportFragmentManager();
            //添加渐隐渐现的动画
            FragmentTransaction ft = fm.beginTransaction();
            if (!to.isAdded()) {    // 先判断是否被add过
                ft.hide(from).add(R.id.frameLayout, to).commit(); // 隐藏当前的fragment，add下一个到Activity中
            } else {
                ft.hide(from).show(to).commit(); // 隐藏当前的fragment，显示下一个
            }
        }
    }
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//
//        // 过滤按键动作
//        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
//
//            if (mSearchView.isSearchOpen() && mSearchView.isSearchOpen()) {
//                mSearchView.closeSearch(true);
//            } else {
//                moveTaskToBack(true);
//            }
//
//        }
//
//        return super.onKeyDown(keyCode, event);
//    }
private void showSearchView() {
    mSuggestionsList.clear();
    mSuggestionsList.add(new SearchItem("Google"));
    mSuggestionsList.add(new SearchItem("Android"));
    mSuggestionsList.addAll(db.getAllItems());
    searchView.openSearchView(true);
}
    @Override
    public void onBackPressed() {
        if (searchView.isSearchOpen() && searchView.isSearchOpen()) {
            searchView.closeSearchView(true);
        }else {
            moveTaskToBack(true);
            super.onBackPressed();
        }

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        // 过滤按键动作
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK&&!searchView.isSearchOpen()) {

            moveTaskToBack(true);

        }

        return super.onKeyDown(keyCode, event);
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.saveData();
    }
}

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
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.example.moimusic.R;
import com.example.moimusic.Servers.PlayService;
import com.example.moimusic.mvp.model.entity.Music;
import com.example.moimusic.mvp.presenters.MainActivityPresenter;
import com.example.moimusic.mvp.views.IMainView;
import com.example.moimusic.play.PlayListSingleton;
import com.example.moimusic.reject.components.AppComponent;
import com.example.moimusic.reject.components.DaggerMainActivityComponent;
import com.example.moimusic.reject.models.MainActivityModule;
import com.example.moimusic.ui.fragment.FragmentFollow;
import com.example.moimusic.ui.fragment.FragmentSearch;
import com.example.moimusic.ui.fragment.FragmentMsg;
import com.example.moimusic.ui.fragment.MainFragment;
import com.example.moimusic.utils.Utils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.github.clans.fab.FloatingActionButton;
import com.lapism.searchview.adapter.SearchAdapter;
import com.lapism.searchview.adapter.SearchItem;
import com.lapism.searchview.history.SearchHistoryTable;
import com.lapism.searchview.view.SearchCodes;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.lapism.searchview.view.SearchView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;

public class MainActivity extends BaseActivity implements IMainView {
    private SystemBarTintManager tintManager;
    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private NavigationView navigationView;
    private MaterialRippleLayout nextButton, prevButton, imageButton;
    private FloatingActionButton playFloat;
    private TextView HeadTitletv, HeadSingertv;
    private ProgressBar HeadProgressBar;
    private PlayListSingleton playListSingleton;
    private SimpleDraweeView headImageView;
    private List<SearchItem> mSuggestionsList;
    private SearchHistoryTable db;
    private FragmentFollow fragmentFollow;
    private FragmentSearch fragmentSearch;
    private MainFragment mainFragment;
    private FragmentMsg fragmentMsg;
    private Fragment isFragment; //当前fragment
    private SearchAdapter mSearchAdapter;
    private SearchView searchView;
    private Observable<String> searchObservable;
    private MenuItem searchItem,homePageItem;
    @Inject
    MainActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_main);
        initData();
        initView();
        initClick();
        presenter.onCreate();
        presenter.attach(this, this, savedInstanceState);
        presenter.initUnderMusicList();
        presenter.checkPermission();
        presenter.subscribeSearch();
        initFragment(savedInstanceState);


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
        mSearchAdapter = new SearchAdapter(this, mResultsList, mSuggestionsList, SearchCodes.THEME_LIGHT);
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
            tintManager.setStatusBarTintColor(0x801c9dd0);
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
        searchView = (SearchView) findViewById(R.id.search_view);
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            searchView.setPadding(0, getStatusBarHeight(), 0, 0);
        }
        searchView.setVersion(SearchCodes.VERSION_MENU_ITEM);
        searchView.setStyle(SearchCodes.STYLE_MENU_ITEM_CLASSIC);
        searchView.setTheme(SearchCodes.THEME_LIGHT);
        searchView.setVoice(false);
        searchView.setDivider(false);
        searchView.setHint("搜索");
        searchView.setHintSize(getResources().getDimension(R.dimen.search_text_medium));
        searchView.setAnimationDuration(360);
        setPlayViewEnable(false);
        searchView.setAdapter(mSearchAdapter);
    }

    public void initClick() {
        imageButton.setOnClickListener(v1 -> startNextActivity(new Intent(this, ActivityPlayNow.class)));
        playFloat.setOnClickListener(v -> presenter.musicPlay());
        nextButton.setOnClickListener(v -> presenter.nextMusic());
        prevButton.setOnClickListener(v -> presenter.prevMusic());
        searchItem = navigationView.getMenu().findItem(R.id.search);
        homePageItem = navigationView.getMenu().findItem(R.id.home);
        navigationView.setNavigationItemSelectedListener(menuItem -> {
            mDrawerLayout.closeDrawers();
            switch (menuItem.getItemId()) {
                case R.id.usercenter: {
                    presenter.logButtonClick();
                    break;
                }
                case R.id.home: {
                    if (mainFragment == null) {
                        mainFragment = new MainFragment();
                    }
                    switchContent(isFragment, mainFragment);
                    getSupportActionBar().setTitle(menuItem.getTitle());
                    break;
                }
                case R.id.search: {
                    if (fragmentSearch == null) {
                        fragmentSearch = new FragmentSearch();
                    }
                    switchContent(isFragment, fragmentSearch);
                    getSupportActionBar().setTitle(menuItem.getTitle());
                    break;
                }
                case R.id.msg: {
                    if (fragmentMsg == null) {
                        fragmentMsg = new FragmentMsg();
                    }
                    switchContent(isFragment, fragmentMsg);
                    getSupportActionBar().setTitle(menuItem.getTitle());
                    break;
                }
                case R.id.follow: {
                    if (fragmentFollow == null) {
                        fragmentFollow = new FragmentFollow();
                    }
                    switchContent(isFragment, fragmentFollow);
                    getSupportActionBar().setTitle(menuItem.getTitle());
                    break;
                }
                case R.id.seting: {
                    break;
                }
                case R.id.exit: {
                    stopService(new Intent(MainActivity.this, PlayService.class));
                    finish();
                    break;
                }
            }
            return true;
        });

        mSearchAdapter.setOnItemClickListener((view, position) -> {
            searchView.hide(true);
            TextView textView = (TextView) view.findViewById(R.id.textView_item_text);
            CharSequence text = textView.getText();
            db.addItem(new SearchItem(text));
            presenter.searchItemClick(text.toString());
        });
        searchObservable = Observable.create(subscriber -> searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.hide(true);
                db.addItem(new SearchItem(query));
                if (fragmentSearch == null) {
                    fragmentSearch = new FragmentSearch();
                }
                switchContent(isFragment, fragmentSearch);
                getSupportActionBar().setTitle(getResources().getString(R.string.search));
                searchItem.setChecked(true);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText!=""&&newText!=null){
                    subscriber.onNext(newText);
                }

                return false;
            }
        }));



    }

    public void initFragment(Bundle savedInstanceState) {
        //判断activity是否重建，如果不是，则不需要重新建立fragment.
        if (savedInstanceState == null) {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            if (mainFragment == null) {
                mainFragment = new MainFragment();
            }
            isFragment = mainFragment;
            ft.replace(R.id.frameLayout, mainFragment).commitAllowingStateLoss();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

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
                break;
            }
            case R.id.underPlayList: {
                presenter.ShowMusicList();
                break;
            }
            case R.id.action_log: {
                presenter.logButtonClick();
                break;
            }

        }
        return super.onOptionsItemSelected(item);
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
        if (playListSingleton.getMusicList().size()!=0){
            if (currentMusic<playListSingleton.getMusicList().size()){
                setPlayViewEnable(true);
                Music music = playListSingleton.getMusicList().get(currentMusic);
                if (music.getMusicImageUri() != null && !music.getMusicImageUri().equals("")) {

                    Uri uri =Uri.parse(music.getMusicImageUri());
                    Utils.reSizeImage(300,400,uri,headImageView);
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

    @Override
    public void startNextActivity(Intent intent) {
        startActivity(intent);
    }

    @Override
    public void ShowSnackbar(String s) {
        Snackbar.make(searchView, s, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void ShowLongSnackbar(String s) {
        Snackbar.make(searchView, s, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public Observable<String> getSearchObservable() {

        return searchObservable;
    }

    @Override
    public void onSearched(List<Music> musics) {
        List<SearchItem> strings = new ArrayList<>();
        for (Music music:musics){
            strings.add(new SearchItem(music.getMusicName()));
        }
        mSuggestionsList.clear();
        mSuggestionsList.addAll(strings);

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
        mSuggestionsList.addAll(db.getAllItems());
        searchView.show(true);
    }

    @Override
    public void onBackPressed() {
        if (searchView.isSearchOpen() && searchView.isSearchOpen()) {
            searchView.hide(true);
        } else if (!(isFragment instanceof MainFragment)){
            if (mainFragment == null) {
                mainFragment = new MainFragment();
            }
            switchContent(isFragment, mainFragment);
            getSupportActionBar().setTitle(getResources().getString(R.string.home_page));
            homePageItem.setChecked(true);
        }else {
            moveTaskToBack(true);
            super.onBackPressed();
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        // 过滤按键动作
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && !searchView.isSearchOpen()&&isFragment instanceof MainFragment) {

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

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {


        presenter.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }
}

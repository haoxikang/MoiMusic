package com.example.moimusic.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.example.moimusic.R;
import com.example.moimusic.mvp.presenters.LogActivityPresenter;
import com.example.moimusic.mvp.views.IlogView;
import com.example.moimusic.reject.components.AppComponent;
import com.example.moimusic.reject.components.DaggerLogActivityComponent;
import com.example.moimusic.reject.models.LogActivityModule;
import com.example.moimusic.ui.fragment.FragmentSignIn;
import com.example.moimusic.ui.fragment.FragmentSignUp;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import javax.inject.Inject;

/**
 * Created by qqq34 on 2016/2/14.
 */
public class LogActivity extends BaseActivity implements IlogView{
    @Inject
    LogActivityPresenter presenter;
    private SystemBarTintManager tintManager;
    private ViewPager viewPager;
    private FragmentSignIn fragmentSignIn;
    private FragmentSignUp fragmentSignUp;
    private RelativeLayout signInLine,signUpLine;
    private TextView signInText,signUpText;
    private MaterialRippleLayout logUpBk,logInBk;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_login);
        presenter.attach(this,this);
        initdata();
        initview();
        initclick();
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerLogActivityComponent.builder()
                .appComponent(appComponent)
                .logActivityModule(new LogActivityModule(this))
                .build()
                .inject(this);
    }
    private void initview(){
        logInBk = (MaterialRippleLayout)findViewById(R.id.log_in_bk);
        logUpBk = (MaterialRippleLayout)findViewById(R.id.log_up_bk);
        signInLine = (RelativeLayout)findViewById(R.id.signin_line);
        signUpLine = (RelativeLayout)findViewById(R.id.signup_line);
        signInText = (TextView)findViewById(R.id.signin_text);
        signUpText = (TextView)findViewById(R.id.signup_text);
        tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintColor(0x002896a6);
        viewPager =(ViewPager) findViewById(R.id.log_viewpager);
        FragmentManager fm = getSupportFragmentManager();
        viewPager.setAdapter(new FragmentPagerAdapter(fm) {
            @Override
            public Fragment getItem(int position) {
                if (position==0){
                    return fragmentSignIn;
                }else if (position==1){
                    return fragmentSignUp;
                }else {
                    return null;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        });
    }
    private void initdata(){
        fragmentSignIn = new FragmentSignIn();
        fragmentSignUp = new FragmentSignUp();
    }
    private void initclick(){
        logInBk.setOnClickListener(v -> viewPager.setCurrentItem(0));
        logUpBk.setOnClickListener(v -> viewPager.setCurrentItem(1));
viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (position==0){
            presenter.setTabs(true);
        }else {
            presenter.setTabs(false);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
});
    }

    @Override
    public void SelectTabs(boolean isSelect) {
        if (!isSelect){
            signUpLine.setBackgroundColor(this.getResources().getColor(R.color.colorPrimary));
            signUpText.setTextColor(this.getResources().getColor(R.color.colorPrimary));
            signInLine.setBackgroundColor(0x00000000);
            signInText.setTextColor(this.getResources().getColor(R.color.greywhite));
        }else {
            signInLine.setBackgroundColor(this.getResources().getColor(R.color.colorPrimary));
            signInText.setTextColor(this.getResources().getColor(R.color.colorPrimary));
            signUpLine.setBackgroundColor(0x00000000);
            signUpText.setTextColor(this.getResources().getColor(R.color.greywhite));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter!=null){
            presenter.onDestroy();
        }

    }
}

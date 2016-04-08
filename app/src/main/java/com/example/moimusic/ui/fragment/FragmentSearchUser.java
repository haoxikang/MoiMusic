package com.example.moimusic.ui.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.example.moimusic.R;
import com.example.moimusic.mvp.model.entity.MoiUser;
import com.example.moimusic.mvp.presenters.FragmentSearchUserPresenter;
import com.example.moimusic.mvp.views.FragmentSearchMusicView;
import com.example.moimusic.mvp.views.FragmentSearchUserView;
import com.example.moimusic.reject.components.AppComponent;
import com.example.moimusic.reject.components.DaggerFragmentSearchSingerComponent;
import com.example.moimusic.reject.components.DaggerFragmentSearchUserComponent;
import com.example.moimusic.reject.models.FragmentSearchSingerModule;
import com.example.moimusic.reject.models.FragmentSearchUserModule;
import com.example.moimusic.ui.activity.UserCenterActivity;
import com.example.moimusic.utils.Utils;
import com.facebook.drawee.view.SimpleDraweeView;

import javax.inject.Inject;

/**
 * Created by 康颢曦 on 2016/4/3.
 */
public class FragmentSearchUser  extends BaseFragment implements FragmentSearchUserView {
    private TextView name,tellnum;
    private MaterialRippleLayout materialRippleLayout;
    private SimpleDraweeView imageView;
    private RelativeLayout relativeLayout;
    private SwipeRefreshLayout swipeRefreshLayout;
    private String searchStr;
    public final static String EXTRA_SEARCH_STRING= "com.moimusic.fragment.searchuser";
    @Inject
    FragmentSearchUserPresenter presenter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        searchStr = getArguments().getString(EXTRA_SEARCH_STRING);
    }

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {
        DaggerFragmentSearchUserComponent.builder()
                .appComponent(appComponent)
                .fragmentSearchUserModule(new FragmentSearchUserModule(this))
                .build()
                .inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search_user, container, false);
        initview(v);
        presenter.attach(getContext(),this);
        presenter.setString(searchStr);
        presenter.getSearchUser();
        return v;
    }
private void initview(View view){
    swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.swipe);
    swipeRefreshLayout.setEnabled(false);
    swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark);
    name = (TextView)view.findViewById(R.id.search_user_name);
    relativeLayout = (RelativeLayout)view.findViewById(R.id.rootLayout);
    tellnum = (TextView)view.findViewById(R.id.search_user_num);
    materialRippleLayout = (MaterialRippleLayout)view.findViewById(R.id.ripple);
    imageView = (SimpleDraweeView)view.findViewById(R.id.simpleView);
    relativeLayout.setVisibility(View.GONE);
    view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {
            view.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            if (searchStr!=""){
                swipeRefreshLayout.setRefreshing(true);
            }
        }
    });
}
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (presenter!=null){
            presenter.onDestroy();
        }
    }
    public static   FragmentSearchUser newInstance(String searchStr){
        Bundle args = new Bundle();
        args.putString(EXTRA_SEARCH_STRING,searchStr);
        FragmentSearchUser fragmentSearchUser = new FragmentSearchUser();
        fragmentSearchUser.setArguments(args);
        return fragmentSearchUser;
    }

    @Override
    public void showSnackbar(String s) {
        Snackbar.make(relativeLayout,s,Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showView(MoiUser moiUser) {
        relativeLayout.setVisibility(View.VISIBLE);
        if (moiUser.getImageFile()!=null){
            Uri uri = Uri.parse(moiUser.getImageFile().getFileUrl(getContext()));
            Utils.reSizeImage(100,100,uri,imageView);
        }
        name.setText(moiUser.getName());
        tellnum.setText(moiUser.getMobilePhoneNumber());
        materialRippleLayout.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), UserCenterActivity.class);
            intent.putExtra("userID",moiUser.getObjectId());
            startActivity(intent);
        });
    }

    @Override
    public void showAndHideSwip(boolean isShow) {
        swipeRefreshLayout.setRefreshing(isShow);
    }
}

package com.example.moimusic.adapter;

import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.moimusic.R;
import com.example.moimusic.mvp.model.entity.Recommend;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.LoopPagerAdapter;

import java.util.List;

/**
 * Created by qqq34 on 2016/3/18.
 */
public  class TestLoopAdapter extends LoopPagerAdapter {
    private List<Recommend> list;
    public TestLoopAdapter(RollPagerView viewPager) {
        super(viewPager);
    }
public void attach(List<Recommend> list ){
    this.list = list;
}
    @Override
    public View getView(ViewGroup container, int position) {
        SimpleDraweeView  view = new SimpleDraweeView(container.getContext());
        if (list!=null){
            view.setImageURI(Uri.parse(list.get(position).getImage().getFileUrl(container.getContext())));
        }
    //    view.setImageURI(Uri.parse(list.get(position).getImage().getFileUrl(container.getContext())));
        return view;
    }

    @Override
    public int getRealCount() {
        return 4;
    }

}
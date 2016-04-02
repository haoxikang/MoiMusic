package com.example.moimusic.adapter;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.balysv.materialripple.MaterialRippleLayout;
import com.example.moimusic.AppApplication;
import com.example.moimusic.R;
import com.example.moimusic.mvp.model.entity.Music;
import com.example.moimusic.mvp.model.entity.MusicList;
import com.example.moimusic.utils.Utils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qqq34 on 2016/3/5.
 */
public class CollectMusicListAdapter extends RecyclerView.Adapter<CollectMusicListAdapter.MyViewHolder>{
    private List<MusicList> lists = new ArrayList<>();

    public CollectMusicListAdapter(List<MusicList> lists) {
        this.lists = lists;
    }
    public interface OnItemClickLitener
    {
        void onItemClick(View view, int position);
        void onItemLongClick(View view , int position);
    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener)
    {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                AppApplication.context).inflate(R.layout.collect_music_list_view, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tv.setText(lists.get(position).getName());
        if (lists.get(position).getListImageUri()!=null){
            Uri uri = Uri.parse(lists.get(position).getListImageUri());
            Utils.reSizeImage(80,80,uri,holder.imageView);
        }
        if (mOnItemClickLitener != null) {
            holder.materialRippleLayout.setOnClickListener(v -> {
                int pos = holder.getLayoutPosition();
                mOnItemClickLitener.onItemClick(holder.materialRippleLayout, pos);
            });

            holder.materialRippleLayout.setOnLongClickListener(v -> {
                int pos = holder.getLayoutPosition();
                mOnItemClickLitener.onItemLongClick(holder.materialRippleLayout, pos);
                return false;
            });

        }
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {

        TextView tv;
        MaterialRippleLayout materialRippleLayout;
        SimpleDraweeView imageView;
        public MyViewHolder(View view)
        {
            super(view);
            tv = (TextView) view.findViewById(R.id.list_view_text);
            materialRippleLayout = (MaterialRippleLayout)view.findViewById(R.id.under_list_ripple);
            imageView = (SimpleDraweeView)view.findViewById(R.id.view);
        }
    }
}

package com.example.moimusic.adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.example.moimusic.AppApplication;
import com.example.moimusic.R;
import com.example.moimusic.mvp.model.entity.MoiUser;
import com.example.moimusic.mvp.model.entity.MusicList;
import com.example.moimusic.ui.activity.ActivityMusicList;
import com.example.moimusic.utils.Utils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.rey.material.app.Dialog;
import com.rey.material.widget.LinearLayout;

import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by qqq34 on 2016/2/20.
 */
public class MusicListViewAdapter extends RecyclerView.Adapter<MusicListViewAdapter.MyViewHolder> {
    private List<MusicList> musicLists ;
    private FragmentActivity activity;
    private boolean isCurrentUser;
    public MusicListViewAdapter(List<MusicList> musicLists,FragmentActivity activity,boolean isCurrentUser) {
       this. musicLists = musicLists;
        this.activity = activity;
        this.isCurrentUser = isCurrentUser;
    }
    public interface OnItemClickLitener
    {
        void onItemClick(View view, int position);
        void onItemLongClick(View view , int position);
    }
    public interface OnDeleteClickLitener{
        void onDeleteClick(View view,String position);
    }
    private OnDeleteClickLitener onDeleteClickLitener;

    public void setOnDeleteClickLitener(OnDeleteClickLitener onDeleteClickLitener) {
        this.onDeleteClickLitener = onDeleteClickLitener;
    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener)
    {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                AppApplication.context).inflate(R.layout.music_list_view, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.materialRippleLayout.setOnClickListener(v -> {
                Intent intent = new Intent(activity, ActivityMusicList.class);
                intent.putExtra("musiclistid",musicLists.get(position).getObjectId());
                    activity.startActivity(intent);
        });
        if (isCurrentUser){
            holder.imageView.setVisibility(View.VISIBLE);
        }else {
            holder.imageView.setVisibility(View.INVISIBLE);
        }
holder.imageView.setOnClickListener(v -> {
    if (onDeleteClickLitener!=null){
        onDeleteClickLitener.onDeleteClick(v,musicLists.get(position).getObjectId());
    }
});
        if (musicLists.get(position).getName()!=null){
            holder.tv.setText(musicLists.get(position).getName());
        }else {
            holder.tv.setText("");
        }
        if (musicLists.get(position).getPlayNum()!=null){

            holder.tvNum.setText(Utils.fitNum(musicLists.get(position).getPlayNum()));

        }else {
            holder.tvNum.setText("0");
        }
        if (musicLists.get(position).getListImageUri()!=null){
            holder.simpleDraweeView.setImageURI(Uri.parse(musicLists.get(position).getListImageUri()));
        }


    }

    @Override
    public int getItemCount() {
        return musicLists.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
CardView cardView;
        TextView tv,tvNum;
        ImageView imageView;
        SimpleDraweeView simpleDraweeView;
        MaterialRippleLayout materialRippleLayout;
        public MyViewHolder(View view)
        {
            super(view);
            imageView = (ImageView)view.findViewById(R.id.imageDelete);
            cardView = (CardView)view.findViewById(R.id.cardView);
            tv = (TextView) view.findViewById(R.id.music_list_view_title_text);
            tvNum = (TextView) view.findViewById(R.id.music_list_view_number);
            simpleDraweeView = (SimpleDraweeView)view.findViewById(R.id.music_list_view_image);
            materialRippleLayout=(MaterialRippleLayout)view.findViewById(R.id.music_list_view_ripple);
        }
    }
    public void addData(List<MusicList> musicLists){
        this.musicLists.addAll(musicLists);
        notifyDataSetChanged();
    }
}

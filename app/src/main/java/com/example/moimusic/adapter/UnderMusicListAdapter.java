package com.example.moimusic.adapter;

import android.graphics.Color;
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
import com.example.moimusic.play.PlayListSingleton;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by qqq34 on 2016/2/12.
 */
public class UnderMusicListAdapter extends RecyclerView.Adapter<UnderMusicListAdapter.MyViewHolder> {
    PlayListSingleton playListSingleton = PlayListSingleton.INSTANCE;
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
                AppApplication.context).inflate(R.layout.under_list_view, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        List<Music> musicList = playListSingleton.getMusicList();
        holder.tv.setText(musicList.get(position).getMusicName());
        if (position==playListSingleton.getCurrentPosition()){
            holder.tv.setTextColor(AppApplication.context.getResources().getColor(R.color.colorPrimary));
        }else {
            holder.tv.setTextColor(AppApplication.context.getResources().getColor(R.color.titeleColor));
        }
        if (playListSingleton.getMusicList().get(position).getMusicImageUri()!=null&&!playListSingleton.getMusicList().get(position).getMusicImageUri().equals("")){
            holder.imageView.setImageURI(Uri.parse(playListSingleton.getMusicList().get(position).getMusicImageUri()));
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
        holder.simpleDraweeView.setOnClickListener(v -> {
            Toast.makeText(AppApplication.context,"点击了删除键",Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {

        return playListSingleton.getMusicList().size();
    }
    class MyViewHolder extends RecyclerView.ViewHolder
    {

        TextView tv;
        MaterialRippleLayout materialRippleLayout;
        SimpleDraweeView simpleDraweeView,imageView;
        public MyViewHolder(View view)
        {
            super(view);
            tv = (TextView) view.findViewById(R.id.list_view_text);
            simpleDraweeView = (SimpleDraweeView)view.findViewById(R.id.list_view_delete);
            materialRippleLayout = (MaterialRippleLayout)view.findViewById(R.id.under_list_ripple);
            imageView = (SimpleDraweeView)view.findViewById(R.id.view);
        }
    }
}

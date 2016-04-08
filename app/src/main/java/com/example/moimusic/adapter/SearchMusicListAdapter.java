package com.example.moimusic.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.example.moimusic.AppApplication;
import com.example.moimusic.R;
import com.example.moimusic.mvp.model.entity.MusicList;
import com.example.moimusic.ui.activity.ActivityMusicList;
import com.example.moimusic.utils.Utils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;


/**
 * Created by 康颢曦 on 2016/4/7.
 */
public class SearchMusicListAdapter extends RecyclerView.Adapter<SearchMusicListAdapter.MyViewHolder> {
    private List<MusicList> musicLists;
    private Context context;
    private String searchString;

    public SearchMusicListAdapter(String searchString, List<MusicList> musicLists) {
        this.searchString = searchString;
        this.musicLists = musicLists;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                AppApplication.context).inflate(R.layout.search_music_view, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        MusicList musicList = musicLists.get(position);
        holder.singer.setText(musicList.getMoiUser().getName());
        if (musicList.getListImageUri()!=null){
            Uri uri = Uri.parse(musicList.getListImageUri());
            Utils.reSizeImage(100,100,uri,holder.imageView);
        }
        String str = musicList.getName();
        int[] i = Utils.getSimpleStrPosition(str,searchString);
        SpannableStringBuilder style=new SpannableStringBuilder(str);
        style.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.colorPrimary)),i[0],i[1], Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        holder.name.setText(style);
        holder.materialRippleLayout.setOnClickListener(v -> {
            Intent intent = new Intent(context, ActivityMusicList.class);
            intent.putExtra("musiclistid",musicList.getObjectId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return musicLists.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {

        TextView name,singer;
        MaterialRippleLayout materialRippleLayout;
        SimpleDraweeView imageView;
        public MyViewHolder(View view)
        {
            super(view);
            name = (TextView)view.findViewById(R.id.search_music_name);
            singer = (TextView)view.findViewById(R.id.search_music_singer);
            materialRippleLayout = (MaterialRippleLayout)view.findViewById(R.id.ripple);
            imageView = (SimpleDraweeView)view.findViewById(R.id.simpleView);
        }
    }
    public void addAndUpData(List<MusicList> musicLists){
        this.musicLists.addAll(musicLists);
        notifyDataSetChanged();
    }
}

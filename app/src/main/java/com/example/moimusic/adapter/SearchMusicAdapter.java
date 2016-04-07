package com.example.moimusic.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.example.moimusic.AppApplication;
import com.example.moimusic.R;
import com.example.moimusic.mvp.model.entity.EvenCall;
import com.example.moimusic.mvp.model.entity.Music;
import com.example.moimusic.play.PlayListSingleton;
import com.example.moimusic.ui.activity.ActivityPlayNow;
import com.example.moimusic.utils.Utils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by 康颢曦 on 2016/4/4.
 */
public class SearchMusicAdapter extends RecyclerView.Adapter<SearchMusicAdapter.MyViewHolder>{
    private List<Music> musics;
    private int type; //1歌曲  2歌手  3动漫
    private Context context;
    private String searchString;
    public SearchMusicAdapter(List<Music> musics, int type,String searchString) {
        this.musics = musics;
        this.type = type;
        this.searchString = searchString;
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
        PlayListSingleton playListSingleton = PlayListSingleton.INSTANCE;
        Music music = musics.get(position);
            holder.name.setText(music.getMusicName()) ;
            if (music.getMusicImageUri()!=null){
                Uri uri = Uri.parse(music.getMusicImageUri());
                Utils.reSizeImage(100,100,uri,holder.imageView);
            }
        if (type==1){
            holder.singer.setText(music.getSinger());
            String str = music.getMusicName();
            int[] i = Utils.getSimpleStrPosition(str,searchString);
            SpannableStringBuilder style=new SpannableStringBuilder(str);
            style.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.colorPrimary)),i[0],i[1], Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            holder.name.setText(style);
        }else if (type==2){
            holder.singer.setText(music.getSinger());
            String str = music.getSinger();
            int[] i = Utils.getSimpleStrPosition(str,searchString);
            SpannableStringBuilder style=new SpannableStringBuilder(str);
            style.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.colorPrimary)),i[0],i[1], Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            holder.singer.setText(style);

        }else if (type==3){
            String str = music.getAlbum();
            int[] i = Utils.getSimpleStrPosition(str,searchString);
            SpannableStringBuilder style=new SpannableStringBuilder(str);
            style.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.colorPrimary)),i[0],i[1], Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            holder.singer.setText(style);
        }
        holder.materialRippleLayout.setOnClickListener(v -> {
            if (music!=null){
                for (Music m :playListSingleton.getMusicList()){
                    if (m.getObjectId().endsWith(music.getObjectId())){
                        playListSingleton.getMusicList().remove(m);
                        break;
                    }
                }
                playListSingleton.getMusicList().add(music);
                playListSingleton.setCurrentPosition( playListSingleton.getMusicList().size()-1);
                EvenCall evenCall = new EvenCall();
                evenCall.setCurrentOrder(EvenCall.PLAY);
                EventBus.getDefault().post(evenCall);
                context.startActivity(new Intent(context, ActivityPlayNow.class));
            }
        });

    }

    @Override
    public int getItemCount() {
        return musics.size();
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
    public void addAndUpData(List<Music> musics){
        this.musics.addAll(musics);
        notifyDataSetChanged();
    }
}

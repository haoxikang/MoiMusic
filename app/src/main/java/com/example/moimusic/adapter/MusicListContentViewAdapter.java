package com.example.moimusic.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.example.moimusic.AppApplication;
import com.example.moimusic.R;
import com.example.moimusic.mvp.model.entity.EvenActivityMusicListCall;
import com.example.moimusic.mvp.model.entity.EvenCall;
import com.example.moimusic.mvp.model.entity.EvenMusicListContentAdapterCall;
import com.example.moimusic.mvp.model.entity.Music;
import com.example.moimusic.mvp.model.entity.MusicList;
import com.example.moimusic.play.PlayListSingleton;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import de.greenrobot.event.EventBus;


/**
 * Created by qqq34 on 2016/2/24.
 */
public class MusicListContentViewAdapter extends RecyclerView.Adapter<MusicListContentViewAdapter.MyViewHolder> {

    List<Music> musicList;

    public MusicListContentViewAdapter(List<Music> musicList) {
        this.musicList = musicList;
        EventBus.getDefault().register(this);
    }

    @Override
    public MusicListContentViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                AppApplication.context).inflate(R.layout.fragment_music_list_content_view, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MusicListContentViewAdapter.MyViewHolder holder, int position) {
        if (musicList.get(position).getMusicName() != null) {
            holder.tv.setTextColor(AppApplication.context.getResources().getColor(R.color.black));
            holder.tv.setText(musicList.get(position).getMusicName());
        }
        if (PlayListSingleton.INSTANCE.getCurrentMusicId()!=null){
            if (musicList.get(position).getMusicName() != null&&PlayListSingleton.INSTANCE.getCurrentMusicId().equals(musicList.get(position).getObjectId())){
                holder.tv.setTextColor(AppApplication.context.getResources().getColor(R.color.colorPrimary));
            }
        }

        if (musicList.get(position).getSinger() != null) {
            holder.tvsub.setText(musicList.get(position).getSinger());
        }
        holder.materialRippleLayout.setOnClickListener(v -> {
            PlayListSingleton.INSTANCE.setMusicList(musicList);
            PlayListSingleton.INSTANCE.setCurrentPosition(position);
            EvenCall evenCall = new EvenCall();
            evenCall.setCurrentOrder(EvenCall.PLAY);
            EventBus.getDefault().post(evenCall);
            notifyDataSetChanged();
        });
        holder.simpleDraweeView.setOnClickListener(v -> {
            Log.d("点击", "点击了simpleDraweeView");
        });
    }

    @Override
    public int getItemCount() {
        return musicList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        MaterialRippleLayout materialRippleLayout;
        TextView tv, tvsub;
        SimpleDraweeView simpleDraweeView;

        public MyViewHolder(View view) {
            super(view);
            materialRippleLayout = (MaterialRippleLayout) view.findViewById(R.id.ripple);
            tv = (TextView) view.findViewById(R.id.title);
            tvsub = (TextView) view.findViewById(R.id.subtitle);
            simpleDraweeView = (SimpleDraweeView) view.findViewById(R.id.more);
        }
    }
    public void onEventMainThread(EvenMusicListContentAdapterCall even){
        notifyDataSetChanged();
    }
}

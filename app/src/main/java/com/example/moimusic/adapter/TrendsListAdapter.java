package com.example.moimusic.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.moimusic.AppApplication;
import com.example.moimusic.R;
import com.example.moimusic.mvp.model.entity.Music;
import com.example.moimusic.mvp.model.entity.MusicList;
import com.example.moimusic.mvp.model.entity.Trends;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/3/25.
 */
public class TrendsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int TYPE_MUSIC = 0;
    public static final int TYPE_LIST = 1;
    public static final int TYPE_NORMAL = 2;
    public static final int TYPE_IMAGE = 3;
    public static final int TYPE_TITLE= 5;
    private View  TitleView;
    private Context context;
    private List<Trends> lists = new ArrayList<>();

    public TrendsListAdapter(List<Trends> lists) {
        this.lists = lists;
    }

    public void setTitleView(View titleView) {
        TitleView = titleView;
    }

    @Override
    public int getItemViewType(int position) {
        if (position==0)return TYPE_TITLE;
        if(lists.size()>1){
            if ("1".equals(lists.get(position-1).getType())) return TYPE_NORMAL;
            if ("2".equals(lists.get(position-1).getType())) return TYPE_IMAGE;
            if ("3".equals(lists.get(position-1).getType())) return TYPE_MUSIC;
        }

        return TYPE_LIST;
    }
    class MyTitleHolder extends RecyclerView.ViewHolder {


        public MyTitleHolder(View itemView) {
            super(itemView);
        }
    }
    class NormalViewHolder extends RecyclerView.ViewHolder {
        protected TextView name, time, content, replysNumber;
        protected SimpleDraweeView userImage;

        public NormalViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            time = (TextView) itemView.findViewById(R.id.time);
            content = (TextView) itemView.findViewById(R.id.content);
            replysNumber = (TextView) itemView.findViewById(R.id.replysNum);
            userImage = (SimpleDraweeView) itemView.findViewById(R.id.userImage);
        }
    }

    class ImageViewHolder extends NormalViewHolder {
        private SimpleDraweeView contentImage;

        public ImageViewHolder(View itemView) {
            super(itemView);
            contentImage = (SimpleDraweeView) itemView.findViewById(R.id.contentImage);
        }
    }

    class MusicViewHolder extends NormalViewHolder {
        private TextView musicName, musicSinger;
        private SimpleDraweeView musicImage;
        private ImageView play;

        public MusicViewHolder(View itemView) {
            super(itemView);

            musicName = (TextView) itemView.findViewById(R.id.musicName);
            musicSinger = (TextView) itemView.findViewById(R.id.musicSinger);
            musicImage = (SimpleDraweeView) itemView.findViewById(R.id.musicImage);
            play = (ImageView) itemView.findViewById(R.id.play);
        }
    }

    class ListViewHolder extends NormalViewHolder {
        private TextView ListName, ListSinger;
        private SimpleDraweeView ListImage;
        private ImageView play;

        public ListViewHolder(View itemView) {
            super(itemView);

            ListName = (TextView) itemView.findViewById(R.id.musicName);
            ListSinger = (TextView) itemView.findViewById(R.id.musicSinger);
            ListImage = (SimpleDraweeView) itemView.findViewById(R.id.musicImage);
            play = (ImageView) itemView.findViewById(R.id.play);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        if (viewType==TYPE_TITLE)return new MyTitleHolder(TitleView);
        if (viewType == TYPE_NORMAL) return new NormalViewHolder(LayoutInflater.from(
                AppApplication.context).inflate(R.layout.trends_view, parent,
                false));
        if (viewType == TYPE_MUSIC) return new MusicViewHolder(LayoutInflater.from(
                AppApplication.context).inflate(R.layout.trends_music_view, parent,
                false));
        if (viewType == TYPE_IMAGE) return new ImageViewHolder(LayoutInflater.from(
                AppApplication.context).inflate(R.layout.trends_image_view, parent,
                false));
        return new ListViewHolder(LayoutInflater.from(
                AppApplication.context).inflate(R.layout.trends_music_view, parent,
                false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_TITLE) {
            return;
        }
        Trends trends = lists.get(position-1);
        if (trends.getType()==null){
            return;
        }
        ((NormalViewHolder) holder).name.setText(trends.getUserid().getName());
        ((NormalViewHolder) holder).time.setText(trends.getCreatedAt());
        ((NormalViewHolder) holder).content.setText(trends.getContent());
        if (trends.getUserid().getImageFile()!=null){
            ((NormalViewHolder) holder).userImage.setImageURI(Uri.parse(trends.getUserid().getImageFile().getFileUrl(context)));
        }

        if (trends.getReplysNum()!=null){
            ((NormalViewHolder) holder).replysNumber.setText(trends.getReplysNum()+"");
        }else {
            ((NormalViewHolder) holder).replysNumber.setText(0+"");
        }

        if (getItemViewType(position) == TYPE_IMAGE) {
            ((ImageViewHolder) holder).contentImage.setImageURI(Uri.parse(trends.getImage().getFileUrl(context)));
        } else if (getItemViewType(position) == TYPE_MUSIC) {
            Music music = trends.getSongid();
            ((MusicViewHolder) holder).musicImage.setImageURI(Uri.parse(music.getMusicImageUri()));
            ((MusicViewHolder) holder).musicName.setText(music.getMusicName());
            ((MusicViewHolder) holder).musicSinger.setText(music.getSinger());
            ((MusicViewHolder) holder).play.setOnClickListener(v -> {

            });
        } else if (getItemViewType(position) == TYPE_LIST) {
            MusicList musicList = trends.getListid();
            if (musicList.getListImageUri()!=null){
                ((ListViewHolder) holder).ListImage.setImageURI(Uri.parse(musicList.getListImageUri()));
            }

            ((ListViewHolder) holder).ListName.setText(musicList.getName());
            ((ListViewHolder) holder).ListSinger.setText(musicList.getMoiUser().getName());
            ((ListViewHolder) holder).play.setOnClickListener(v -> {

            });
        }

    }

    @Override
    public int getItemCount() {
        return lists.size()+1;
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (getItemViewType(position) == TYPE_TITLE) {
                        return gridManager.getSpanCount();
                    } else return 1;
                }
            });
        }
    }
}

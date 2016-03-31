package com.example.moimusic.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.moimusic.AppApplication;
import com.example.moimusic.R;
import com.example.moimusic.mvp.model.entity.EvenCall;
import com.example.moimusic.mvp.model.entity.EvenMusicPlay;
import com.example.moimusic.mvp.model.entity.MoiUser;
import com.example.moimusic.mvp.model.entity.Music;
import com.example.moimusic.mvp.model.entity.MusicList;
import com.example.moimusic.mvp.model.entity.Trends;
import com.example.moimusic.play.PlayListSingleton;
import com.example.moimusic.ui.activity.ActivityMusicList;
import com.example.moimusic.ui.activity.ActivityNewTrends;
import com.example.moimusic.ui.activity.ActivityPlayNow;
import com.example.moimusic.ui.activity.ActivityTrendsContent;
import com.example.moimusic.ui.activity.LogActivity;
import com.example.moimusic.ui.activity.UserCenterActivity;
import com.facebook.drawee.view.SimpleDraweeView;
import com.vanniktech.emoji.EmojiTextView;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;
import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 2016/3/25.
 */
public class TrendsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int TYPE_MUSIC = 0;
    public static final int TYPE_LIST = 1;
    public static final int TYPE_NORMAL = 2;
    public static final int TYPE_IMAGE = 3;
    public static final int TYPE_TITLE = 5;
    private View TitleView;
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
        if (position == 0) return TYPE_TITLE;
        if (lists.size() >=1) {
            if ("4".equals(lists.get(position - 1).getType())) return TYPE_LIST;
            if ("2".equals(lists.get(position - 1).getType())) return TYPE_IMAGE;
            if ("3".equals(lists.get(position - 1).getType())) return TYPE_MUSIC;
            if ("1".equals(lists.get(position - 1).getType())) return TYPE_NORMAL;
        }

        return TYPE_NORMAL;
    }

    class MyTitleHolder extends RecyclerView.ViewHolder {


        public MyTitleHolder(View itemView) {
            super(itemView);
        }
    }

    class NormalViewHolder extends RecyclerView.ViewHolder {
        protected TextView name, time, replysNumber;
        protected EmojiTextView content;
        protected SimpleDraweeView userImage;
        protected LinearLayout linearLayout;

        public NormalViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            time = (TextView) itemView.findViewById(R.id.time);
            content = (EmojiTextView) itemView.findViewById(R.id.content);
            replysNumber = (TextView) itemView.findViewById(R.id.replysNum);
            userImage = (SimpleDraweeView) itemView.findViewById(R.id.userImage);
            linearLayout = (LinearLayout)itemView.findViewById(R.id.linearlayout);
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
private RelativeLayout areaMusic;
        public MusicViewHolder(View itemView) {
            super(itemView);
areaMusic = (RelativeLayout)itemView.findViewById(R.id.areaMusic);
            musicName = (TextView) itemView.findViewById(R.id.musicName);
            musicSinger = (TextView) itemView.findViewById(R.id.musicSinger);
            musicImage = (SimpleDraweeView) itemView.findViewById(R.id.musicImage);
        }
    }

    class ListViewHolder extends NormalViewHolder {
        private TextView ListName, ListSinger;
        private SimpleDraweeView ListImage;
        private RelativeLayout areaMusic;
        public ListViewHolder(View itemView) {
            super(itemView);
            areaMusic = (RelativeLayout)itemView.findViewById(R.id.areaMusic);
            ListName = (TextView) itemView.findViewById(R.id.musicName);
            ListSinger = (TextView) itemView.findViewById(R.id.musicSinger);
            ListImage = (SimpleDraweeView) itemView.findViewById(R.id.musicImage);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        if (viewType == TYPE_TITLE) return new MyTitleHolder(TitleView);
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
        Trends trends = lists.get(position - 1);
        if (trends.getType() == null) {
            return;
        }
        ((NormalViewHolder) holder).name.setText(trends.getUserid().getName());
        ((NormalViewHolder) holder).time.setText(trends.getCreatedAt());
        ((NormalViewHolder) holder).content.setText(trends.getContent());
        ((NormalViewHolder) holder).userImage.setOnClickListener(v1 -> {
            if (BmobUser.getCurrentUser(context,MoiUser.class)==null){
                context.startActivity(new Intent(context, LogActivity.class));
            }else {
                Intent intent = new Intent(context, UserCenterActivity.class);
                intent.putExtra("userID",trends.getUserid().getObjectId());
                context.startActivity(intent);
            }

        });
        ((NormalViewHolder) holder).linearLayout.setOnClickListener(v1 -> {
            Intent intent = new Intent(context, ActivityTrendsContent.class);
            intent.putExtra("TrendsObject",trends);
            context.startActivity(intent);
        });
        if (trends.getUserid().getImageFile() != null) {
            ((NormalViewHolder) holder).userImage.setImageURI(Uri.parse(trends.getUserid().getImageFile().getFileUrl(context)));
        }else {
            ((NormalViewHolder) holder).userImage.setImageURI(Uri.parse("null"));
        }

        if (trends.getReplysNum() != null) {
            ((NormalViewHolder) holder).replysNumber.setText(trends.getReplysNum() + "");
        } else {
            ((NormalViewHolder) holder).replysNumber.setText(0 + "");
        }

        if (getItemViewType(position) == TYPE_IMAGE) {
            if (trends.getType() == null) {
                return;
            }
            ((ImageViewHolder) holder).contentImage.setImageURI(Uri.parse(trends.getImage().getFileUrl(context)));
        } else if (getItemViewType(position) == TYPE_MUSIC) {
            if (trends.getType() == null) {
                return;
            }
            Music music = trends.getSongid();
            ((MusicViewHolder) holder).musicImage.setImageURI(Uri.parse(music.getMusicImageUri()));
            ((MusicViewHolder) holder).musicName.setText(music.getMusicName());
            ((MusicViewHolder) holder).musicSinger.setText(music.getSinger());
            ((MusicViewHolder) holder).areaMusic.setOnClickListener(v -> {
                final Music music1 = trends.getSongid();
                if (music1 != null) {
                    PlayListSingleton playListSingleton = PlayListSingleton.INSTANCE;
                    for (Music m : playListSingleton.getMusicList()) {
                        if (m.getObjectId().endsWith(music.getObjectId())) {
                            playListSingleton.getMusicList().remove(m);
                            break;
                        }
                    }
                    playListSingleton.getMusicList().add(music);
                    playListSingleton.setCurrentPosition(playListSingleton.getMusicList().size() - 1);
                    EvenCall evenCall = new EvenCall();
                    evenCall.setCurrentOrder(EvenCall.PLAY);
                    EventBus.getDefault().post(evenCall);
                    EventBus.getDefault().post(new EvenMusicPlay());

                    context.startActivity(new Intent(context, ActivityPlayNow.class));
                }
            });
        } else if (getItemViewType(position) == TYPE_LIST) {
            if (trends.getType() == null) {
                return;
            }
            MusicList musicList = trends.getListid();
            if (musicList.getListImageUri() != null) {
                ((ListViewHolder) holder).ListImage.setImageURI(Uri.parse(musicList.getListImageUri()));
            }

            ((ListViewHolder) holder).ListName.setText(musicList.getName());
            ((ListViewHolder) holder).ListSinger.setText(musicList.getMoiUser().getName());
            ((ListViewHolder) holder).areaMusic.setOnClickListener(v -> {
                Intent intent = new Intent(context, ActivityMusicList.class);
                intent.putExtra("musiclistid",trends.getListid().getObjectId());
                context.startActivity(intent);
            });
        }

    }

    @Override
    public int getItemCount() {
        return lists.size() + 1;
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

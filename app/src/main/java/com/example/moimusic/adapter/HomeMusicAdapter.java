package com.example.moimusic.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.example.moimusic.AppApplication;
import com.example.moimusic.R;
import com.example.moimusic.mvp.model.entity.EvenCall;
import com.example.moimusic.mvp.model.entity.EvenMusicPlay;
import com.example.moimusic.mvp.model.entity.Music;
import com.example.moimusic.mvp.model.entity.MusicList;
import com.example.moimusic.play.PlayListSingleton;
import com.example.moimusic.ui.activity.ActivityPlayNow;
import com.facebook.drawee.view.SimpleDraweeView;
import com.zhy.android.percent.support.PercentRelativeLayout;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by qqq34 on 2016/3/18.
 */
public class HomeMusicAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int TYPE_HEADER = 0;
    public static final int TYPE_NORMAL = 1;
    public static final int TYPE_TITLE1 = 2;
    public static final int TYPE_TITLE2 = 3;
    private View mHeaderView, Title1view, Title2View;
    private Context context;
    private List<Music> lists = new ArrayList<>();

    public HomeMusicAdapter(List<Music> lists) {
        this.lists = lists;
    }


    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
        notifyItemInserted(0);
    }

    public View getHeaderView() {
        return mHeaderView;
    }

    public View getTitle2View() {
        return Title2View;
    }

    public void setTitle2View(View title2View) {
        Title2View = title2View;
        notifyItemInserted(0);
    }

    public View getTitle1view() {
        return Title1view;
    }

    public void setTitle1view(View title1view) {
        Title1view = title1view;
        notifyItemInserted(0);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) return TYPE_HEADER;
        if (position == 1) return TYPE_TITLE1;
        if (position == 8) return TYPE_TITLE2;
        return TYPE_NORMAL;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        if (viewType == TYPE_HEADER) return new MyHeadHolder(mHeaderView);
        if (viewType == TYPE_TITLE1) return new MyTitle1Holder(Title1view);
        if (viewType == TYPE_TITLE2) return new MyTitle2Holder(Title2View);

        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                AppApplication.context).inflate(R.layout.music_view, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_HEADER || getItemViewType(position) == TYPE_TITLE2 || getItemViewType(position) == TYPE_TITLE1) {
            return;
        }
        final int pos = getRealPosition(position);
        final Music music = lists.get(pos);
        if (holder instanceof MyViewHolder) {
            ((MyViewHolder) holder).materialRippleLayout.setOnClickListener(v -> {
                if (music != null) {
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
            ((MyViewHolder) holder).imageView.setImageURI(Uri.parse(music.getMusicImageUri()));
            ((MyViewHolder) holder).name.setText(music.getMusicName());
            ((MyViewHolder) holder).singer.setText(music.getSinger());

        }


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
                    if (getItemViewType(position) == TYPE_TITLE1 || getItemViewType(position) == TYPE_TITLE2 || getItemViewType(position) == TYPE_HEADER) {
                        return gridManager.getSpanCount();
                    } else return 1;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return 15;
    }

    class MyHeadHolder extends RecyclerView.ViewHolder {

        public MyHeadHolder(View itemView) {
            super(itemView);
        }
    }

    class MyTitle1Holder extends RecyclerView.ViewHolder {


        public MyTitle1Holder(View itemView) {
            super(itemView);
        }
    }

    class MyTitle2Holder extends RecyclerView.ViewHolder {


        public MyTitle2Holder(View itemView) {
            super(itemView);
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        MaterialRippleLayout materialRippleLayout;
        TextView name, singer;
        PercentRelativeLayout percentRelativeLayout;
        SimpleDraweeView imageView;

        public MyViewHolder(View view) {
            super(view);
            materialRippleLayout = (MaterialRippleLayout) view.findViewById(R.id.header_image);
            name = (TextView) view.findViewById(R.id.name);
            singer = (TextView) view.findViewById(R.id.singer);
            percentRelativeLayout = (PercentRelativeLayout) view.findViewById(R.id.relativeLayout);
            imageView = (SimpleDraweeView) view.findViewById(R.id.simpleView);
        }
    }

    private int getRealPosition(int position) {
        if (position >= 2 && position <= 7) {
            return position - 2;
        } else {
            return position - 3;
        }
    }
}

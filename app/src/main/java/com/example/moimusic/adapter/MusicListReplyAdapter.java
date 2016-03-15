package com.example.moimusic.adapter;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
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
import com.example.moimusic.mvp.model.entity.IReply;
import com.example.moimusic.mvp.model.entity.MusicListReply;
import com.example.moimusic.ui.activity.LogActivity;
import com.example.moimusic.ui.activity.UserCenterActivity;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by qqq34 on 2016/2/25.
 */
public class MusicListReplyAdapter extends RecyclerView.Adapter<MusicListReplyAdapter.MyViewHolder> {
    private List<IReply> list;
    private FragmentActivity fragmentActivity;

    public MusicListReplyAdapter(List<IReply> list, FragmentActivity fragmentActivity) {
        this.list = list;
        this.fragmentActivity = fragmentActivity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                AppApplication.context).inflate(R.layout.comment_view, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if (list.get(position).getUser().getImageFile().getFileUrl(fragmentActivity)!=null){
            holder.simpleDraweeView.setImageURI(Uri.parse(list.get(position).getUser().getImageFile().getFileUrl(fragmentActivity)));
        }


        holder.simpleDraweeView.setOnClickListener(v -> {
            Intent intent = new Intent(fragmentActivity, UserCenterActivity.class);
            intent.putExtra("userID", list.get(position).getUser().getObjectId());
            fragmentActivity.startActivity(intent);
        });
        if (list.get(position).getUser().getName() != null) {
            holder.name.setText(list.get(position).getUser().getName());
        }
        if (list.get(position).getCreatedAt() != null) {
            holder.time.setText(list.get(position).getCreatedAt());
        }
        if (list.get(position).getContent() != null) {
            holder.content.setText(list.get(position).getContent());
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addData(List<IReply> list) {
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public void ClearData() {
        list.clear();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name, time, content;
        SimpleDraweeView simpleDraweeView;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            time = (TextView) view.findViewById(R.id.time);
            content = (TextView) view.findViewById(R.id.content);
            simpleDraweeView = (SimpleDraweeView) view.findViewById(R.id.userImage);
        }
    }
}

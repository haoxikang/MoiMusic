package com.example.moimusic.ui.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.example.moimusic.R;
import com.example.moimusic.adapter.CollectMusicListAdapter;
import com.example.moimusic.mvp.model.entity.MoiUser;
import com.example.moimusic.mvp.model.entity.Music;
import com.example.moimusic.mvp.model.entity.MusicList;
import com.example.moimusic.utils.ErrorList;
import com.gc.materialdesign.views.ButtonFlat;
import com.rey.material.app.Dialog;
import com.rey.material.app.DialogFragment;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import rx.Observable;

/**
 * Created by qqq34 on 2016/3/5.
 */
public class ListCollectBuild implements DialogFragment.Builder {
    private Dialog dialog;
    private String musicId;

    public ListCollectBuild(String musicId) {
        this.musicId = musicId;
    }

    @Override
    public Dialog build(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.collect_dialog, null, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        TextInputLayout textInputLayout = (TextInputLayout) view.findViewById(R.id.createList);
        ButtonFlat buttonFlat = (ButtonFlat) view.findViewById(R.id.ok);
        buttonFlat.setOnClickListener(v -> {
            if (TextUtils.isEmpty(textInputLayout.getEditText().getText())) {
                textInputLayout.setErrorEnabled(true);
                textInputLayout.setError(context.getResources().getString(R.string.noEmp));
            } else {
                Music music = new Music();
                music.setObjectId(musicId);
                BmobRelation relation = new BmobRelation();
                relation.add(music);
                MoiUser user = BmobUser.getCurrentUser(context, MoiUser.class);
                MusicList musicList = new MusicList();
                musicList.setMusic(relation);
                musicList.setRelease(false);
                musicList.setName(textInputLayout.getEditText().getText().toString());
                musicList.setMoiUser(user);
                musicList.save(context, new SaveListener() {

                    @Override
                    public void onSuccess() {
                        // TODO Auto-generated method stub
                        BmobRelation relation = new BmobRelation();
                        relation.add(musicList);
                        user.setMyMusicList(relation);
                        user.update(context, new UpdateListener() {
                            @Override
                            public void onSuccess() {
                                Toast.makeText(context, "收藏成功", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(int i, String s) {
                                Toast.makeText(context, "收藏失败", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        // TODO Auto-generated method stub
                        Toast.makeText(context, "收藏失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        MoiUser moiUser = BmobUser.getCurrentUser(context, MoiUser.class);
        BmobQuery<MusicList> query = new BmobQuery<>();
        query.addWhereRelatedTo("myMusicList", new BmobPointer(moiUser));
        query.findObjects(context, new FindListener<MusicList>() {
            @Override
            public void onSuccess(List<MusicList> list) {
                Log.d("TAG", "成功");
                if (list.size() != 0) {
                    CollectMusicListAdapter adapter = new CollectMusicListAdapter(list);
                    recyclerView.setAdapter(adapter);
                    adapter.setOnItemClickLitener(new CollectMusicListAdapter.OnItemClickLitener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            Music music = new Music();
                            music.setObjectId(musicId);
                            MusicList musicList = new MusicList();
                            musicList.setObjectId(list.get(position).getObjectId());
                            BmobRelation relation = new BmobRelation();
                            relation.add(music);
                            musicList.setMusic(relation);
                            musicList.update(context, new UpdateListener() {

                                @Override
                                public void onSuccess() {
                                    // TODO Auto-generated method stub
                                    if (dialog.isShowing()) {
                                        dialog.dismiss();
                                    }

                                    Toast.makeText(context, "收藏成功", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onFailure(int arg0, String arg1) {
                                    // TODO Auto-generated method stub
                                    if (dialog.isShowing()) {
                                        dialog.dismiss();
                                    }
                                    Toast.makeText(context, "收藏失败", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                        @Override
                        public void onItemLongClick(View view, int position) {

                        }
                    });
                }
            }

            @Override
            public void onError(int i, String s) {
            }
        });
        dialog = new Dialog(context)
                .title(context.getResources().getString(R.string.CollageTheList))
                .contentView(view)
                .positiveAction(context.getResources().getString(R.string.OK))
                .negativeAction(context.getResources().getString(R.string.cancel));

        return dialog;
    }

    @Override
    public void onPositiveActionClicked(DialogFragment fragment) {
        fragment.dismiss();
    }

    @Override
    public void onNegativeActionClicked(DialogFragment fragment) {
        fragment.dismiss();
    }

    @Override
    public void onNeutralActionClicked(DialogFragment fragment) {
        fragment.dismiss();
    }

    @Override
    public void onCancel(DialogInterface dialog) {

    }

    @Override
    public void onDismiss(DialogInterface dialog) {

    }
}

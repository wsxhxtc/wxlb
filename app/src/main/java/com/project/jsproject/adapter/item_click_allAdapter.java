package com.project.jsproject.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.project.jsproject.R;
import com.project.jsproject.entity.AllPlanEntity;
import com.superrecycleview.superlibrary.adapter.BaseViewHolder;
import com.superrecycleview.superlibrary.adapter.SuperBaseAdapter;

import java.util.List;

public class item_click_allAdapter extends SuperBaseAdapter<AllPlanEntity> {


    private final Context context;
    private final List<AllPlanEntity> data;

    public item_click_allAdapter(Context context, List<AllPlanEntity> data) {
        super(context, data);
        this.context=context;
        this.data=data;
    }

    @Override
    protected void convert(BaseViewHolder holder, final AllPlanEntity item, int position) {
        MediaMetadataRetriever media = new MediaMetadataRetriever();
// Uri uri=Uri.paese("android:resource://包名/"+R.raw.xxx);

 /*       Glide.with(context) .setDefaultRequestOptions(
                new RequestOptions()
                        .frame(4000000)
                        .centerCrop()
                                *//*.error(R.mipmap.eeeee)//可以忽略
                                .placeholder(R.mipmap.ppppp)//可以忽略*//*
        ).load()).into((ImageView) holder.getView(R.id.img));*/
        media.setDataSource(context, Uri.parse(item.getFrame()));
        Bitmap bitmap = media.getFrameAtTime();

        ImageView im = (ImageView) holder.getView(R.id.img);
        im.setImageBitmap(bitmap);
        holder.setText(R.id.group,item.getPlan());
        holder.setText(R.id.title,item.getTime());
        CheckBox mCk = (CheckBox) holder.getView(R.id.ck);
        mCk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                item.setChecked(b);

            }
        });
    }

    @Override
    protected int getItemViewLayoutId(int position, AllPlanEntity item) {
        return R.layout.item_click;
    }
    public List<AllPlanEntity>getList(){
        return data;
    }
}

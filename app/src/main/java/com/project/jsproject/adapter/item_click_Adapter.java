package com.project.jsproject.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.project.jsproject.R;
import com.project.jsproject.activity.TimerActivity;
import com.project.jsproject.entity.AllPlanEntity;
import com.project.jsproject.entity.PlanEntity;
import com.superrecycleview.superlibrary.adapter.BaseViewHolder;
import com.superrecycleview.superlibrary.adapter.SuperBaseAdapter;

import java.util.List;

public class item_click_Adapter extends SuperBaseAdapter<PlanEntity> {


    private final Context context;
    private final List<PlanEntity> data;

    public item_click_Adapter(Context context, List<PlanEntity> data) {
        super(context, data);
        this.context=context;
        this.data=data;
    }

    @Override
    protected void convert(BaseViewHolder holder, final PlanEntity item, int position) {
        holder.setOnClickListener(R.id.start, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(context, TimerActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        holder.setText(R.id.title,item.getPlan());
        holder.setText(R.id.group,item.getTime());
    }

    @Override
    protected int getItemViewLayoutId(int position, PlanEntity item) {
        return R.layout.item_;
    }
    public List<PlanEntity>getList(){
        return data;
    }
}

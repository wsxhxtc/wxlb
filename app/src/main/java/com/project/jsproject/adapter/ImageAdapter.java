package com.project.jsproject.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.project.jsproject.R;
import com.project.jsproject.activity.ActionActivity;
import com.project.jsproject.activity.OwnPlayActivity;
import com.superrecycleview.superlibrary.adapter.BaseViewHolder;
import com.superrecycleview.superlibrary.adapter.SuperBaseDragAdapter;

import java.util.List;

public class ImageAdapter extends SuperBaseDragAdapter<String> {

    private final List<String> data;
    private  Context context;
    private Long str;

    public ImageAdapter(Context context, List<String> data) {
        super(context, data);
        this.context=context;
        this.data=data;
    }
    public void setData(Long str){
        this.str=str;
    }
    @Override
    protected void convert(BaseViewHolder holder, String item, int position) {

        Glide.with(context).load(Uri.parse(data.get(position))).apply(new RequestOptions()
                .error(R.drawable.ic_launcher_background)
                .placeholder(R.drawable.ic_launcher_background)).into((ImageView) holder.getView(R.id.image));
        holder.setOnClickListener(R.id.item, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, OwnPlayActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("type",str);
                context.startActivity(intent);
                ((ActionActivity)context).finish();
            }
        });
    }

    @Override
    protected int getItemViewLayoutId(int position, String item) {
        return R.layout.image_layout;
    }
}

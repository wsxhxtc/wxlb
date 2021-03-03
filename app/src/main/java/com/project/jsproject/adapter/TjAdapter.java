package com.project.jsproject.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.project.jsproject.R;
import com.project.jsproject.activity.Player;
import com.project.jsproject.bean.tjbean;
import com.superrecycleview.superlibrary.adapter.BaseViewHolder;
import com.superrecycleview.superlibrary.adapter.SuperBaseAdapter;

import java.util.List;

public class TjAdapter extends SuperBaseAdapter<tjbean> {

    private  Context context;
    List<tjbean>getTj;

    public TjAdapter(Context context, List<tjbean> data) {
        super(context, data);
        this.getTj=data;
        this.context=context;
    }

    @Override
    protected void convert(BaseViewHolder holder, final tjbean item, int position) {
        holder.setText(R.id.x1,item.getTitle());
        holder.setText(R.id.x2,item.getGroup());
        holder.setText(R.id.pos,(position+1)+".");
        holder.setOnClickListener(R.id.item, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, Player.class);
                intent.putExtra("url",item.getI());
                context.startActivity(intent);
            }
        });
    }

    @Override
    protected int getItemViewLayoutId(int position, tjbean item) {
        return R.layout.tj_item_layout;
    }
}

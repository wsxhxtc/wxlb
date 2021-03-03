package com.project.jsproject.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.project.jsproject.R;
import com.project.jsproject.activity.OwnPlayActivity;
import com.project.jsproject.entity.ActionEntity;
import com.superrecycleview.superlibrary.adapter.BaseViewHolder;
import com.superrecycleview.superlibrary.adapter.SuperBaseAdapter;
import com.superrecycleview.superlibrary.recycleview.SuperRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ActionAdapter extends SuperBaseAdapter<ActionEntity> {

    private final Context context;
    private List<String>list=null;
    public ActionAdapter(Context context, List<ActionEntity> data) {
        super(context, data);
        this.context=context;
        list=new ArrayList<>();
    }

    @Override
    protected void convert(BaseViewHolder holder, final ActionEntity item, int position) {

        holder.setText(R.id.title,item.getTitle());
        holder.setText(R.id.time,item.getTime());

        List<Integer> as = new Gson().fromJson(item.getPlan(), new TypeToken<List<Integer>>(){}.getType());
        if(list.size()!=0){
            list.clear();
        }
        for(Integer i:as){

            if(i==R.mipmap.muscle_cb){
                list.add("android.resource://com.project.jsproject/"+i);
            }else if(i==R.mipmap.muscle_ea){
                list.add("android.resource://com.project.jsproject/"+i);
            }else if(i==R.mipmap.muscle_ga){
                list.add("android.resource://com.project.jsproject/"+i);
            }
        }


        SuperRecyclerView view =(SuperRecyclerView) holder.getView(R.id.sv2);
        LinearLayout mItem =(LinearLayout) holder.getView(R.id.item);
        view.setLoadMoreEnabled(false);
        view.setRefreshEnabled(false);
        view.setLayoutManager(new GridLayoutManager(context,3));
        ImageAdapter adapter =new ImageAdapter(context,list);
        adapter.setData(item.getId());
        view.setAdapter(adapter);

       /* mItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, OwnPlayActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("type",item.getId());
                context.startActivity(intent);
            }
        });*/

    }

    @Override
    protected int getItemViewLayoutId(int position, ActionEntity item) {
        return R.layout.action_item_layout;
    }
}

package com.project.jsproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.project.jsproject.R;
import com.project.jsproject.adapter.item_click_Adapter;
import com.project.jsproject.adapter.item_click_allAdapter;
import com.project.jsproject.api.BaseUrl;
import com.project.jsproject.entity.AllPlanEntity;
import com.project.jsproject.entity.PlanEntity;
import com.project.jsproject.utils.DbController;
import com.superrecycleview.superlibrary.recycleview.SuperRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class OwnPlayActivity extends AppCompatActivity {

    private List<AllPlanEntity> list = new ArrayList<>();


    private SuperRecyclerView mSv1;
    private long actionid;
    private List<PlanEntity> data;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tas_layout);
        actionid=getIntent().getLongExtra("type",-1);
        mSv1 = (SuperRecyclerView) findViewById(R.id.sv1);
        mSv1.setLayoutManager(new LinearLayoutManager(this));

        TextView mSc = (TextView) findViewById(R.id.bc);
        mSc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), AlllPlayActivity.class);
                intent.putExtra("type",actionid);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        //获取所有的数据
        getList();
        item_click_Adapter adapter=new item_click_Adapter(this,data);
        mSv1.setAdapter(adapter);
        mSv1.setRefreshEnabled(false);
        mSv1.setLoadMoreEnabled(false);
    }

    public void getList() {
       data= DbController.getInstance(this).searchPlanAll(BaseUrl.username,actionid);
    }
}

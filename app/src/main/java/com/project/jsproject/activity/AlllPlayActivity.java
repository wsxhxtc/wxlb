package com.project.jsproject.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.project.jsproject.R;
import com.project.jsproject.adapter.item_click_allAdapter;
import com.project.jsproject.api.BaseUrl;
import com.project.jsproject.entity.AllPlanEntity;
import com.project.jsproject.entity.PlanEntity;
import com.project.jsproject.utils.DbController;
import com.superrecycleview.superlibrary.recycleview.SuperRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AlllPlayActivity extends AppCompatActivity {

    private List<AllPlanEntity> list = new ArrayList<>();


    private List<String> stj = new ArrayList<String>();
    private List<String> title = new ArrayList<String>();
    private List<Integer> istj = new ArrayList<Integer>();

    private SuperRecyclerView mSv1;
    private item_click_allAdapter item_click;
    private Long actionid;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_layout);
        actionid=getIntent().getLongExtra("type",-1);
        mSv1 = (SuperRecyclerView) findViewById(R.id.sv1);
        mSv1.setRefreshEnabled(false);
        mSv1.setLoadMoreEnabled(false);
        TextView mSc = (TextView) findViewById(R.id.bc);
        mSc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(item_click!=null){
                    List<AllPlanEntity> data = item_click.getList();
                    for(AllPlanEntity allPlanEntity:data){
                        if(allPlanEntity.getChecked()){
                            PlanEntity planEntity =new PlanEntity();
                            planEntity.setActionid(String.valueOf(actionid));
                            planEntity.setFrame(allPlanEntity.getFrame());
                            planEntity.setPlan(allPlanEntity.getPlan());
                            planEntity.setTime(allPlanEntity.getTime());
                            planEntity.setUsername(BaseUrl.username);
                            DbController.getInstance(getApplicationContext()).insertOrReplace(planEntity);
                        }
                    }

                }
                Toast.makeText(AlllPlayActivity.this, "操作完成", Toast.LENGTH_SHORT).show();
            }
        });
        mSv1.setLayoutManager(new LinearLayoutManager(this));
        stj.add("坐姿哑铃屈臂伸");
        stj.add("仰卧屈臂伸");
        stj.add("俯身支撑屈臂伸");
        title.add("三头肌");
        title.add("胸肌");
        title.add("大腿");
        istj.add(R.raw.aaa1);
        istj.add(R.raw.aaa2);
        istj.add(R.raw.aaa3);


        stj.add("哑铃卧推");
        stj.add("下斜哑铃飞鸟");
        stj.add("哑铃仰卧屈臂伸");

        istj.add(R.raw.aab1);
        istj.add(R.raw.aab2);
        istj.add(R.raw.aab3);


        stj.add("哑铃深蹲");
        stj.add("哑铃弓步蹲");
        stj.add("哑铃前弓步");

        istj.add(R.raw.had1);
        istj.add(R.raw.had2);
        istj.add(R.raw.had3);

        //获取所有的数据
        getList();

        item_click =new item_click_allAdapter(this,list);
        mSv1.setAdapter(item_click);
    }

    public void getList() {
        AllPlanEntity allPlanEntity = null;
        for (int i = 0; i < 9; i++) {
            allPlanEntity = new AllPlanEntity();
            allPlanEntity.setChecked(false);
            allPlanEntity.setFrame("android.resource://com.project.jsproject/" + istj.get(i));
            allPlanEntity.setPlan(stj.get(i));
            allPlanEntity.setTime(stj.get(i));
            if (i < 3) {
                allPlanEntity.setPlan(title.get(0));
            } else if (i < 6) {
                allPlanEntity.setPlan(title.get(1));
            } else {
                allPlanEntity.setPlan(title.get(2));
            }
            list.add(allPlanEntity);
        }

    }
}

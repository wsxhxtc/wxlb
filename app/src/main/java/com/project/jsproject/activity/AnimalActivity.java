package com.project.jsproject.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.project.jsproject.R;
import com.project.jsproject.adapter.TjAdapter;
import com.project.jsproject.bean.tjbean;
import com.superrecycleview.superlibrary.recycleview.SuperRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AnimalActivity extends AppCompatActivity {

    private List<tjbean> list = null;
    private List<String> stj = new ArrayList<String>();
    private List<Integer> istj = new ArrayList<Integer>();
    private List<String> sj = new ArrayList<String>();
    private List<Integer> isj = new ArrayList<Integer>();
    private List<String> dt = new ArrayList<String>();
    private List<Integer> idt = new ArrayList<Integer>();
    private SuperRecyclerView mSv;
    private TjAdapter tjAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animal_layout);
        list = new ArrayList<>();
        int mtype = getIntent().getIntExtra("type", -1);
        //根据id,获取不同的数据

        stj.add("坐姿哑铃屈臂伸");
        stj.add("仰卧屈臂伸");
        stj.add("俯身支撑屈臂伸");

        istj.add(R.raw.aaa1);
        istj.add(R.raw.aaa2);
        istj.add(R.raw.aaa3);


        sj.add("哑铃卧推");
        sj.add("下斜哑铃飞鸟");
        sj.add("哑铃仰卧屈臂伸");

        isj.add(R.raw.aab1);
        isj.add(R.raw.aab2);
        isj.add(R.raw.aab3);


        dt.add("哑铃深蹲");
        dt.add("哑铃弓步蹲");
        dt.add("哑铃前弓步");

        idt.add(R.raw.had1);
        idt.add(R.raw.had2);
        idt.add(R.raw.had3);
        getList(mtype);
        //根据不同的加载不同的动画，用弹窗
        mSv=(SuperRecyclerView)findViewById(R.id.sv1);
        mSv.setLayoutManager(new LinearLayoutManager(this));
        tjAdapter= new TjAdapter(this,list);
        mSv.setAdapter(tjAdapter);
    }

    public List<tjbean> getList(int type) {
        tjbean tjbean = null;
        switch (type) {
            case 1:
                for (int i = 0; i < 3; i++) {
                    tjbean = new tjbean();
                    tjbean.setGroup("三头肌");
                    tjbean.setTitle(stj.get(i));
                    tjbean.setI(istj.get(i));
                    list.add(tjbean);
                }
                break;
            case 2:
                for (int i = 0; i < 3; i++) {
                    tjbean = new tjbean();
                    tjbean.setGroup("胸肌");
                    tjbean.setTitle(sj.get(i));
                    tjbean.setI(isj.get(i));
                    list.add(tjbean);
                }
                break;
            case 3:
                for (int i = 0; i < 3; i++) {
                    tjbean = new tjbean();
                    tjbean.setGroup("大腿");
                    tjbean.setTitle(dt.get(i));
                    tjbean.setI(idt.get(i));
                    list.add(tjbean);
                }
                break;
        }
        return list;
    }
}

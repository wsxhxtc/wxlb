package com.project.jsproject.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.othershe.calendarview.bean.DateBean;
import com.othershe.calendarview.listener.OnPagerChangeListener;
import com.othershe.calendarview.listener.OnSingleChooseListener;
import com.othershe.calendarview.weiget.CalendarView;
import com.project.jsproject.R;
import com.project.jsproject.api.BaseUrl;
import com.project.jsproject.entity.ActionEntity;
import com.project.jsproject.utils.DbController;

import java.util.ArrayList;
import java.util.List;

public class AddTaskActivity extends AppCompatActivity {
    private RadioButton mRadioButton1, mRadioButton2, mRadioButton3;
    private List<Integer> list;
    private String time = "";
    private List<String>title;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        list = new ArrayList<>();
        title= new ArrayList<>();
        setContentView(R.layout.addtask);
        mRadioButton1 = (RadioButton) findViewById(R.id.radioButton1);
        mRadioButton2 = (RadioButton) findViewById(R.id.radioButton2);
        mRadioButton3 = (RadioButton) findViewById(R.id.radioButton3);
        CalendarView calendarView = (CalendarView) findViewById(R.id.calendar);
        TextView apply = (TextView) findViewById(R.id.apply);
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mRadioButton1.isChecked()) {
                    list.add(R.mipmap.muscle_cb);
                    title.add("三头肌");
                }
                if (mRadioButton2.isChecked()) {
                    title.add("胸肌");
                    list.add(R.mipmap.muscle_ea);
                }
                if (mRadioButton3.isChecked()) {
                    title.add("大腿");
                    list.add(R.mipmap.muscle_ga);
                }

                if (time.equals("")) {
                    Toast.makeText(AddTaskActivity.this, "请选择时间", Toast.LENGTH_SHORT).show();
                    return;
                }
                ActionEntity actionEntity = new ActionEntity();
                actionEntity.setTime(time);
                actionEntity.setPlan(new Gson().toJson(list));
                actionEntity.setName(BaseUrl.username);
                StringBuffer stringBuffer=null;
                stringBuffer=new StringBuffer();
                for(int i=0;i<title.size();i++){
                    stringBuffer.append(title.get(i));
                }
                if(stringBuffer==null){
                    stringBuffer.append("自由活动");
                }
                actionEntity.setTitle(stringBuffer.toString());
                List<ActionEntity> data = DbController.getInstance(getApplicationContext()).searchByNametime(BaseUrl.username, time);
                if ( data.size() == 0) {
                    DbController.getInstance(getApplicationContext()).insert(actionEntity);
                    Toast.makeText(AddTaskActivity.this, "数据添加成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AddTaskActivity.this, "数据已经失败", Toast.LENGTH_SHORT).show();
                }

            }
        });

        /**
         * 如果是1月请修改 2021.01或者2021.1尝试
         */
        calendarView
                .setStartEndDate("2020.12", "2099.12")
                .setInitDate("2017.11")
                .setSingleDate("2017.12.12")
                .init();

        //月份切换回调
        calendarView.setOnPagerChangeListener(new OnPagerChangeListener() {
            @Override
            public void onPagerChanged(int[] date) {

            }
        });

        calendarView.setOnSingleChooseListener(new OnSingleChooseListener() {
            @Override
            public void onSingleChoose(View view, DateBean date) {
                int y = date.getSolar()[0];
                int m = date.getSolar()[1];
                int d = date.getSolar()[2];
                time = String.valueOf(y) + String.valueOf(m) + String.valueOf(d);
            }
        });
    }
}

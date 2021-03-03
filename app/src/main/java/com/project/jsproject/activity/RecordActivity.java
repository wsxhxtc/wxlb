package com.project.jsproject.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.othershe.calendarview.bean.DateBean;
import com.othershe.calendarview.listener.OnPagerChangeListener;
import com.othershe.calendarview.listener.OnSingleChooseListener;
import com.othershe.calendarview.weiget.CalendarView;
import com.project.jsproject.R;
import com.project.jsproject.api.BaseUrl;
import com.project.jsproject.entity.ResultEntity;
import com.project.jsproject.utils.DbController;

import java.util.List;

public class RecordActivity extends AppCompatActivity {
    private TextView mRecord;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record_layout);

        CalendarView calendarView = (CalendarView) findViewById(R.id.calendar);
        mRecord= (TextView) findViewById(R.id.record);
        //日历init，年月日之间用点号隔开
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
                int y=date.getSolar()[0];
                int m=date.getSolar()[1];
                mRecord.setText("您当前训练的时间暂无");
                mRecord.setTextColor(getResources().getColor(R.color.green_4d));
                String str1="";
                if(m<10){
                    str1 ="0"+m;
                }else{
                    str1=String.valueOf(m);
                }

                int d=date.getSolar()[2];
                String day="";
                if(m<10){
                    day="0"+d;
                }else{
                    day=String.valueOf(d);
                }
                String time = String.valueOf(y) + str1 + day;
                List<ResultEntity> list = DbController.getInstance(RecordActivity.this).searchTimerAll(BaseUrl.username);
                for(int i=0;i<list.size();i++){
                    if(list.get(i).getTime().equals(time)){
                       if( list.get(i).getContuinetime()/60==0){
                           mRecord.setText("您当前训练的时间不足一分钟");
                       }else {
                           mRecord.setText("您当前训练的时间：" + list.get(i).getContuinetime() / 60 + "分钟");
                       }
                        if(Integer.valueOf(list.get(i).getContuinetime())<25*60){
                            mRecord.setTextColor(getResources().getColor(R.color.green_4d));
                        }else{
                            mRecord.setTextColor(getResources().getColor(R.color.red_d4));
                        }
                    }
                }

            }
        });
    }
    /**
     *  根据日期10-01-11 三段式时间判断
     *  显示时间在title
     *  mRecord==========================
     *
     */

}

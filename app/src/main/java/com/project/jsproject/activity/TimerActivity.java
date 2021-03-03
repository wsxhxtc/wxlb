package com.project.jsproject.activity;

import android.animation.TimeAnimator;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.othershe.calendarview.bean.DateBean;
import com.othershe.calendarview.listener.OnPagerChangeListener;
import com.othershe.calendarview.listener.OnSingleChooseListener;
import com.othershe.calendarview.weiget.CalendarView;
import com.project.jsproject.R;
import com.project.jsproject.api.BaseUrl;
import com.project.jsproject.entity.ResultEntity;
import com.project.jsproject.utils.DbController;

import java.text.DecimalFormat;
import java.util.Timer;
import java.util.TimerTask;

public class TimerActivity extends AppCompatActivity {
    private TextView mTimerView;
    private long baseTimer;
    private String mtime;
    private AlertDialog dialog;
    private String mm;
    private TextView mSuggest;
    private int time;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.time_layout);
        initView();

        initData();
    }

    private void initData() {
        mSuggest=(TextView)findViewById(R.id.suggest);
        mTimerView=(TextView)findViewById(R.id.timerView);
        mSuggest.setText(BaseUrl.SetttingTime);
        findViewById(R.id.apply).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show();
            }
        });
    }
    public void show(){

        View view = LayoutInflater.from(this).inflate(R.layout.timepack, null);
        CalendarView calendarView = (CalendarView)view.findViewById(R.id.calendar);

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
                mtime=String.valueOf(y)+str1+day;
            }
        });

        dialog=new AlertDialog.Builder(this).setView(view).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(!mtime.equals("0")) {
                    ResultEntity resultEntity =new ResultEntity();
                    resultEntity.setName(BaseUrl.username);
                    resultEntity.setContuinetime(time);
                    resultEntity.setTime(mtime);
                    DbController.getInstance(TimerActivity.this).insertOrReplace(resultEntity);
                    Toast.makeText(TimerActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialog.dismiss();
            }
        }).show();
    }
    private void initView() {
        baseTimer = SystemClock.elapsedRealtime();

        final Handler startTimehandler = new Handler(){
            public void handleMessage(android.os.Message msg) {
                if (null != mTimerView) {
                    mTimerView.setText((String) msg.obj);
                }
            }
        };
        new Timer("开机计时器").scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                time = (int)((SystemClock.elapsedRealtime() - baseTimer) / 1000);
                String hh = new DecimalFormat("00").format(time / 3600);
                mm = new DecimalFormat("00").format(time % 3600 / 60);
                String ss = new DecimalFormat("00").format(time % 60);
                String timeFormat = new String(hh + ":" + mm + ":" + ss);
                Message msg = new Message();
                msg.obj = timeFormat;
                startTimehandler.sendMessage(msg);
            }

        }, 0, 1000L);
    }
}

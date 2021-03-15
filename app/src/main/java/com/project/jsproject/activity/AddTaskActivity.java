package com.project.jsproject.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AddTaskActivity extends AppCompatActivity {

  private CheckBox mCb1, mCb2, mCb3;
  private TextView tvSelectDate, datePageRange;
  private List<Integer> list;
  private String time = "";
  private List<String> title;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    list = new ArrayList<>();
    title = new ArrayList<>();
    setContentView(R.layout.addtask);
    mCb1 = findViewById(R.id.cb1);
    mCb2 = findViewById(R.id.cb2);
    mCb3 = findViewById(R.id.cb3);
    tvSelectDate = findViewById(R.id.date_select);
    datePageRange = findViewById(R.id.date_page_range);
    final CalendarView calendarView = findViewById(R.id.calendar);
    TextView apply = findViewById(R.id.apply);
    apply.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if (mCb1.isChecked()) {
          list.add(R.mipmap.muscle_cb);
          title.add("三头肌");
        }
        if (mCb2.isChecked()) {
          title.add("胸肌");
          list.add(R.mipmap.muscle_ea);
        }
        if (mCb3.isChecked()) {
          title.add("大腿");
          list.add(R.mipmap.muscle_ga);
        }

        if (TextUtils.isEmpty(time)) {
          Toast.makeText(AddTaskActivity.this, "请选择时间", Toast.LENGTH_SHORT).show();
          return;
        }
        ActionEntity actionEntity = new ActionEntity();
        actionEntity.setTime(time);
        actionEntity.setPlan(new Gson().toJson(list));
        actionEntity.setName(BaseUrl.username);
        StringBuffer stringBuffer = null;
        stringBuffer = new StringBuffer();
        for (int i = 0; i < title.size(); i++) {
          stringBuffer.append(title.get(i));
        }
        if (stringBuffer == null) {
          stringBuffer.append("自由活动");
        }
        actionEntity.setTitle(stringBuffer.toString());
        List<ActionEntity> data = DbController.getInstance(getApplicationContext())
            .searchByNametime(BaseUrl.username, time);
        if (data.size() == 0) {
          DbController.getInstance(getApplicationContext()).insert(actionEntity);
          Toast.makeText(AddTaskActivity.this, "数据添加成功", Toast.LENGTH_SHORT).show();
        } else {
          Toast.makeText(AddTaskActivity.this, "数据已经失败", Toast.LENGTH_SHORT).show();
        }

      }
    });
    DateFormat initDateFormat = new SimpleDateFormat("yyyy.MM");
    DateFormat singleDateFormat = new SimpleDateFormat("yyyy.MM.dd");
    final DateFormat datePageRangeFormat = new SimpleDateFormat("yyyy年MM月");
    final DateFormat selectDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Date now = Calendar.getInstance().getTime();
    time = selectDateFormat.format(now);
    tvSelectDate.setText(time);
    datePageRange.setText(datePageRangeFormat.format(now));

    //月份切换回调
    calendarView.setOnPagerChangeListener(new OnPagerChangeListener() {
      @Override
      public void onPagerChanged(int[] date) {
        int y = date[0];
        int m = date[1];
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, y);
        c.set(Calendar.MONTH, m - 1);
        if (date.length > 2) {
          c.set(Calendar.DAY_OF_MONTH, date[2]);
        }
        datePageRange.setText(datePageRangeFormat.format(c.getTime()));
      }
    });

    calendarView.setOnSingleChooseListener(new OnSingleChooseListener() {
      @Override
      public void onSingleChoose(View view, DateBean date) {
        int y = date.getSolar()[0];
        int m = date.getSolar()[1];
        int d = date.getSolar()[2];
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, y);
        c.set(Calendar.MONTH, m - 1);
        c.set(Calendar.DAY_OF_MONTH, d);
        time = selectDateFormat.format(c.getTime());
        tvSelectDate.setText(time);
      }
    });

    /**
     * 如果是1月请修改 2021.01或者2021.1尝试
     */
    calendarView
        .setStartEndDate("2020.12", "2099.12")
        .setInitDate(initDateFormat.format(now))
        .setSingleDate(singleDateFormat.format(now))
        .init();

  }
}

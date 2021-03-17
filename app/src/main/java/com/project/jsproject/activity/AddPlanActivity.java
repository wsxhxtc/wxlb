package com.project.jsproject.activity;

import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatEditText;
import com.othershe.calendarview.weiget.CalendarView;
import com.project.jsproject.Commons;
import com.project.jsproject.R;
import com.project.jsproject.base.BaseActivity;
import com.project.jsproject.entity.ActionPlan;

import com.project.jsproject.utils.Log;
import com.project.jsproject.utils.Utils;
import com.project.jsproject.viewmodel.AddPlanViewModel;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddPlanActivity extends BaseActivity<AddPlanViewModel> {

  private AppCompatEditText editTitle;
  private CheckBox mCb1, mCb2, mCb3;
  private TextView tvSelectDate, datePageRange;
  private CalendarView calendarView;
  private String time = "";
  private long expireTime;

  @NonNull
  @Override
  protected Class<AddPlanViewModel> getViewModelClass() {
    return AddPlanViewModel.class;
  }

  @Override
  protected int getLayoutId() {
    return R.layout.addtask;
  }

  @Override
  protected void initView() {
    editTitle = findViewById(R.id.edit_plan_title);
    mCb1 = findViewById(R.id.cb1);
    mCb2 = findViewById(R.id.cb2);
    mCb3 = findViewById(R.id.cb3);
    tvSelectDate = findViewById(R.id.date_select);
    datePageRange = findViewById(R.id.date_page_range);
    calendarView = findViewById(R.id.calendar);

    DateFormat initDateFormat = new SimpleDateFormat("yyyy.MM");
    DateFormat singleDateFormat = new SimpleDateFormat("yyyy.MM.dd");
    final DateFormat datePageRangeFormat = new SimpleDateFormat("yyyy年MM月");
    final DateFormat selectDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Date now = Calendar.getInstance().getTime();
    time = selectDateFormat.format(now);
    expireTime = Utils.getDateEnd(now).getTime();
    tvSelectDate.setText(time);
    datePageRange.setText(datePageRangeFormat.format(now));

    //月份切换回调
    calendarView.setOnPagerChangeListener(date -> {
      int y = date[0];
      int m = date[1];
      Calendar c = Calendar.getInstance();
      c.set(Calendar.YEAR, y);
      c.set(Calendar.MONTH, m - 1);
      if (date.length > 2) {
        c.set(Calendar.DAY_OF_MONTH, date[2]);
      }
      datePageRange.setText(datePageRangeFormat.format(c.getTime()));
    });

    calendarView.setOnSingleChooseListener((view, date) -> {
      int y = date.getSolar()[0];
      int m = date.getSolar()[1];
      int d = date.getSolar()[2];
      Calendar c = Calendar.getInstance();
      c.set(Calendar.YEAR, y);
      c.set(Calendar.MONTH, m - 1);
      c.set(Calendar.DAY_OF_MONTH, d);
      Date selectDate = c.getTime();
      time = selectDateFormat.format(selectDate);
      expireTime = Utils.getDateEnd(selectDate).getTime();
      tvSelectDate.setText(time);
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

  private ActionPlan createPlan() {
    String title = editTitle.getText().toString().trim();
    if (TextUtils.isEmpty(title)) {
      showToast("没有设置训练计划title");
      return null;
    }
    int categories = 0;
    if (mCb1.isChecked()) {
      categories |= Commons.CATEGORY_STJ;
    }
    if (mCb2.isChecked()) {
      categories |= Commons.CATEGORY_XJ;
    }
    if (mCb3.isChecked()) {
      categories |= Commons.CATEGORY_DT;
    }
    if (categories <= 0) {
      showToast("没有选择锻炼部位");
      return null;
    }
    if (expireTime <= 0) {
      showToast("没有选择计划截止日期");
      return null;
    }
    ActionPlan plan = new ActionPlan();
    plan.title = title;
    plan.createTime = System.currentTimeMillis();
    plan.expireTime = expireTime;
    plan.categories = categories;

    return plan;
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_right, menu);
    MenuItem item = menu.findItem(R.id.action_right);
    TextView view = item.getActionView().findViewById(R.id.tv_menu_action_right);
    view.setText("保存");
    view.setOnClickListener(v -> save());
    return true;
  }

  private void save() {
    ActionPlan plan = createPlan();
    if (plan != null) {
      addDisposable(
          viewModel
              .addPlan(plan)
              .subscribe(() -> {
                showToast("数据添加成功");
                finish();
              }, throwable -> {
                Log.e(throwable.getMessage());
                showToast("数据已经失败");
              }));
    }
  }
}

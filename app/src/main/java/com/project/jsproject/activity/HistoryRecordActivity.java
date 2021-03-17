package com.project.jsproject.activity;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.util.Pair;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarLayout;
import com.haibin.calendarview.CalendarView;

import com.haibin.calendarview.CalendarView.OnCalendarSelectListener;
import com.haibin.calendarview.CalendarView.OnMonthChangeListener;
import com.haibin.calendarview.CalendarView.OnYearChangeListener;
import com.project.jsproject.R;
import com.project.jsproject.adapter.ExerciseRecordsAdapter;
import com.project.jsproject.base.BaseActivity;
import com.project.jsproject.entity.ExerciseRecord;
import com.project.jsproject.entity.PlanTask;
import com.project.jsproject.utils.Log;
import com.project.jsproject.utils.Utils;
import com.project.jsproject.viewmodel.HistoryRecordViewModel;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.subjects.BehaviorSubject;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.reactivestreams.Publisher;

/**
 * 使用到的第三方开源库： https://github.com/huanghaibin-dev/CalendarView
 */
public class HistoryRecordActivity extends BaseActivity<HistoryRecordViewModel> implements
    OnCalendarSelectListener,
    OnYearChangeListener, OnMonthChangeListener {

  private TextView mTextMonthDay;
  private TextView mTextYear;
  private TextView mTextLunar;
  private TextView mTextCurrentDay;
  private CalendarView mCalendarView;
  private RelativeLayout mRelativeTool;
  private CalendarLayout mCalendarLayout;
  private TextView mRecord;
  private RecyclerView listView;

  private ExerciseRecordsAdapter adapter;

  private int mYear;
  private int mMonth;
  private int mDay;

  private BehaviorSubject<Date> selectDateChangedBs = BehaviorSubject.create();
  private BehaviorSubject<Integer> monthChangedBs = BehaviorSubject.create();

  private Map<String, Calendar> map = new HashMap<>();

  private static final int SELECTED_COLOR = 0xfff54a00;
  private static final int GRATE_COLOR = 0x7f00ff00;
  private static final int GOOD_COLOR = 0xee00ff00;
  private static final int BAD_COLOR = 0x7fff0000;
  private static final int WORST_COLOR = 0xccff0000;

  @Override
  protected Class<HistoryRecordViewModel> getViewModelClass() {
    return HistoryRecordViewModel.class;
  }

  @Override
  protected int getLayoutId() {
    return R.layout.record_layout;
  }

  @Override
  protected void initView() {
    mTextMonthDay = findViewById(R.id.tv_month_day);
    mTextYear = findViewById(R.id.tv_year);
    mTextLunar = findViewById(R.id.tv_lunar);
    mRelativeTool = findViewById(R.id.rl_tool);
    mCalendarView = findViewById(R.id.calendarView);
    mTextCurrentDay = findViewById(R.id.tv_current_day);
    mTextMonthDay.setOnClickListener(v -> {
      if (!mCalendarLayout.isExpand()) {
        mCalendarLayout.expand();
        return;
      }
      mCalendarView.showYearSelectLayout(mYear);
      mTextLunar.setVisibility(View.GONE);
      mTextYear.setVisibility(View.GONE);
      mTextMonthDay.setText(String.valueOf(mYear));
    });
    findViewById(R.id.fl_current).setOnClickListener(v -> mCalendarView.scrollToCurrent());
    mRecord = findViewById(R.id.record);
    listView = findViewById(R.id.date_records);
    listView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
    listView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    adapter = new ExerciseRecordsAdapter(this);
    listView.setAdapter(adapter);
    mCalendarLayout = findViewById(R.id.calendarLayout);
    mCalendarView.setOnCalendarSelectListener(this);
    mCalendarView.setOnYearChangeListener(this);
    mCalendarView.setOnMonthChangeListener(this);
    mTextYear.setText(String.valueOf(mCalendarView.getCurYear()));
    mYear = mCalendarView.getCurYear();
    mMonth = mCalendarView.getCurMonth();
    mDay = mCalendarView.getCurDay();
    mTextMonthDay.setText(String.format("%d月%d日", mMonth, mDay));
    mTextLunar.setText("今日");
    mTextCurrentDay.setText(String.valueOf(mDay));
    selectDateChangedBs.onNext(Utils.getDate(mYear, mMonth, mDay));
    monthChangedBs.onNext(mMonth);
  }

  @Override
  protected void initSubscriptions() {
    super.initSubscriptions();
    addDisposable(selectDateChangedBs.toFlowable(BackpressureStrategy.LATEST)
        .flatMap((Function<Date, Publisher<List<ExerciseRecord>>>) date -> viewModel
            .getDateRecords(date))
        .flatMap(
            (Function<List<ExerciseRecord>, Publisher<Pair<List<ExerciseRecord>, List<PlanTask>>>>) records -> viewModel
                .getTasksRecordsBelongsTo(records).map(
                    tasks -> Pair.create(records, tasks)))
        .subscribe(pair -> calculateDateRecords(pair.first, pair.second),
            throwable -> Log.e(throwable.getMessage())));
    addDisposable(monthChangedBs.toFlowable(BackpressureStrategy.LATEST)
        .flatMap((Function<Integer, Publisher<List<ExerciseRecord>>>) month -> {
          int preYear;
          int preMonth;
          int nextYear;
          int nextMonth;
          if (month == 1) {
            preYear = mYear - 1;
            preMonth = 12;
          } else {
            preYear = mYear;
            preMonth = mMonth - 1;
          }
          if (month == 12) {
            nextYear = preYear + 1;
            nextMonth = 1;
          } else {
            nextYear = mYear;
            nextMonth = mMonth + 1;
          }
          return viewModel
              .getMonthRecords(Utils.getFirstDateOfMonth(preYear, preMonth),
                  Utils.getLastDateOfMonth(nextYear, nextMonth));
        })
        .subscribe(this::calculateMonthRecords, throwable -> {
          Log.e(throwable.getMessage());
        }));
  }

  private void calculateDateRecords(List<ExerciseRecord> records, List<PlanTask> tasks) {
    Log.d("calculateDateRecords " + records.size() + " 条数据");
    adapter.updateData(records, tasks);
    if (records.isEmpty()) {
      mRecord.setText(String
          .format("%s\t没有锻炼", Utils.getFormatDate(Utils.getDate(mYear, mMonth, mDay).getTime())));
      return;
    }
    int sum = sumDateExerciseDuration(records);
    if (sum < 30) {
      mRecord.setText(String.format("%s\t没有完成计划", Utils.getFormatDate(records.get(0).timestamp)));
    } else {
      mRecord.setText(
          String.format("%1$s\t锻炼%2$s", Utils.getFormatDate(records.get(0).timestamp),
              getParseDurationText(sum)));
    }
  }

  private String getParseDurationText(int duration) {
    StringBuilder sb = new StringBuilder();
    if (duration > 3600) {
      sb.append(duration / 3600);
      sb.append("小时");
      duration = duration % 3600;
    }
    if (duration > 60) {
      sb.append(duration / 60);
      sb.append("分钟");
      duration = duration % 60;
    }
    if (duration > 0) {
      sb.append(duration);
      sb.append("秒");
    }
    return sb.toString();
  }

  private void calculateMonthRecords(List<ExerciseRecord> records) {
    Log.d("calculateMonthRecords " + records.size() + " 条数据");
    List<List<ExerciseRecord>> groups = Utils.groupBy(records,
        record -> Utils.getFormatDate(record.timestamp));
    int year, month, date;
    for (List<ExerciseRecord> group : groups) {
      int sum = sumDateExerciseDuration(group);
      int[] info = Utils.getYearMonthDateArray(group.get(0).timestamp);
      year = info[0];
      month = info[1];
      date = info[2];
      int color = isCurrentSelectedDate(year, month, date) ? SELECTED_COLOR : getSchemeColor(sum);
      Calendar dateScheme = getSchemeCalendar(year, month, date, color, "");
      map.put(dateScheme.toString(), dateScheme);
    }
    mCalendarView.setSchemeDate(map);
  }

  private int sumDateExerciseDuration(List<ExerciseRecord> records) {
    int sum = 0;
    for (ExerciseRecord record : records) {
      sum += record.duration;
    }
    return sum;
  }

  private boolean isCurrentSelectedDate(int year, int month, int date) {
    return mYear == year && mMonth == month && mDay == date;
  }

  private int getSchemeColor(int sum) {
    if (sum > 300) {
      return GRATE_COLOR;
    }
    if (sum > 120) {
      return GOOD_COLOR;
    }
    if (sum > 30) {
      return BAD_COLOR;
    }
    return WORST_COLOR;
  }

  private Calendar getSchemeCalendar(int year, int month, int day, int color, String text) {
    Calendar calendar = new Calendar();
    calendar.setYear(year);
    calendar.setMonth(month);
    calendar.setDay(day);
    calendar.setSchemeColor(color);//如果单独标记颜色、则会使用这个颜色
    calendar.setScheme(text);
    return calendar;
  }

  @Override
  public void onCalendarOutOfRange(Calendar calendar) {
    Log.d("onCalendarOutOfRange: " + calendar.getYear() + "-" + calendar.getMonth() + "-" + calendar
        .getDay());
  }

  @Override
  public void onCalendarSelect(Calendar calendar, boolean isClick) {
    Log.d("onCalendarSelect: " + calendar.getYear() + "-" + calendar.getMonth() + "-" + calendar
        .getDay() + " By clicked is " + isClick);
    mTextLunar.setVisibility(View.VISIBLE);
    mTextYear.setVisibility(View.VISIBLE);
    mTextYear.setText(String.valueOf(calendar.getYear()));
    mTextLunar.setText(calendar.getLunar());
    mYear = calendar.getYear();
    mMonth = calendar.getMonth();
    mDay = calendar.getDay();
    mTextMonthDay.setText(String.format("%d月%d日", mMonth, mDay));
    selectDateChangedBs.onNext(Utils.getDate(mYear, mMonth, mDay));
  }

  @Override
  public void onYearChange(int year) {
    mTextMonthDay.setText(String.valueOf(year));
  }

  @Override
  public void onMonthChange(int year, int month) {
    mYear = year;
    mMonth = month;
    monthChangedBs.onNext(month);
  }
}

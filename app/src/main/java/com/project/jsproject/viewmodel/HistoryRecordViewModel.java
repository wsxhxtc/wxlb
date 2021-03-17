package com.project.jsproject.viewmodel;

import android.util.SparseLongArray;
import com.project.jsproject.ActionPlanDataSource;
import com.project.jsproject.entity.ExerciseRecord;
import com.project.jsproject.entity.PlanTask;
import com.project.jsproject.utils.Log;
import com.project.jsproject.utils.Rxu;
import com.project.jsproject.utils.Utils;
import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HistoryRecordViewModel extends BaseViewModel {

  public HistoryRecordViewModel(ActionPlanDataSource dataSource) {
    super(dataSource);
  }

  public Flowable<List<ExerciseRecord>> getDateRecords(Date date) {
    return mDataSource
        .getRecords(Utils.getDateStart(date).getTime(), Utils.getDateEnd(date).getTime())
        .compose(Rxu.itm());
  }

  public Flowable<List<ExerciseRecord>> getMonthRecords(Date from, Date to) {
    return mDataSource
        .getRecords(Utils.getDateStart(from).getTime(),
            Utils.getDateEnd(to).getTime())
        .compose(Rxu.itm());
  }

  public Flowable<List<PlanTask>> getTasksRecordsBelongsTo(List<ExerciseRecord> records) {
    List<Long> ids = new ArrayList<>();
    Set<Long> set = new HashSet<>();
    for (ExerciseRecord record : records) {
      if (!set.contains(record.taskId)) {
        set.add(record.taskId);
        ids.add(record.taskId);
      }
    }
    return mDataSource.getPlanTasksByIds(ids).compose(Rxu.itm());
  }
}

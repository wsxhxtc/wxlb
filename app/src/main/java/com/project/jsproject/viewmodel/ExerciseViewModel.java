package com.project.jsproject.viewmodel;

import com.project.jsproject.ActionPlanDataSource;
import com.project.jsproject.entity.ExerciseRecord;
import com.project.jsproject.entity.PlanTask;
import com.project.jsproject.utils.Rxu;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ExerciseViewModel extends BaseViewModel {

  public ExerciseViewModel(ActionPlanDataSource dataSource) {
    super(dataSource);
  }

  public Flowable<PlanTask> getTask(long planId, long taskId) {
    return mDataSource.getPlanTask(planId, taskId).compose(Rxu.itm());
  }

  public Completable addRecord(ExerciseRecord record) {
    return mDataSource.insertRecord(record)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread());
  }
}

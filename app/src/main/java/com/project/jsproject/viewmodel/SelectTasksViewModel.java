package com.project.jsproject.viewmodel;

import com.project.jsproject.ActionPlanDataSource;
import com.project.jsproject.entity.ActionPlan;
import com.project.jsproject.entity.PlanTask;
import com.project.jsproject.utils.Rxu;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import java.util.List;

public class SelectTasksViewModel extends BaseViewModel {

  public SelectTasksViewModel(ActionPlanDataSource dataSource) {
    super(dataSource);
  }

  public Flowable<ActionPlan> getPlan(long planId) {
    return mDataSource.getPlanById(planId).compose(Rxu.itm());
  }

  public Flowable<List<PlanTask>> getSelectedTasks(long planId) {
    return mDataSource.getPlanTasks(planId).compose(Rxu.itm());
  }

  public Completable insertPlanTasks(List<PlanTask> tasks) {
    return mDataSource
        .insertPlanTasks(tasks)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread());
  }
}

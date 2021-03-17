package com.project.jsproject.viewmodel;

import android.content.Context;
import androidx.annotation.NonNull;
import com.project.jsproject.ActionPlanDataSource;
import com.project.jsproject.activity.AllPlanTasksActivity;
import com.project.jsproject.entity.PlanTask;
import com.project.jsproject.utils.Rxu;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import java.util.List;

public class PlanTaskListViewModel extends BaseViewModel {

  public PlanTaskListViewModel(ActionPlanDataSource dataSource) {
    super(dataSource);
  }

  public void addTask(Context context, long planId) {
    context.startActivity(AllPlanTasksActivity.args(context, planId));
  }

  /**
   * 获取锻炼计划的所有训练任务科目
   */
  public Flowable<List<PlanTask>> getPlanTasks(long planId) {
    return mDataSource.getPlanTasks(planId).compose(Rxu.itm());
  }

  public Completable deleteTask(@NonNull PlanTask task) {
    return mDataSource.deleteTask(task).subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread());
  }
}

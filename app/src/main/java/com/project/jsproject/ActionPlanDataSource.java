package com.project.jsproject;

import com.project.jsproject.entity.ActionPlan;
import com.project.jsproject.entity.ExerciseRecord;
import com.project.jsproject.entity.PlanTask;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import java.util.List;

public interface ActionPlanDataSource {
  Flowable<List<ActionPlan>> getPlans();

  Flowable<ActionPlan> getPlanById(long id);

  Flowable<ActionPlan> getPlanByExpireTime(long time);

  Completable insertOrUpdate(ActionPlan plan);

  void deleteAllPlans();

  Flowable<List<PlanTask>> getPlanTasks(long planId);

  Flowable<PlanTask> getPlanTask(long planId, long taskId);

  Completable insertPlanTasks(List<PlanTask> tasks);

  Completable deleteTask(PlanTask task);

  Completable insertRecord(ExerciseRecord record);

  Flowable<List<ExerciseRecord>> getRecords(long startTime, long endTime);

  Flowable<List<PlanTask>> getPlanTasksByIds(List<Long> taskIds);

}

package com.project.jsproject.persistence;

import com.project.jsproject.ActionPlanDataSource;
import com.project.jsproject.dao.ActionPlanDao;
import com.project.jsproject.dao.ExerciseRecordDao;
import com.project.jsproject.dao.PlanTaskDao;
import com.project.jsproject.entity.ActionPlan;
import com.project.jsproject.entity.ExerciseRecord;
import com.project.jsproject.entity.PlanTask;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import java.util.List;

public class LocalActionPlanDataSource implements ActionPlanDataSource {
  private final ActionPlanDao mPlanDao;
  private final PlanTaskDao mTaskDao;
  private final ExerciseRecordDao mRecordDao;

  public LocalActionPlanDataSource(ActionPlanDao planDao, PlanTaskDao taskDao, ExerciseRecordDao recordDao) {
    this.mPlanDao = planDao;
    this.mTaskDao = taskDao;
    this.mRecordDao = recordDao;
  }

  @Override
  public Flowable<List<ActionPlan>> getPlans() {
    return mPlanDao.getPlans();
  }

  @Override
  public Flowable<ActionPlan> getPlanById(long id) {
    return mPlanDao.getPlanById(id);
  }

  @Override
  public Flowable<ActionPlan> getPlanByExpireTime(long time) {
    return mPlanDao.getPlanByExpireTime(time);
  }

  @Override
  public Completable insertOrUpdate(ActionPlan plan) {
    return mPlanDao.insertOrUpdate(plan);
  }

  @Override
  public void deleteAllPlans() {
    mPlanDao.deleteAllPlans();
  }

  @Override
  public Flowable<List<PlanTask>> getPlanTasks(long planId) {
    return mTaskDao.getPlanTasks(planId);
  }

  @Override
  public Flowable<PlanTask> getPlanTask(long planId, long taskId) {
    return mTaskDao.getPlanTask(planId, taskId);
  }

  @Override
  public Completable insertPlanTasks(List<PlanTask> tasks) {
    return mTaskDao.insertTask(tasks);
  }

  @Override
  public Completable deleteTask(PlanTask task) {
    return mTaskDao.deleteTask(task);
  }

  @Override
  public Completable insertRecord(ExerciseRecord record) {
    return mRecordDao.insertRecord(record);
  }

  @Override
  public Flowable<List<ExerciseRecord>> getRecords(long startTime, long endTime) {
    return mRecordDao.getRecords(startTime, endTime);
  }

  @Override
  public Flowable<List<PlanTask>> getPlanTasksByIds(List<Long> taskIds) {
    return mTaskDao.getPlanTasksByIds(taskIds);
  }
}

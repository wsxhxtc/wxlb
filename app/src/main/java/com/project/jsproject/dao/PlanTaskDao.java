package com.project.jsproject.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.project.jsproject.entity.PlanTask;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import java.util.List;

@Dao
public interface PlanTaskDao {
  @Query("SELECT * FROM planTasks WHERE planId = :planId ORDER BY subCategory ASC")
  Flowable<List<PlanTask>> getPlanTasks(long planId);

  @Query("SELECT * FROM planTasks WHERE id IN (:ids)")
  Flowable<List<PlanTask>> getPlanTasksByIds(List<Long> ids);

  @Query("SELECT * FROM planTasks WHERE planId = :planId AND id = :taskId LIMIT 1")
  Flowable<PlanTask> getPlanTask(long planId, long taskId);

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  Completable insertTask(List<PlanTask> tasks);

  @Delete
  Completable deleteTask(PlanTask task);
}

package com.project.jsproject.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.project.jsproject.entity.ActionPlan;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import java.util.List;

@Dao
public interface ActionPlanDao {
  @Query("SELECT * FROM plans ORDER BY expireTime DESC")
  Flowable<List<ActionPlan>> getPlans();

  @Query("SELECT * FROM plans WHERE id = :id LIMIT 1")
  Flowable<ActionPlan> getPlanById(long id);

  @Query("SELECT * FROM plans WHERE expireTime = :time LIMIT 1")
  Flowable<ActionPlan> getPlanByExpireTime(long time);

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  Completable insertOrUpdate(ActionPlan plan);

  @Query("DELETE FROM plans")
  void deleteAllPlans();

}

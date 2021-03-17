package com.project.jsproject;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.project.jsproject.dao.ActionPlanDao;
import com.project.jsproject.dao.ExerciseRecordDao;
import com.project.jsproject.dao.PlanTaskDao;
import com.project.jsproject.entity.ActionPlan;
import com.project.jsproject.entity.ExerciseRecord;
import com.project.jsproject.entity.PlanTask;

@Database(entities = {ActionPlan.class, PlanTask.class, ExerciseRecord.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
  private static volatile AppDatabase INSTANCE;

  public abstract ActionPlanDao planDao();

  public abstract PlanTaskDao planTaskDao();

  public abstract ExerciseRecordDao recordDao();

  public static AppDatabase getInstance(Context context) {
    if (INSTANCE == null) {
      synchronized (AppDatabase.class) {
        if (INSTANCE == null) {
          INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "app_plan.db").build();
        }
      }
    }
    return INSTANCE;
  }

}

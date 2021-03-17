package com.project.jsproject.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.project.jsproject.entity.ExerciseRecord;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import java.util.List;

@Dao
public interface ExerciseRecordDao {

  @Insert
  Completable insertRecord(ExerciseRecord record);

  @Query("SELECT * FROM exerciseRecords WHERE timestamp >= :startTime AND timestamp <= :endTime")
  Flowable<List<ExerciseRecord>> getRecords(long startTime, long endTime);

}

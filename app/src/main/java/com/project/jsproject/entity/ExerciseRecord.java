package com.project.jsproject.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "exerciseRecords")
public class ExerciseRecord {
  @PrimaryKey(autoGenerate = true)
  public long id;

  @ColumnInfo(name = "planId")
  public long planId;

  @ColumnInfo(name = "taskId")
  public long taskId;

  @ColumnInfo(name = "timestamp")
  public long timestamp;

  @ColumnInfo(name = "duration")
  public int duration;
}

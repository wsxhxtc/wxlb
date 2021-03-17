package com.project.jsproject.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "plans")
public class ActionPlan {
  @PrimaryKey(autoGenerate = true)
  public long id;

  @ColumnInfo(name = "title")
  public String title;

  @ColumnInfo(name = "categories")
  public int categories;

  @ColumnInfo(name = "createTime")
  public long createTime;

  @ColumnInfo(name = "expireTime")
  public long expireTime;

}

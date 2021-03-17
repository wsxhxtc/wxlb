package com.project.jsproject.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import java.util.Objects;

@Entity(tableName = "planTasks", primaryKeys = {"id", "planId"})
public class PlanTask {
  @ColumnInfo(name = "id")
  public long id;

  @ColumnInfo(name = "planId")
  public long planId;

  @ColumnInfo(name = "category")
  public int category;

  @ColumnInfo(name = "subCategory")
  public int subCategory;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PlanTask task = (PlanTask) o;
    return id == task.id &&
        planId == task.planId &&
        category == task.category &&
        subCategory == task.subCategory;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, planId, category, subCategory);
  }
}

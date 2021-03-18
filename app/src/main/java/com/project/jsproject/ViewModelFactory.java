package com.project.jsproject;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.project.jsproject.viewmodel.ActionPlanViewModel;
import com.project.jsproject.viewmodel.AddPlanViewModel;
import com.project.jsproject.viewmodel.ExerciseViewModel;
import com.project.jsproject.viewmodel.HistoryRecordViewModel;
import com.project.jsproject.viewmodel.PlanTaskListViewModel;
import com.project.jsproject.viewmodel.SelectTasksViewModel;
import com.project.jsproject.viewmodel.SettingsViewModel;

public class ViewModelFactory implements ViewModelProvider.Factory {

  private final ActionPlanDataSource mDataSource;

  public ViewModelFactory(ActionPlanDataSource dataSource) {
    this.mDataSource = dataSource;
  }

  @NonNull
  @Override
  public <T extends ViewModel> T create(
      @NonNull Class<T> modelClass) {
    if (modelClass.isAssignableFrom(ActionPlanViewModel.class)) {
      return (T) new ActionPlanViewModel(mDataSource);
    }
    if (modelClass.isAssignableFrom(AddPlanViewModel.class)) {
      return (T) new AddPlanViewModel(mDataSource);
    }
    if (modelClass.isAssignableFrom(HistoryRecordViewModel.class)) {
      return (T) new HistoryRecordViewModel(mDataSource);
    }
    if (modelClass.isAssignableFrom(PlanTaskListViewModel.class)) {
      return (T) new PlanTaskListViewModel(mDataSource);
    }
    if (modelClass.isAssignableFrom(SelectTasksViewModel.class)) {
      return (T) new SelectTasksViewModel(mDataSource);
    }
    if (modelClass.isAssignableFrom(ExerciseViewModel.class)) {
      return (T) new ExerciseViewModel(mDataSource);
    }
    if (modelClass.isAssignableFrom(SettingsViewModel.class)) {
      return (T) new SelectTasksViewModel(mDataSource);
    }
    throw new IllegalArgumentException("Unknown ViewModel class");
  }
}

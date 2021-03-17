package com.project.jsproject.viewmodel;

import androidx.lifecycle.ViewModel;
import com.project.jsproject.ActionPlanDataSource;

public class BaseViewModel extends ViewModel {
  protected final ActionPlanDataSource mDataSource;

  public BaseViewModel(ActionPlanDataSource dataSource) {
    this.mDataSource = dataSource;
  }

}

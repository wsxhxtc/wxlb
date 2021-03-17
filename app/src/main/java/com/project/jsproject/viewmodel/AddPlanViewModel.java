package com.project.jsproject.viewmodel;

import com.project.jsproject.ActionPlanDataSource;
import com.project.jsproject.entity.ActionPlan;
import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class AddPlanViewModel extends BaseViewModel {

  public AddPlanViewModel(ActionPlanDataSource dataSource) {
    super(dataSource);
  }

  public Completable addPlan(ActionPlan plan) {
    return mDataSource.insertOrUpdate(plan)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread());
  }
}

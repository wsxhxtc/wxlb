package com.project.jsproject.viewmodel;

import android.content.Context;
import android.content.Intent;
import androidx.lifecycle.ViewModel;
import com.project.jsproject.ActionPlanDataSource;
import com.project.jsproject.activity.AddPlanActivity;
import com.project.jsproject.entity.ActionPlan;
import com.project.jsproject.utils.Rxu;
import io.reactivex.Flowable;
import java.util.List;

public class ActionPlanViewModel extends BaseViewModel {

  public ActionPlanViewModel(ActionPlanDataSource dataSource) {
    super(dataSource);
  }

  public Flowable<List<ActionPlan>> plans() {
    return mDataSource.getPlans().compose(Rxu.itm());
  }

  public void addPlan(Context context) {
    context.startActivity(new Intent(context, AddPlanActivity.class));
  }
}

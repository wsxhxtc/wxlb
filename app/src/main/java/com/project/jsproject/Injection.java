package com.project.jsproject;

import android.content.Context;
import com.project.jsproject.persistence.LocalActionPlanDataSource;

public class Injection {
  public static ActionPlanDataSource provideActionPlanDataSource(Context context) {
    AppDatabase database = AppDatabase.getInstance(context);
    return new LocalActionPlanDataSource(database.planDao(), database.planTaskDao(), database.recordDao());
  }

  public static ViewModelFactory provideViewModelFactory(Context context) {
    ActionPlanDataSource dataSource = provideActionPlanDataSource(context);
    return new ViewModelFactory(dataSource);
  }

}

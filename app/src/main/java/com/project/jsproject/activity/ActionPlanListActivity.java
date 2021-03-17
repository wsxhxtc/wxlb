package com.project.jsproject.activity;

import android.view.Menu;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.project.jsproject.R;
import com.project.jsproject.adapter.PlansAdapter;
import com.project.jsproject.base.BaseActivity;
import com.project.jsproject.entity.ActionPlan;
import com.project.jsproject.utils.Rxu;
import com.project.jsproject.viewmodel.ActionPlanViewModel;
import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import java.util.List;
import org.reactivestreams.Publisher;

/**
 * 训练计划列表
 */
public class ActionPlanListActivity extends BaseActivity<ActionPlanViewModel> {

  private RecyclerView listView;
  private PlansAdapter adapter;


  @Override
  protected Class getViewModelClass() {
    return ActionPlanViewModel.class;
  }

  @Override
  protected int getLayoutId() {
    return R.layout.action_layout;
  }

  @Override
  protected void initView() {
    listView = findViewById(R.id.plan_list);
    adapter = new PlansAdapter(this);
    listView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
    listView.setAdapter(adapter);
  }

  @Override
  protected void initSubscriptions() {
    super.initSubscriptions();
    addDisposable(
        viewModel.plans()
            .subscribe(plans -> {
              adapter.setData(plans);
            }, throwable -> {

            }));
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_right, menu);
    menu.findItem(R.id.action_right).getActionView().setOnClickListener(
        view -> {
          viewModel.addPlan(ActionPlanListActivity.this);
        });
    return true;
  }
}

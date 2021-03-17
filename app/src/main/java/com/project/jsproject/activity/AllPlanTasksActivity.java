package com.project.jsproject.activity;

import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.project.jsproject.R;
import com.project.jsproject.adapter.TaskAdapter;
import com.project.jsproject.base.BaseActivity;
import com.project.jsproject.entity.PlanTask;
import com.project.jsproject.utils.Log;
import com.project.jsproject.utils.Utils;
import com.project.jsproject.viewmodel.SelectTasksViewModel;
import io.reactivex.Flowable;
import java.util.List;

public class AllPlanTasksActivity extends BaseActivity<SelectTasksViewModel> {

  public static final String PLAN_ID = "planId";
  private RecyclerView listView;
  private TaskAdapter adapter;
  private long planId;

  public static Intent args(Context context, long planId) {
    Intent intent = new Intent(context, AllPlanTasksActivity.class);
    intent.putExtra(PLAN_ID, planId);
    return intent;
  }

  @NonNull
  @Override
  protected Class<SelectTasksViewModel> getViewModelClass() {
    return SelectTasksViewModel.class;
  }

  @Override
  protected int getLayoutId() {
    return R.layout.activity_all_plan_tasks;
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_right, menu);
    TextView view = menu.findItem(R.id.action_right).getActionView()
        .findViewById(R.id.tv_menu_action_right);
    view.setText("保存");
    view.setOnClickListener(v -> {
      save();
    });
    return true;
  }

  private void save() {
    List<PlanTask> selected = adapter.getSelectedTasks();
    if (adapter.getItemCount() > 0 && selected.isEmpty()) {
      showToast("请至少选择1个锻炼任务");
      return;
    }
    if (selected.isEmpty()) {
      finish();
      return;
    }
    addDisposable(viewModel.insertPlanTasks(selected).subscribe(() -> finish()));
  }

  @Override
  protected void initView() {
    listView = findViewById(R.id.task_list_for_select);
    listView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
    DividerItemDecoration decoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
    decoration.setDrawable(Utils.drawable(this, R.drawable.listview_divider));
    listView.addItemDecoration(decoration);
    adapter = new TaskAdapter(this, true);
    listView.setAdapter(adapter);
  }

  @Override
  protected void initData() {
    super.initData();
    planId = getIntent().getLongExtra(PLAN_ID, 0);
  }

  @Override
  protected void initSubscriptions() {
    super.initSubscriptions();
    addDisposable(
        Flowable.combineLatest(
            viewModel.getPlan(planId),
            viewModel.getSelectedTasks(planId),
            (plan, selected) -> {
              // 将已经选择的任务过滤掉
              List<PlanTask> all = Utils.getPlanAllTasks(plan);
              return Utils.filter(all, task -> !selected.contains(task));
            }).subscribe(tasks -> {
          adapter.updateData(tasks);
        }, throwable -> Log.e(throwable.getMessage())));
  }
}

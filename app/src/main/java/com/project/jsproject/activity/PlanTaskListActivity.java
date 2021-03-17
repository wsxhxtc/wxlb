package com.project.jsproject.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.Menu;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AlertDialog;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.project.jsproject.R;
import com.project.jsproject.adapter.TaskAdapter;
import com.project.jsproject.adapter.TaskAdapter.OnPlanTaskActionListener;
import com.project.jsproject.base.BaseActivity;
import com.project.jsproject.entity.PlanTask;
import com.project.jsproject.utils.Log;
import com.project.jsproject.utils.Utils;
import com.project.jsproject.viewmodel.PlanTaskListViewModel;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

public class PlanTaskListActivity extends BaseActivity<PlanTaskListViewModel> implements
    OnPlanTaskActionListener {

  public static final String PLAN_ID = "planId";
  private long planId;

  private RecyclerView listView;
  private TaskAdapter adapter;

  public static Intent args(Context context, long planId) {
    Intent intent = new Intent(context, PlanTaskListActivity.class);
    intent.putExtra(PLAN_ID, planId);
    return intent;
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_right, menu);
    TextView view = menu.findItem(R.id.action_right).getActionView()
        .findViewById(R.id.tv_menu_action_right);
    view.setText("添加任务");
    view.setOnClickListener(v -> {
      viewModel.addTask(PlanTaskListActivity.this, planId);
    });
    return true;
  }

  @NonNull
  @Override
  protected Class<PlanTaskListViewModel> getViewModelClass() {
    return PlanTaskListViewModel.class;
  }

  @Override
  protected void initData() {
    super.initData();
    planId = getIntent().getLongExtra(PLAN_ID, 0);
  }

  @Override
  protected int getLayoutId() {
    return R.layout.layout_plan_task_list;
  }

  @Override
  protected void initView() {
    listView = findViewById(R.id.recycler_plan_tasks);
    listView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
    DividerItemDecoration decoration = new DividerItemDecoration(this,
        DividerItemDecoration.VERTICAL);
    decoration.setDrawable(Utils.drawable(this, R.drawable.listview_divider));
    listView.addItemDecoration(decoration);
    adapter = new TaskAdapter(this, false);
    listView.setAdapter(adapter);
    adapter.setOnPlanTaskActionListener(this);
  }

  @Override
  protected void initSubscriptions() {
    super.initSubscriptions();
    addDisposable(viewModel.getPlanTasks(planId).subscribe(tasks -> {
      adapter.updateData(tasks);
    }, throwable -> {
      Log.e(throwable.getMessage());
    }));
  }

  @Override
  public void onDelete(PlanTask task) {
    new AlertDialog.Builder(this).setTitle("确认删除？").setPositiveButton("确认",
        (dialog, which) -> delete(task)).setNegativeButton("取消", (dialog, which) -> {

    }).setCancelable(true).create().show();
  }

  private void delete(PlanTask task) {
    addDisposable(viewModel.deleteTask(task).subscribe(() -> {
      showToast("删除任务成功");
    }, throwable -> {
      Log.e(throwable.getMessage());
    }));
  }
}

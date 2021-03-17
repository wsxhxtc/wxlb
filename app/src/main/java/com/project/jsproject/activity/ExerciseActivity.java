package com.project.jsproject.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import com.project.jsproject.R;
import com.project.jsproject.base.BaseActivity;
import com.project.jsproject.entity.ExerciseRecord;
import com.project.jsproject.utils.Log;
import com.project.jsproject.utils.Utils;
import com.project.jsproject.viewmodel.ExerciseViewModel;
import java.util.Timer;
import java.util.TimerTask;

public class ExerciseActivity extends BaseActivity<ExerciseViewModel> implements Handler.Callback {

  public static final String PLAN_ID = "planId";
  public static final String TASK_ID = "taskId";
  private TextView title;
  private ImageView action;
  private TextView duration;

  private Timer timer;

  private static final int STATE_PLAY = 0;
  private static final int STATE_PAUSE = 1;

  private Handler handler;

  private int state = STATE_PAUSE;

  private int calculateDuration = 0;
  private long startTime;

  private long planId;
  private long taskId;

  public static Intent args(Context context, long planId, long taskId) {
    Intent intent = new Intent(context, ExerciseActivity.class);
    intent.putExtra(PLAN_ID, planId);
    intent.putExtra(TASK_ID, taskId);
    return intent;
  }

  @NonNull
  @Override
  protected Class<ExerciseViewModel> getViewModelClass() {
    return ExerciseViewModel.class;
  }

  @Override
  protected int getLayoutId() {
    return R.layout.activity_exercise;
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
    if (calculateDuration < 5) {
      showToast("本次锻炼不满5秒，无法记录");
      return;
    }
    ExerciseRecord record = new ExerciseRecord();
    record.timestamp = startTime;
    record.duration = calculateDuration;
    record.planId = planId;
    record.taskId = taskId;
    state = STATE_PAUSE;
    resetStateButton();
    addDisposable(
        viewModel.addRecord(record)
            .subscribe(() -> {
              showToast("记录成功");
              finish();
            }));
  }

  @Override
  protected void initView() {
    title = findViewById(R.id.title);
    action = findViewById(R.id.action);
    duration = findViewById(R.id.duration);
    action.setOnClickListener(v -> {
      if (state == STATE_PLAY) {
        state = STATE_PAUSE;
        action.setImageResource(R.drawable.ic_start);
      } else {
        state = STATE_PLAY;
        action.setImageResource(R.drawable.ic_pause);
      }
      resetStateButton();
    });
  }

  private void resetStateButton() {
    if (state == STATE_PAUSE) {
      action.setImageResource(R.drawable.ic_start);
    } else {
      action.setImageResource(R.drawable.ic_pause);
    }
  }

  @Override
  protected void initData() {
    super.initData();
    planId = getIntent().getLongExtra(PLAN_ID, 0);
    taskId = getIntent().getLongExtra(TASK_ID, 0);
    handler = new Handler(this);
    timer = new Timer("exercise_record");
    timer.scheduleAtFixedRate(new TimerTask() {
      @Override
      public void run() {
        handler.sendEmptyMessage(0);
      }
    }, 0, 1000);
  }

  @Override
  protected void initSubscriptions() {
    super.initSubscriptions();
    addDisposable(viewModel.getTask(planId, taskId).subscribe(
        task -> title.setText(Utils.getTaskTitle(task)),
        throwable -> Log.e(throwable.getMessage())));
  }

  @Override
  public void onBackPressed() {
    if (state == STATE_PLAY) {
      new AlertDialog.Builder(this).setTitle("正在计时，确定退出吗？").setPositiveButton("确定",
          (dialog, which) -> {
            state = STATE_PAUSE;
            resetStateButton();
            finish();
          }).setNegativeButton("取消", (dialog, which) -> {

      }).create().show();
      return;
    }
    super.onBackPressed();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    timer.cancel();
  }

  @Override
  public boolean handleMessage(@NonNull Message msg) {
    if (msg.what == 0) {
      if (state == STATE_PLAY) {
        if (startTime == 0) {
          startTime = System.currentTimeMillis();
        }
        calculateDuration++;
        duration.setText(String.format("%d s", calculateDuration));
      }
    }
    return true;
  }
}

package com.project.jsproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.project.jsproject.R;
import com.project.jsproject.adapter.ActionAdapter;
import com.project.jsproject.api.BaseUrl;
import com.project.jsproject.entity.ActionEntity;
import com.project.jsproject.utils.DbController;
import com.superrecycleview.superlibrary.recycleview.SuperRecyclerView;

import java.util.List;

public class ActionActivity extends AppCompatActivity {

  private TextView mAdd;
  private SuperRecyclerView mSv1;
  private ActionAdapter actionAdapter;
  private List<ActionEntity> data;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.action_layout);
    mAdd = (TextView) findViewById(R.id.add);
    mAdd.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(ActionActivity.this, AddTaskActivity.class);
        startActivity(intent);
        finish();
      }
    });
    data = DbController.getInstance(getApplicationContext()).searchByName(BaseUrl.username);
    mSv1 = (SuperRecyclerView) findViewById(R.id.sv1);
    mSv1.setLayoutManager(new LinearLayoutManager(this));
    mSv1.setRefreshEnabled(false);
    mSv1.setLoadMoreEnabled(false);
    actionAdapter = new ActionAdapter(this, data);
    mSv1.setAdapter(actionAdapter);

  }

  @Override
  protected void onResume() {
    super.onResume();

  }

  @Override
  protected void onRestart() {
    super.onRestart();
    if (data.size() != 0) {
      data.clear();
    }
    data.addAll(DbController.getInstance(getApplicationContext()).searchByName(BaseUrl.username));
    actionAdapter.notifyDataSetChanged();
  }
}

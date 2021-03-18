package com.project.jsproject.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;

import com.project.jsproject.R;
import com.project.jsproject.api.BaseUrl;

import com.project.jsproject.base.BaseActivity;
import com.project.jsproject.utils.PreferencesUtil;
import com.project.jsproject.viewmodel.SettingsViewModel;
import java.util.ArrayList;
import java.util.List;


public class SettingsActivity extends BaseActivity<SettingsViewModel> {

  public static final String RECOMMEND_PLAY_TIME_DAILY = "recommend_play_time_daily";

  private Spinner mSpinner;
  private List<String> time;

  @NonNull
  @Override
  protected Class<SettingsViewModel> getViewModelClass() {
    return SettingsViewModel.class;
  }

  @Override
  protected int getLayoutId() {
    return R.layout.settings_activity;
  }

  @Override
  protected void initView() {
    mSpinner = findViewById(R.id.spnner);
    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
        android.R.layout.simple_list_item_1, time);
    mSpinner.setAdapter(adapter);
    mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        BaseUrl.SetttingTime = time.get(i);
        PreferencesUtil.putString(SettingsActivity.this, RECOMMEND_PLAY_TIME_DAILY, time.get(i));
      }

      @Override
      public void onNothingSelected(AdapterView<?> adapterView) {

      }
    });
    String lastTime = PreferencesUtil
        .getString(SettingsActivity.this, RECOMMEND_PLAY_TIME_DAILY, "30");
    for (int i = 0; i < time.size(); i++) {
      if (TextUtils.equals(lastTime, time.get(i))) {
        mSpinner.setSelection(i);
        break;
      }
    }
  }

  @Override
  protected void initData() {
    super.initData();
    time = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      time.add(String.format("%d", (i + 1) * 10));
    }
  }

}
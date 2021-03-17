package com.project.jsproject.base;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.project.jsproject.Injection;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BaseActivity<VM extends ViewModel> extends AppCompatActivity {

  protected final CompositeDisposable disposableSet = new CompositeDisposable();
  protected VM viewModel;

  protected void initWindow() {
  }

  protected void initData(){

  }

  protected abstract @NonNull
  Class<VM> getViewModelClass();

  private void createViewModel() {
    viewModel = new ViewModelProvider(this, Injection.provideViewModelFactory(this))
        .get(getViewModelClass());
  }

  protected abstract int getLayoutId();

  protected abstract void initView();

  protected boolean enableDisplayHomeAsUp() {
    return true;
  }

  protected void initSubscriptions() {

  }

  protected void addDisposable(Disposable disposable) {
    this.disposableSet.add(disposable);
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    initWindow();
    setContentView(getLayoutId());
    initData();
    if (enableDisplayHomeAsUp()) {
      ActionBar actionBar = getSupportActionBar();
      if (actionBar != null) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      }
    }
    initView();
    createViewModel();
  }

  @Override
  protected void onStart() {
    super.onStart();
    initSubscriptions();
  }

  @Override
  protected void onStop() {
    super.onStop();
    disposableSet.clear();
  }

  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    if (item.getItemId() == android.R.id.home) {
      onBackPressed();
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  protected void showToast(CharSequence text) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
  }
}


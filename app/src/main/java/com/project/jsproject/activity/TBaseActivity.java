package com.project.jsproject.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public abstract class TBaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layoutId());
        initView();
        initData();
    }

    public abstract int layoutId();

    public abstract void initView();

    public abstract void initData();

    public void onShow() {

    }

    public void onHide() {

    }
}

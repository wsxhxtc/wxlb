package com.project.jsproject.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.project.jsproject.R;
import com.project.jsproject.api.Appcofing;
import com.project.jsproject.api.BaseUrl;
import com.project.jsproject.api.service;
import com.project.jsproject.bean.codeAndMegDTO;
import com.project.jsproject.entity.ywEntity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FromApplayActivity extends AppCompatActivity {


    private String time = "0";

    private EditText m1, m2, m3, m4, m5;
    private TextView mApply;

    private List<String> ti;

    private List<String> da;
    private TextView ms1;
    private EditText ms2;
    private EditText ms3;
    private EditText ms4;
    private TextView t_username;
    private AlertDialog dialog;
    private ScrollView mview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_layout);
        ti = new ArrayList<>();
        da = new ArrayList<>();
        initView();
    }

    private void initView() {
        Call<List<ywEntity>> call = new Retrofit.Builder().baseUrl(BaseUrl.BASEURL).client(Appcofing.getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create(new Gson())).build().create(
                        service.class
                ).getIndyw(BaseUrl.username);
        call.enqueue(new Callback<List<ywEntity>>() {
            @Override
            public void onResponse(Call<List<ywEntity>> call, Response<List<ywEntity>> response) {

                List<ywEntity> list = response.body();
                AlertDialog.Builder builder = new AlertDialog.Builder(FromApplayActivity.this);
                builder.setTitle("您有一份测试");
                builder.setMessage(list.size()==0?"暂无成绩":list.get(0).getScore());
                builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mview.setVisibility(View.VISIBLE);
                    }
                });
                builder.setNegativeButton("否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialog.dismiss();
                        FromApplayActivity.this.finish();

                    }
                });
                dialog = builder.show();
                dialog.setCanceledOnTouchOutside(false);
                dialog.setCanceledOnTouchOutside(false);
            }

            @Override
            public void onFailure(Call<List<ywEntity>> call, Throwable t) {
                Toast.makeText(FromApplayActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        mview = (ScrollView) findViewById(R.id.view);
        t_username = (TextView) findViewById(R.id.t_username);
        t_username.setText("学生："+BaseUrl.username);
        ms1 = (EditText) findViewById(R.id.et_1);
        ms2 = (EditText) findViewById(R.id.et_2);
        ms3 = (EditText) findViewById(R.id.et_3);
        ms4 = (EditText) findViewById(R.id.et_4);

        mApply = (TextView) findViewById(R.id.apply);
        mApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ms1.getText().toString().isEmpty()) {
                    Toast.makeText(FromApplayActivity.this, "请输入第一项", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (ms2.getText().toString().isEmpty()) {
                    Toast.makeText(FromApplayActivity.this, "请输入第二项", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (ms3.getText().toString().isEmpty()) {
                    Toast.makeText(FromApplayActivity.this, "请输入第三项", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (ms4.getText().toString().isEmpty()) {
                    Toast.makeText(FromApplayActivity.this, "请输入第四项", Toast.LENGTH_SHORT).show();
                    return;
                }
                ywEntity yw = new ywEntity();
                yw.setUsername(BaseUrl.username);
                yw.setYtx(ms1.getText().toString());
                yw.setQbs(ms2.getText().toString());
                yw.setRunscore(ms3.getText().toString());
                yw.setJumpscore(ms4.getText().toString());
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd:hhmmss");
                yw.setTime(simpleDateFormat.format(new Date()));

                Call<codeAndMegDTO> call = new Retrofit.Builder().baseUrl(BaseUrl.BASEURL).client(Appcofing.getOkHttpClient())
                        .addConverterFactory(GsonConverterFactory.create(new Gson())).build().create(
                                service.class
                        ).savedata(yw);
                call.enqueue(new Callback<codeAndMegDTO>() {
                    @Override
                    public void onResponse(Call<codeAndMegDTO> call, Response<codeAndMegDTO> response) {
                        if (response.body() == null) {
                            return;
                        }
                        if (response.body().getCode().equals("0")) {
                            mApply.setEnabled(false);
                            mApply.setAlpha(0.3f);
                            Toast.makeText(FromApplayActivity.this, "提交数据成功", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<codeAndMegDTO> call, Throwable t) {
                        Toast.makeText(FromApplayActivity.this, "提交失败" + t.getMessage().toString(), Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });
    }
}

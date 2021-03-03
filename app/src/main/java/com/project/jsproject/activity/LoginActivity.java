package com.project.jsproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.gson.Gson;
import com.project.jsproject.DrawActivity;
import com.project.jsproject.MainActivity;
import com.project.jsproject.R;
import com.project.jsproject.api.Appcofing;
import com.project.jsproject.api.BaseUrl;
import com.project.jsproject.api.service;
import com.project.jsproject.bean.loginUserDTO;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {
    private TextView link_signup;
    private AppCompatButton btn_login;
    private EditText input_name;

    private EditText input_password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        initView();
    }

    private void initView() {
        input_name = (EditText) findViewById(R.id.input_name);
        input_password = (EditText) findViewById(R.id.input_password);
        btn_login = (AppCompatButton) findViewById(R.id.btn_login);
        link_signup = (TextView) findViewById(R.id.link_signup);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String value_name = input_name.getText().toString();
                String value_pwd = input_password.getText().toString();
                if (!value_name.isEmpty() && !value_pwd.isEmpty()) {
                    Call<loginUserDTO> call = new Retrofit.Builder().baseUrl(BaseUrl.BASEURL).client(Appcofing.getOkHttpClient())
                            .addConverterFactory(GsonConverterFactory.create(new Gson())).build().create(
                                    service.class
                            ).register(value_name, value_name);
                    call.enqueue(new Callback<loginUserDTO>() {
                        @Override
                        public void onResponse(Call<loginUserDTO> call, Response<loginUserDTO> response) {
                            if (response.body() == null) {
                                Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            if (response.body().getCodeAndMegDTO().getCode().equals("0")) {
                                BaseUrl.username = value_name;
                                Intent intent = new Intent(getApplicationContext(), DrawActivity.class);
                                startActivity(intent);
                            }
                        }

                        @Override
                        public void onFailure(Call<loginUserDTO> call, Throwable t) {
                            Toast.makeText(LoginActivity.this, "登录失败" + t.getMessage().toString(), Toast.LENGTH_SHORT).show();

                        }
                    });

                } else {
                    Toast.makeText(LoginActivity.this, "请检查填写的信息", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}

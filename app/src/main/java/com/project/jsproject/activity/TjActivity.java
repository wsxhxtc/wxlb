package com.project.jsproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.project.jsproject.MainActivity;
import com.project.jsproject.R;

public class TjActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tj_layout);

        findViewById(R.id.x1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(TjActivity.this, AnimalActivity.class);
                intent.putExtra("type",1);
                startActivity(intent);
            }
        });
        findViewById(R.id.x2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(TjActivity.this, AnimalActivity.class);
                intent.putExtra("type",2);
                startActivity(intent);
            }
        });
        findViewById(R.id.x3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(TjActivity.this, AnimalActivity.class);
                intent.putExtra("type",3);
                startActivity(intent);
            }
        });
    }
}

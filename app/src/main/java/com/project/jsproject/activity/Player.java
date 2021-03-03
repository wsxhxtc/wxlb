package com.project.jsproject.activity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.project.jsproject.R;

public class Player extends AppCompatActivity {
    private VideoView video;
    private int mUrl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player);

        video = (VideoView) findViewById(R.id.vp);
        mUrl = getIntent().getIntExtra("url", -1);
        MediaController mc = new MediaController(Player.this);       // 创建一个MediaController对象
        video.setMediaController(mc);       // 将VideoView与MediaController关联起来
        video.setVideoURI(Uri.parse("android.resource://com.project.jsproject/" + mUrl));
        video.requestFocus();       // 设置VideoView获取焦点
        try {
            video.start();      // 播放视频
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 设置VideoView的Completion事件监听器
        video.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
            }
        });


    }
}

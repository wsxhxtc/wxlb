package com.project.jsproject;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class AppUtils {
    /**
     * 加载第四秒的帧数作为封面
     *  url就是视频的地址
     */
    public static void loadCover(ImageView imageView, String url, Context context) {

        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Glide.with(context)
                .setDefaultRequestOptions(
                        new RequestOptions()
                                .frame(4000000)
                                .centerCrop()
                                /*.error(R.mipmap.eeeee)//可以忽略
                                .placeholder(R.mipmap.ppppp)//可以忽略*/
                )
                .load(url)
                .into(imageView);
    }
}

package com.project.jsproject.api;

import com.project.jsproject.bean.DataInfo;
import com.project.jsproject.bean.codeAndMegDTO;
import com.project.jsproject.bean.loginUserDTO;
import com.project.jsproject.entity.ywEntity;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface service {
    /**
     * banner 优惠活动
     * @return
     */
    @POST(BaseUrl.BASEURL+"save")
    public Call<codeAndMegDTO> savedata(@Body ywEntity y);

    @GET(BaseUrl.BASEURL+"findyw")
    public Call<List<ywEntity>> getIndyw(@Query("username")String username);

    @POST(BaseUrl.BASEURL+"register")
    Call<loginUserDTO>register(@Query("username") String username, @Query("pwd") String pwd);

}


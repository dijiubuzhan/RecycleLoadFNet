package com.zhanxun.myapplication;

import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.ResponseBody;
import com.zhanxun.myapplication.bean.WeatherModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


/**
 * Created by wilsen on 2016/12/28.
 */
public interface ApiService {


    @POST("forecast")
    Call<ResponseBody> check(@Body RequestBody description);

    @POST("forecast")
    @FormUrlEncoded
    Call<WeatherModel> postRequest(@Field("city") String city, @Field("key") String key);

}
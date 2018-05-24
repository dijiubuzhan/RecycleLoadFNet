package com.zhanxun.myapplication;

import com.zhanxun.myapplication.bean.WeatherModel;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


/**
 * Created by wilsen on 2016/12/28.
 */
public interface ApiService {


    @POST("forecast")
    @FormUrlEncoded
    Observable<WeatherModel> postRequest(@Field("city") String city, @Field("key") String key);

}
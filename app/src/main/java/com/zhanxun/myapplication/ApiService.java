package com.zhanxun.myapplication;

import com.zhanxun.myapplication.bean.MultiNewsModel;
import com.zhanxun.myapplication.bean.WeatherModel;

import io.reactivex.Observable;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;


/**
 * Created by wilsen on 2016/12/28.
 */
public interface ApiService {


    @POST("forecast")
    @FormUrlEncoded
    Observable<WeatherModel> postRequest(@Field("city") String city, @Field("key") String key);


    @GET("latest")
    Observable<ResponseBody> getNews();

    @GET("http://is.snssdk.com/api/news/feed/v62/?iid=5034850950&device_id=6096495334&refer=1&count=20&aid=13")
    Observable<MultiNewsModel> getNewsArticle(
            @Query("category") String category,
            @Query("max_behot_time") String maxBehotTime);
}
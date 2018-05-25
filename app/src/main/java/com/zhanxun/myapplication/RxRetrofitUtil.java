package com.zhanxun.myapplication;



import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RxRetrofitUtil{
    private OkHttpClient.Builder okHttpBuilder;
    private Retrofit.Builder mRetrofitBuilder;
    private static RxRetrofitUtil instance;
    private String baseUrl;
    public  static RxRetrofitUtil getInstance(){
        return new RxRetrofitUtil();
    }



    public Retrofit.Builder getRetrofitBuilder(){
        mRetrofitBuilder=new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(getOkHttpBuilder().build())
                .baseUrl(baseUrl);

        return mRetrofitBuilder;
    }

    public RxRetrofitUtil baseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        return this;
    }

    public <K> K createSApi(Class<K> cls) {
        return getRetrofitBuilder().build().create(cls);
    }

    private OkHttpClient.Builder getOkHttpBuilder(){
        if (okHttpBuilder==null) {
            okHttpBuilder=new OkHttpClient.Builder();
            okHttpBuilder.connectTimeout(15,TimeUnit.SECONDS);
            okHttpBuilder.readTimeout(15,TimeUnit.SECONDS);
            okHttpBuilder.writeTimeout(15,TimeUnit.SECONDS);
        }
        return okHttpBuilder;
    }
}

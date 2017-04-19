package com.zhanxun.myapplication;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "FirstDemo";
    SwipeRefreshLayout m_swipeRefreshLayout;
    RecyclerView m_recycleView;
    RecycleAdapter m_RecycleAdapter;
    NewsModel news;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iniView();
        iniData();
    }

    private void iniData() {

        SharedPreferences sp = this.getSharedPreferences("collectPolicy", 0);
        String last_info = sp.getString("collectPolicy", "123");
        Log.d(TAG, "iniData(),last_info=" + last_info);
        news = JsonTools.jsonObj(last_info, NewsModel.class);
        if (news != null) {
            m_RecycleAdapter = new RecycleAdapter(MainActivity.this, news.getStories());
            m_recycleView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            m_recycleView.setHasFixedSize(true);
            m_recycleView.setItemAnimator(new DefaultItemAnimator());
            m_recycleView.addItemDecoration(new ListItemDecoration(this, LinearLayoutManager.VERTICAL));
            m_recycleView.setAdapter(m_RecycleAdapter);
        }

    }

    private void jsonInfos(String result) {
        SharedPreferences sp = this.getSharedPreferences("collectPolicy", 0);
        String policy = sp.getString("collectPolicy", "123");
        if (!TextUtils.isEmpty(policy) && policy.equals(result)) {
            //不做修改
            Log.d(TAG, "downInfos,jsonInfos,same policy");
        } else {
            Log.d(TAG, "downInfos,jsonInfos,parser policy");
            SharedPreferences jsonText = this.getSharedPreferences("collectPolicy", 0);
            jsonText.edit().clear();
            jsonText.edit().putString("collectPolicy", result).commit();
            iniData();
        }
    }

    private void iniView() {
        m_swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);
        m_swipeRefreshLayout.setColorSchemeColors(Color.RED, Color.BLUE, Color.GREEN);
        m_swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // TODO Auto-generated method stub
                OkHttpClient mOkHttpClient = new OkHttpClient();
                //创建一个Request
                final Request request = new Request.Builder()
                        .url("http://news-at.zhihu.com/api/4/news/latest")
                        .build();
                //new call
                mOkHttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Request request, IOException e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this, "update error", Toast.LENGTH_SHORT).show();
                                m_swipeRefreshLayout.setRefreshing(false);
                            }
                        });

                        Log.d(TAG, "onFailure(),e=" + e.getMessage());
                    }

                    @Override
                    public void onResponse(Response response) throws IOException {
                        try {
                            final String result = response.body().string();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    jsonInfos(result);
                                    Toast.makeText(MainActivity.this, "update successfully", Toast.LENGTH_SHORT).show();
                                    m_swipeRefreshLayout.setRefreshing(false);
                                }
                            });
                            Log.d(TAG, "onResponse(),result=" + result);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });
            }
        });

        m_recycleView = (RecyclerView) findViewById(R.id.recycler_view);

        Log.d(TAG, "iniView");

    }

}

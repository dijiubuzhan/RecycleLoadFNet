package com.zhanxun.myapplication;

import android.content.ClipData;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;


import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.zhanxun.myapplication.bean.MultiNewsArticleDataModel;
import com.zhanxun.myapplication.bean.MultiNewsModel;
import com.zhanxun.myapplication.bean.NewsModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "FirstDemo";
    SwipeRefreshLayout m_swipeRefreshLayout;
    RecyclerView m_recycleView;
    RecycleAdapter m_RecycleAdapter;
    NewsModel news;
    private DrawerLayout m_drawerLayout;
    private NavigationView m_NavigationView;
    private Gson gson = new Gson();
    private List<MultiNewsArticleDataModel> oldItems=new ArrayList<>();
    private List<MultiNewsArticleDataModel> dataModelList=new ArrayList<>();
    private int lastItemCount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iniView();
        loadData();
    }

    private void iniView() {
        Log.d(TAG, "iniView");
        m_swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);
        m_swipeRefreshLayout.setColorSchemeColors(Color.RED, Color.BLUE, Color.GREEN);
        m_swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // TODO Auto-generated method stub
                loadData();
            }
        });

        m_recycleView = (RecyclerView) findViewById(R.id.recycler_view);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        m_drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        m_NavigationView = (NavigationView) findViewById(R.id.nav);
        if (m_drawerLayout != null) {
            setDrawerContent(m_NavigationView);
        }

        m_RecycleAdapter = new RecycleAdapter(MainActivity.this, oldItems);
        m_recycleView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        m_recycleView.setHasFixedSize(true);
        m_recycleView.setItemAnimator(new DefaultItemAnimator());
        m_recycleView.addItemDecoration(new ListItemDecoration(this, LinearLayoutManager.VERTICAL));
        m_recycleView.setAdapter(m_RecycleAdapter);
        m_recycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int itemCount = layoutManager.getItemCount();
                int lastPosition = layoutManager.findLastCompletelyVisibleItemPosition();

                if (lastItemCount != itemCount && lastPosition == itemCount - 1) {
                    lastItemCount = itemCount;
                    loadData();
                }
            }
        });
    }


    private void loadData() {
        Log.d(TAG, "loadData: ");
        RxRetrofitUtil.getInstance()
                .baseUrl("http://toutiao.com/")
                .createSApi(ApiService.class)
                .getNewsArticle("",String.valueOf(System.currentTimeMillis() / 1000))
                .subscribeOn(Schedulers.newThread())
                .switchMap((Function< MultiNewsModel, Observable<MultiNewsArticleDataModel>>)multiNewsModel->{
                    List<MultiNewsArticleDataModel> dataList=new ArrayList<>();
                    for (MultiNewsModel.DataBean datum : multiNewsModel.getData()) {
                        dataList.add(gson.fromJson(datum.getContent(),MultiNewsArticleDataModel.class));
                    }
                    return Observable.fromIterable(dataList);
                })
                .toList()
                .map(list -> {
                    // 过滤重复新闻(与本次刷新的数据比较,因为使用了2个请求,数据会有重复)
                    for (int i = 0; i < list.size() - 1; i++) {
                        for (int j = list.size() - 1; j > i; j--) {
                            if (list.get(j).getTitle().equals(list.get(i).getTitle())) {
                                list.remove(j);
                            }
                        }
                    }
                    return list;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> {
                    if (null != list && list.size() > 0) {
                        doSetAdapter(list);
                    } else {
                        Toast.makeText(MainActivity.this, "no more data", Toast.LENGTH_SHORT).show();
                    }
                    m_swipeRefreshLayout.setRefreshing(false);
                }, throwable -> {
                    Toast.makeText(MainActivity.this, "update error", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onError: Throwable e="+throwable.getMessage());
                    m_swipeRefreshLayout.setRefreshing(false);
                });
    }

    private void doSetAdapter(List<MultiNewsArticleDataModel> list) {
        if (dataModelList.size()>150) {
            dataModelList.clear();
        }
        dataModelList.addAll(list);
        DiffCallback.create(oldItems, dataModelList, m_RecycleAdapter);
        oldItems.clear();
        oldItems.addAll(dataModelList);
        m_recycleView.stopNestedScroll();
    }





    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (m_drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    m_drawerLayout.closeDrawers();
                }else {
                    m_drawerLayout.openDrawer(GravityCompat.START);
                }
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setDrawerContent(NavigationView m_NavigationView) {
        m_NavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.hello:
                        startActivity(new Intent(MainActivity.this,WeatherActivity.class));
                        break;
                    case R.id.about:
                        Toast.makeText(MainActivity.this, "I am green", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
                item.setChecked(true);
                m_drawerLayout.closeDrawers();
                return true;
            }
        });
    }
}

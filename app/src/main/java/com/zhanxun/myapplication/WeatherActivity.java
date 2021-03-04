package com.zhanxun.myapplication;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.zhanxun.myapplication.bean.WeatherModel;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherActivity extends AppCompatActivity {
    private static final String TAG = "MyWeather";
    private EditText m_editText;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        m_editText = (EditText) findViewById(R.id.btn_edit);
        listView = (ListView) findViewById(R.id.list_View);
    }


    public void check(View v) {

        final String city = m_editText.getText().toString().trim();
        if (TextUtils.isEmpty(city)) {
            Toast.makeText(this, "请输入城市名", Toast.LENGTH_SHORT).show();
            return;
        }

        RxRetrofitUtil.getInstance()
                .baseUrl("https://free-api.heweather.com/v5/")
                .createSApi(ApiService.class)
                .postRequest(city, "96217f3f638b4d61ba3581bb184d889b")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<WeatherModel, List<WeatherModel.HeWeather5Bean>>() {

                    @Override
                    public List<WeatherModel.HeWeather5Bean> apply(WeatherModel weatherModel) throws Exception {
                        return weatherModel.getHeWeather5();
                    }
                })
                .subscribe(new Observer<List<WeatherModel.HeWeather5Bean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<WeatherModel.HeWeather5Bean> heWeather5Beans) {
                        Log.d(TAG, "onNext: ");
                        loadData(heWeather5Beans);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: " + e.getMessage());
                        Toast.makeText(WeatherActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: ");
                    }
                });

    }

    private void loadData(List<WeatherModel.HeWeather5Bean> heWeather5) {
        WeatherModel.HeWeather5Bean heWeather = heWeather5.get(0);
        Log.d(TAG, "loadData: " + (heWeather != null));
        if (!isConnected(this)) {
            Toast.makeText(this, "please connect network", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!TextUtils.isEmpty(heWeather.getStatus()) && heWeather.getStatus().equalsIgnoreCase("ok")) {
            Log.i(TAG, "loadData,,update time=" + heWeather.getBasic().getUpdate().getLoc());
            for (WeatherModel.HeWeather5Bean.DailyForecastBean dailyForecastBean : heWeather.getDaily_forecast()) {
                Log.d(TAG, "loadData, data=" + dailyForecastBean.getDate() + ",maxTmp:" + dailyForecastBean.getTmp().getMax() + ",txt:" + dailyForecastBean.getCond().getTxt_d());
            }
            WeatherAdapter weatherAdapter = new WeatherAdapter(getApplicationContext(), heWeather.getDaily_forecast());
            listView.setAdapter(weatherAdapter);
        } else {
            Toast.makeText(this, "broadcast query fail", Toast.LENGTH_SHORT).show();
        }


    }

    public static boolean isConnected(Context context) {

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        return info != null && info.isConnected();

    }

    public class WeatherAdapter extends BaseAdapter {
        private Context context;
        private List<WeatherModel.HeWeather5Bean.DailyForecastBean> dailyForecastBeans;
        private LayoutInflater m_layoutInflater;

        public WeatherAdapter(Context context, List<WeatherModel.HeWeather5Bean.DailyForecastBean> dailyForecastBeans) {
            this.context = context;
            this.dailyForecastBeans = dailyForecastBeans;
            this.m_layoutInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return dailyForecastBeans.size();
        }

        @Override
        public Object getItem(int position) {
            return dailyForecastBeans.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView != null) {
                viewHolder = (ViewHolder) convertView.getTag();
            } else {
                viewHolder = new ViewHolder();
                convertView = m_layoutInflater.inflate(R.layout.weather_item, null);
                viewHolder.date = (TextView) convertView.findViewById(R.id.data);
                viewHolder.max_Tem = (TextView) convertView.findViewById(R.id.max_tem);
                viewHolder.min_Tem = (TextView) convertView.findViewById(R.id.min_tem);
                viewHolder.descripe_day = (TextView) convertView.findViewById(R.id.day_txt);
                viewHolder.descripe_night = (TextView) convertView.findViewById(R.id.night_txt);
                convertView.setTag(viewHolder);
            }
            viewHolder.date.setText(dailyForecastBeans.get(position).getDate());
            viewHolder.max_Tem.setText(getString(R.string.pre_max_tem) + dailyForecastBeans.get(position).getTmp().getMax());
            viewHolder.min_Tem.setText(getString(R.string.pre_min_tem) + dailyForecastBeans.get(position).getTmp().getMin());
            viewHolder.descripe_day.setText(getString(R.string.pre_day_descripe) + dailyForecastBeans.get(position).getCond().getTxt_d());
            viewHolder.descripe_night.setText(getString(R.string.pre_night_descripe) + dailyForecastBeans.get(position).getCond().getTxt_n());
            return convertView;
        }

        public final class ViewHolder {
            public TextView date;
            public TextView max_Tem;
            public TextView min_Tem;
            public TextView descripe_day;
            public TextView descripe_night;
        }
    }


}

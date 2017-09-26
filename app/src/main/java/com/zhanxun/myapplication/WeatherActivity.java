package com.zhanxun.myapplication;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

import com.zhanxun.myapplication.bean.WeatherModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
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

        String city = m_editText.getText().toString().trim();
        if (TextUtils.isEmpty(city)) {
            Toast.makeText(this, "请输入城市名", Toast.LENGTH_SHORT).show();
            return;
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://free-api.heweather.com/v5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService service = retrofit.create(ApiService.class);

        Call<WeatherModel> call = service.postRequest(city, "96217f3f638b4d61ba3581bb184d889b");
        call.enqueue(new Callback<WeatherModel>() {
            @Override
            public void onResponse(Call<WeatherModel> call, Response<WeatherModel> response) {
                try {
                    loadData(response.body().getHeWeather5());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<WeatherModel> call, Throwable t) {
                t.printStackTrace();
                Log.i(TAG, "onFailure(),," + t.getMessage());
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

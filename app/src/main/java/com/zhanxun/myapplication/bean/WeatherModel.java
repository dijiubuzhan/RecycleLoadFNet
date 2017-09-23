package com.zhanxun.myapplication.bean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wilson on 2017/9/23.
 */

public class WeatherModel {
    private List<HeWeather5Bean> HeWeather5;


    public static WeatherModel objectFromData(String str) {

        return new Gson().fromJson(str, WeatherModel.class);
    }

    public static WeatherModel objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(str), WeatherModel.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<WeatherModel> arrayweatherFromData(String str) {

        Type listType = new TypeToken<ArrayList<WeatherModel>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public static List<WeatherModel> arrayweatherFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);
            Type listType = new TypeToken<ArrayList<WeatherModel>>() {
            }.getType();

            return new Gson().fromJson(jsonObject.getString(str), listType);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new ArrayList();


    }

    public List<HeWeather5Bean> getHeWeather5() {
        return HeWeather5;
    }

    public void setHeWeather5(List<HeWeather5Bean> HeWeather5) {
        this.HeWeather5 = HeWeather5;
    }

    public static class HeWeather5Bean {
        /**
         * basic : {"city":"上海","cnty":"中国","id":"CN101020100","lat":"31.23170662","lon":"121.47264099","update":{"loc":"2017-09-23 13:46","utc":"2017-09-23 05:46"}}
         * daily_forecast : [{"astro":{"mr":"08:18","ms":"19:54","sr":"05:42","ss":"17:49"},"cond":{"code_d":"306","code_n":"306","txt_d":"中雨","txt_n":"中雨"},"date":"2017-09-23","hum":"73","pcpn":"17.6","pop":"97","pres":"1016","tmp":{"max":"23","min":"21"},"uv":"1","vis":"17","wind":{"deg":"95","dir":"东风","sc":"微风","spd":"5"}},{"astro":{"mr":"09:13","ms":"20:31","sr":"05:43","ss":"17:48"},"cond":{"code_d":"305","code_n":"306","txt_d":"小雨","txt_n":"中雨"},"date":"2017-09-24","hum":"89","pcpn":"30.9","pop":"91","pres":"1014","tmp":{"max":"24","min":"23"},"uv":"1","vis":"12","wind":{"deg":"128","dir":"东南风","sc":"微风","spd":"3"}},{"astro":{"mr":"10:07","ms":"21:10","sr":"05:44","ss":"17:47"},"cond":{"code_d":"305","code_n":"306","txt_d":"小雨","txt_n":"中雨"},"date":"2017-09-25","hum":"83","pcpn":"16.4","pop":"83","pres":"1008","tmp":{"max":"26","min":"23"},"uv":"5","vis":"11","wind":{"deg":"122","dir":"东南风","sc":"微风","spd":"6"}}]
         * status : ok
         */

        private BasicBean basic;
        private String status;
        private List<DailyForecastBean> daily_forecast;

        public static HeWeather5Bean objectFromData(String str) {

            return new Gson().fromJson(str, HeWeather5Bean.class);
        }

        public static HeWeather5Bean objectFromData(String str, String key) {

            try {
                JSONObject jsonObject = new JSONObject(str);

                return new Gson().fromJson(jsonObject.getString(str), HeWeather5Bean.class);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        public static List<HeWeather5Bean> arrayHeWeather5BeanFromData(String str) {

            Type listType = new TypeToken<ArrayList<HeWeather5Bean>>() {
            }.getType();

            return new Gson().fromJson(str, listType);
        }

        public static List<HeWeather5Bean> arrayHeWeather5BeanFromData(String str, String key) {

            try {
                JSONObject jsonObject = new JSONObject(str);
                Type listType = new TypeToken<ArrayList<HeWeather5Bean>>() {
                }.getType();

                return new Gson().fromJson(jsonObject.getString(str), listType);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return new ArrayList();


        }

        public BasicBean getBasic() {
            return basic;
        }

        public void setBasic(BasicBean basic) {
            this.basic = basic;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public List<DailyForecastBean> getDaily_forecast() {
            return daily_forecast;
        }

        public void setDaily_forecast(List<DailyForecastBean> daily_forecast) {
            this.daily_forecast = daily_forecast;
        }

        public static class BasicBean {
            /**
             * city : 上海
             * cnty : 中国
             * id : CN101020100
             * lat : 31.23170662
             * lon : 121.47264099
             * update : {"loc":"2017-09-23 13:46","utc":"2017-09-23 05:46"}
             */

            private String city;
            private String cnty;
            private String id;
            private String lat;
            private String lon;
            private UpdateBean update;

            public static BasicBean objectFromData(String str) {

                return new Gson().fromJson(str, BasicBean.class);
            }

            public static BasicBean objectFromData(String str, String key) {

                try {
                    JSONObject jsonObject = new JSONObject(str);

                    return new Gson().fromJson(jsonObject.getString(str), BasicBean.class);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return null;
            }

            public static List<BasicBean> arrayBasicBeanFromData(String str) {

                Type listType = new TypeToken<ArrayList<BasicBean>>() {
                }.getType();

                return new Gson().fromJson(str, listType);
            }

            public static List<BasicBean> arrayBasicBeanFromData(String str, String key) {

                try {
                    JSONObject jsonObject = new JSONObject(str);
                    Type listType = new TypeToken<ArrayList<BasicBean>>() {
                    }.getType();

                    return new Gson().fromJson(jsonObject.getString(str), listType);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return new ArrayList();


            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getCnty() {
                return cnty;
            }

            public void setCnty(String cnty) {
                this.cnty = cnty;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getLat() {
                return lat;
            }

            public void setLat(String lat) {
                this.lat = lat;
            }

            public String getLon() {
                return lon;
            }

            public void setLon(String lon) {
                this.lon = lon;
            }

            public UpdateBean getUpdate() {
                return update;
            }

            public void setUpdate(UpdateBean update) {
                this.update = update;
            }

            public static class UpdateBean {
                /**
                 * loc : 2017-09-23 13:46
                 * utc : 2017-09-23 05:46
                 */

                private String loc;
                private String utc;

                public static UpdateBean objectFromData(String str) {

                    return new Gson().fromJson(str, UpdateBean.class);
                }

                public static UpdateBean objectFromData(String str, String key) {

                    try {
                        JSONObject jsonObject = new JSONObject(str);

                        return new Gson().fromJson(jsonObject.getString(str), UpdateBean.class);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    return null;
                }

                public static List<UpdateBean> arrayUpdateBeanFromData(String str) {

                    Type listType = new TypeToken<ArrayList<UpdateBean>>() {
                    }.getType();

                    return new Gson().fromJson(str, listType);
                }

                public static List<UpdateBean> arrayUpdateBeanFromData(String str, String key) {

                    try {
                        JSONObject jsonObject = new JSONObject(str);
                        Type listType = new TypeToken<ArrayList<UpdateBean>>() {
                        }.getType();

                        return new Gson().fromJson(jsonObject.getString(str), listType);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    return new ArrayList();


                }

                public String getLoc() {
                    return loc;
                }

                public void setLoc(String loc) {
                    this.loc = loc;
                }

                public String getUtc() {
                    return utc;
                }

                public void setUtc(String utc) {
                    this.utc = utc;
                }
            }
        }

        public static class DailyForecastBean {
            /**
             * astro : {"mr":"08:18","ms":"19:54","sr":"05:42","ss":"17:49"}
             * cond : {"code_d":"306","code_n":"306","txt_d":"中雨","txt_n":"中雨"}
             * date : 2017-09-23
             * hum : 73
             * pcpn : 17.6
             * pop : 97
             * pres : 1016
             * tmp : {"max":"23","min":"21"}
             * uv : 1
             * vis : 17
             * wind : {"deg":"95","dir":"东风","sc":"微风","spd":"5"}
             */

            private AstroBean astro;
            private CondBean cond;
            private String date;
            private String hum;
            private String pcpn;
            private String pop;
            private String pres;
            private TmpBean tmp;
            private String uv;
            private String vis;
            private WindBean wind;

            public static DailyForecastBean objectFromData(String str) {

                return new Gson().fromJson(str, DailyForecastBean.class);
            }

            public static DailyForecastBean objectFromData(String str, String key) {

                try {
                    JSONObject jsonObject = new JSONObject(str);

                    return new Gson().fromJson(jsonObject.getString(str), DailyForecastBean.class);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return null;
            }

            public static List<DailyForecastBean> arrayDailyForecastBeanFromData(String str) {

                Type listType = new TypeToken<ArrayList<DailyForecastBean>>() {
                }.getType();

                return new Gson().fromJson(str, listType);
            }

            public static List<DailyForecastBean> arrayDailyForecastBeanFromData(String str, String key) {

                try {
                    JSONObject jsonObject = new JSONObject(str);
                    Type listType = new TypeToken<ArrayList<DailyForecastBean>>() {
                    }.getType();

                    return new Gson().fromJson(jsonObject.getString(str), listType);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return new ArrayList();


            }

            public AstroBean getAstro() {
                return astro;
            }

            public void setAstro(AstroBean astro) {
                this.astro = astro;
            }

            public CondBean getCond() {
                return cond;
            }

            public void setCond(CondBean cond) {
                this.cond = cond;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getHum() {
                return hum;
            }

            public void setHum(String hum) {
                this.hum = hum;
            }

            public String getPcpn() {
                return pcpn;
            }

            public void setPcpn(String pcpn) {
                this.pcpn = pcpn;
            }

            public String getPop() {
                return pop;
            }

            public void setPop(String pop) {
                this.pop = pop;
            }

            public String getPres() {
                return pres;
            }

            public void setPres(String pres) {
                this.pres = pres;
            }

            public TmpBean getTmp() {
                return tmp;
            }

            public void setTmp(TmpBean tmp) {
                this.tmp = tmp;
            }

            public String getUv() {
                return uv;
            }

            public void setUv(String uv) {
                this.uv = uv;
            }

            public String getVis() {
                return vis;
            }

            public void setVis(String vis) {
                this.vis = vis;
            }

            public WindBean getWind() {
                return wind;
            }

            public void setWind(WindBean wind) {
                this.wind = wind;
            }

            public static class AstroBean {
                /**
                 * mr : 08:18
                 * ms : 19:54
                 * sr : 05:42
                 * ss : 17:49
                 */

                private String mr;
                private String ms;
                private String sr;
                private String ss;

                public static AstroBean objectFromData(String str) {

                    return new Gson().fromJson(str, AstroBean.class);
                }

                public static AstroBean objectFromData(String str, String key) {

                    try {
                        JSONObject jsonObject = new JSONObject(str);

                        return new Gson().fromJson(jsonObject.getString(str), AstroBean.class);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    return null;
                }

                public static List<AstroBean> arrayAstroBeanFromData(String str) {

                    Type listType = new TypeToken<ArrayList<AstroBean>>() {
                    }.getType();

                    return new Gson().fromJson(str, listType);
                }

                public static List<AstroBean> arrayAstroBeanFromData(String str, String key) {

                    try {
                        JSONObject jsonObject = new JSONObject(str);
                        Type listType = new TypeToken<ArrayList<AstroBean>>() {
                        }.getType();

                        return new Gson().fromJson(jsonObject.getString(str), listType);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    return new ArrayList();


                }

                public String getMr() {
                    return mr;
                }

                public void setMr(String mr) {
                    this.mr = mr;
                }

                public String getMs() {
                    return ms;
                }

                public void setMs(String ms) {
                    this.ms = ms;
                }

                public String getSr() {
                    return sr;
                }

                public void setSr(String sr) {
                    this.sr = sr;
                }

                public String getSs() {
                    return ss;
                }

                public void setSs(String ss) {
                    this.ss = ss;
                }
            }

            public static class CondBean {
                /**
                 * code_d : 306
                 * code_n : 306
                 * txt_d : 中雨
                 * txt_n : 中雨
                 */

                private String code_d;
                private String code_n;
                private String txt_d;
                private String txt_n;

                public static CondBean objectFromData(String str) {

                    return new Gson().fromJson(str, CondBean.class);
                }

                public static CondBean objectFromData(String str, String key) {

                    try {
                        JSONObject jsonObject = new JSONObject(str);

                        return new Gson().fromJson(jsonObject.getString(str), CondBean.class);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    return null;
                }

                public static List<CondBean> arrayCondBeanFromData(String str) {

                    Type listType = new TypeToken<ArrayList<CondBean>>() {
                    }.getType();

                    return new Gson().fromJson(str, listType);
                }

                public static List<CondBean> arrayCondBeanFromData(String str, String key) {

                    try {
                        JSONObject jsonObject = new JSONObject(str);
                        Type listType = new TypeToken<ArrayList<CondBean>>() {
                        }.getType();

                        return new Gson().fromJson(jsonObject.getString(str), listType);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    return new ArrayList();


                }

                public String getCode_d() {
                    return code_d;
                }

                public void setCode_d(String code_d) {
                    this.code_d = code_d;
                }

                public String getCode_n() {
                    return code_n;
                }

                public void setCode_n(String code_n) {
                    this.code_n = code_n;
                }

                public String getTxt_d() {
                    return txt_d;
                }

                public void setTxt_d(String txt_d) {
                    this.txt_d = txt_d;
                }

                public String getTxt_n() {
                    return txt_n;
                }

                public void setTxt_n(String txt_n) {
                    this.txt_n = txt_n;
                }
            }

            public static class TmpBean {
                /**
                 * max : 23
                 * min : 21
                 */

                private String max;
                private String min;

                public static TmpBean objectFromData(String str) {

                    return new Gson().fromJson(str, TmpBean.class);
                }

                public static TmpBean objectFromData(String str, String key) {

                    try {
                        JSONObject jsonObject = new JSONObject(str);

                        return new Gson().fromJson(jsonObject.getString(str), TmpBean.class);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    return null;
                }

                public static List<TmpBean> arrayTmpBeanFromData(String str) {

                    Type listType = new TypeToken<ArrayList<TmpBean>>() {
                    }.getType();

                    return new Gson().fromJson(str, listType);
                }

                public static List<TmpBean> arrayTmpBeanFromData(String str, String key) {

                    try {
                        JSONObject jsonObject = new JSONObject(str);
                        Type listType = new TypeToken<ArrayList<TmpBean>>() {
                        }.getType();

                        return new Gson().fromJson(jsonObject.getString(str), listType);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    return new ArrayList();


                }

                public String getMax() {
                    return max;
                }

                public void setMax(String max) {
                    this.max = max;
                }

                public String getMin() {
                    return min;
                }

                public void setMin(String min) {
                    this.min = min;
                }
            }

            public static class WindBean {
                /**
                 * deg : 95
                 * dir : 东风
                 * sc : 微风
                 * spd : 5
                 */

                private String deg;
                private String dir;
                private String sc;
                private String spd;

                public static WindBean objectFromData(String str) {

                    return new Gson().fromJson(str, WindBean.class);
                }

                public static WindBean objectFromData(String str, String key) {

                    try {
                        JSONObject jsonObject = new JSONObject(str);

                        return new Gson().fromJson(jsonObject.getString(str), WindBean.class);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    return null;
                }

                public static List<WindBean> arrayWindBeanFromData(String str) {

                    Type listType = new TypeToken<ArrayList<WindBean>>() {
                    }.getType();

                    return new Gson().fromJson(str, listType);
                }

                public static List<WindBean> arrayWindBeanFromData(String str, String key) {

                    try {
                        JSONObject jsonObject = new JSONObject(str);
                        Type listType = new TypeToken<ArrayList<WindBean>>() {
                        }.getType();

                        return new Gson().fromJson(jsonObject.getString(str), listType);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    return new ArrayList();


                }

                public String getDeg() {
                    return deg;
                }

                public void setDeg(String deg) {
                    this.deg = deg;
                }

                public String getDir() {
                    return dir;
                }

                public void setDir(String dir) {
                    this.dir = dir;
                }

                public String getSc() {
                    return sc;
                }

                public void setSc(String sc) {
                    this.sc = sc;
                }

                public String getSpd() {
                    return spd;
                }

                public void setSpd(String spd) {
                    this.spd = spd;
                }
            }
        }
    }
}

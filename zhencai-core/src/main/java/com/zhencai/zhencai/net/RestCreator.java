package com.zhencai.zhencai.net;

import android.util.ArrayMap;

import com.zhencai.zhencai.app.ConfigType;
import com.zhencai.zhencai.app.ZhenCai;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * 创建Retrofit和对应的service
 * Created by Administrator on 2018/4/4 0004.
 */
public class RestCreator {


    private static final class ParamsHolder{
        public static final ArrayMap<String,Object> PARAMS = new ArrayMap<>();
    }
    public static ArrayMap<String,Object> getParams(){
        return ParamsHolder.PARAMS;
    }


    private static final class RetrofitHolder{
        private static final String BASE_URL = (String)ZhenCai.getConfigurations().get(ConfigType.API_HOST.name());
        private static final Retrofit RETROFIT_CLIENT = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
    }

    private static final class OKHttpHolder{
        private static final int Time_OUT = 60;
        private static final OkHttpClient OK_HTTP_CLIENT = new OkHttpClient.Builder()
                .connectTimeout(Time_OUT, TimeUnit.SECONDS)
                .build();
    }

    private static final class RestServiceHolder{
        private static final RestService REST_SERVICE =
                RetrofitHolder.RETROFIT_CLIENT.create(RestService.class);
    }

    public static RestService getRestService(){
        return RestServiceHolder.REST_SERVICE;
    }
}

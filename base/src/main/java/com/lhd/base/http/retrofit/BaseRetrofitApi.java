package com.lhd.base.http.retrofit;
/*
 * 项目名:    BaseExample
 * 包名       com.lhd.base.http.retrofit
 * 文件名:    BaseRetrofitApi
 * 创建者:    YHF
 * 创建时间:  2018/7/6 0006 on 16:02
 * 描述:     TODO
 */

import android.util.Log;

import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class BaseRetrofitApi {

    private Retrofit retrofit;
    public static volatile BaseRetrofitApi instance;

    public static Retrofit getInstance() {
        if (instance == null) {
            synchronized (BaseRetrofitApi.class) {
                if (instance == null) {
                    instance = new BaseRetrofitApi();
                }
            }
        }
        return instance.getRetrofit();
    }

    public BaseRetrofitApi() {
        retrofit = createRetrofit();
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

    private Retrofit createRetrofit() {
        retrofit = new Retrofit.Builder().addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().create()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(getOkHttpClient())
                .baseUrl(Contact.BASE_URL)
                .build();
        return retrofit;
    }

    /**
     * 自定义OkHttpClient拦截请求参数并加上 加密参数
     */
    private OkHttpClient getOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //日志显示级别
        HttpLoggingInterceptor.Level level = HttpLoggingInterceptor.Level.HEADERS;
        //新建log拦截器
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.d("ApiUrl", "--->" + message);
            }
        });
        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request oldRequest = chain.request();

                // 添加新的参数
                HttpUrl.Builder authorizedUrlBuilder = oldRequest.url()
                        .newBuilder()
                        .scheme(oldRequest.url().scheme())
                        .host(oldRequest.url().host());
//                        .addQueryParameter("requestCode", AppData.getInstance().getRequestCode());

                // 新的请求
                Request newRequest = oldRequest.newBuilder()
                        .method(oldRequest.method(), oldRequest.body())
                        .url(authorizedUrlBuilder.build())
                        .build();

                return chain.proceed(newRequest);
            }
        };
        loggingInterceptor.setLevel(level);
        builder.addInterceptor(loggingInterceptor);
        //设置超时
        builder.connectTimeout(15, TimeUnit.SECONDS);
        builder.readTimeout(15, TimeUnit.SECONDS);
        builder.writeTimeout(15, TimeUnit.SECONDS);
        //错误重连
        builder.retryOnConnectionFailure(true);
        builder.addInterceptor(interceptor);
        return builder.build();
    }

}

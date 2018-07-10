package com.lhd.base;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.lhd.base.Net.Net;
import com.lhd.base.Net.RetrofitResponse;
import com.lhd.base.http.retrofit.BaseRetrofitApi;
import com.lhd.base.http.retrofit.exception.ApiException;
import com.lhd.base.main.BaseBean;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

@SuppressLint("CheckResult")
public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        change(BaseRetrofitApi.getInstance().create(Net.class).getWeather("杭州"), 1, response);
    }

    private RetrofitResponse response = new RetrofitResponse() {
        @Override
        public void success(int id, BaseBean bean) {
            Log.d("bbbbb", "success-->" + id + "<--id" + new Gson().toJson(bean));
        }

        @Override
        public void error(int id, Throwable throwable) {
            Log.d("bbbbb", "error-->" + throwable.toString());
        }
    };

    private void change(Observable<? extends BaseBean> observable, final int id, final RetrofitResponse response) {
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).filter(new Predicate<BaseBean>() {
            @Override
            public boolean test(BaseBean baseBean) {
                if (baseBean.getCode() == 200) {
                    return true;
                } else {
                    throw new ApiException(baseBean.getMsg());
                }
            }
        }).subscribe(new Consumer<BaseBean>() {
            @Override
            public void accept(BaseBean bean) {
                response.success(id, bean);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) {
                response.error(id, throwable);
            }
        });
    }

}

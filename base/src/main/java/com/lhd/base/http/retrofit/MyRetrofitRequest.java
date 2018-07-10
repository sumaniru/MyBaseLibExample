package com.lhd.base.http.retrofit;

import android.annotation.SuppressLint;

import com.lhd.base.http.retrofit.exception.ApiException;
import com.lhd.base.interfaces.RetrofitResponse;
import com.lhd.base.main.BaseBean;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

public class MyRetrofitRequest {

    @SuppressLint("CheckResult")
    public  void request(final int id, Observable<BaseBean> observable, final RetrofitResponse response) {
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

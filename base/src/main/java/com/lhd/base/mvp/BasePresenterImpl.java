package com.lhd.base.mvp;

import android.annotation.SuppressLint;

import com.lhd.base.http.retrofit.exception.ApiException;
import com.lhd.base.interfaces.RetrofitResponse;
import com.lhd.base.main.BaseBean;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

public abstract class BasePresenterImpl<V extends BaseView> implements BasePresenter {

    protected V view;
    private final WeakReference<V> vWeakReference;

    public BasePresenterImpl(BaseView view) {
        vWeakReference = (WeakReference<V>) new WeakReference<>(view);
        this.view = vWeakReference.get();
    }

}

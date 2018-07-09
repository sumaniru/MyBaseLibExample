package com.lhd.base.mvp;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

public abstract class BasePresenterImpl<V extends BaseView> implements BasePresenter {

    protected V view;
    private final WeakReference<V> vWeakReference;

    public BasePresenterImpl(BaseView view) {
        vWeakReference = (WeakReference<V>) new WeakReference<>(view);
        this.view = vWeakReference.get();
    }




}

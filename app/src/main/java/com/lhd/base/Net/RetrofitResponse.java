package com.lhd.base.Net;

import com.lhd.base.main.BaseBean;

public interface RetrofitResponse {

    void success(int id, BaseBean bean);

    void error(int id, Throwable throwable);

}

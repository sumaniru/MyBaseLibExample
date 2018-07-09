package com.lhd.base.Net;
/*
 * 项目名:    BaseExample
 * 包名       com.lhd.base.Net
 * 文件名:    Net
 * 创建者:    YHF
 * 创建时间:  2018/7/6 0006 on 16:18
 * 描述:     TODO
 */

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Net {

    @POST("weatherApi")
    @FormUrlEncoded
    Observable<String> getWeather(@Field("city") String city);

}

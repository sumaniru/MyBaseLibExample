package com.lhd.base.http.retrofit.exception;

import android.net.ParseException;

import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

/*
 * 项目名:    Pigeon
 * 包名       cn.hjtech.directory.common.retroft
 * 文件名:    ExceptionHelper
 * 创建者:    ZJB
 * 创建时间:  2017/5/19 on 20:45
 * 描述:     TODO 异常抛出帮助类
 */

public class ExceptionHelper {

    public static String handleException(Throwable e) {
        e.printStackTrace();
        String error;

        if (e instanceof SocketTimeoutException) {
            //网络超时
            error = "网络连接异常";
        } else if (e instanceof ConnectException) {
            //均视为网络错误
            error = "网络连接异常";
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {
            //均视为解析错误
            error = "数据解析异常";
        } else if (e instanceof ApiException) {
            //服务器返回的错误信息
            error = e.getCause().getMessage();
        } else if (e instanceof UnknownHostException) {
            error = "网络连接异常";
        } else if (e instanceof IllegalArgumentException) {
            error = "下载文件已存在";
        } else {
            //未知错误
            error = "错误";
        }
        return error;
    }

}
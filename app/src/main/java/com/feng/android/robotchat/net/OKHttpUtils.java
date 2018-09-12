package com.feng.android.robotchat.net;

import android.os.Looper;

import android.os.Handler;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public class OKHttpUtils {

    public static OkHttpClient sOkHttpClient;
    private static OKHttpUtils sOKHttpUtils;
    private final Handler handler;


    private OKHttpUtils(){
        handler = new Handler(Looper.getMainLooper());
        sOkHttpClient = new OkHttpClient.Builder()
                .connectTimeout(5000, TimeUnit.MILLISECONDS)
                .readTimeout(5000,TimeUnit.MILLISECONDS)
                .writeTimeout(5000,TimeUnit.MILLISECONDS)
                .build();
    }

    public static OKHttpUtils getInstance(){
        if(sOKHttpUtils == null){
            synchronized (OKHttpUtils.class){
                if (sOKHttpUtils == null){
                    sOKHttpUtils = new OKHttpUtils();
                    return sOKHttpUtils;
                }
            }
        }
        return sOKHttpUtils;
    }




}

package com.sunsoft_supplier.net.network;

import android.os.CountDownTimer;

import com.sunsoft_supplier.net.checknet.CheckNet;
import com.sunsoft_supplier.utils.LogUtil;
import com.sunsoft_supplier.utils.ToastUtil;

import okhttp3.Call;


/**
 * 监听网络请求时间
 * Created by MJX on 2017/4/17.
 */
public class HttpCountDown {

    private static CountDownTimer countDownTimer;
    /**
     * 是否需要取消当前的request请求,false不取消当前的请求，true取消当前的请求
     */
    private static boolean cancelCurrentRequest;
    /**
     * 是否已经取消了当前的请求
     */
    public static boolean hasCanceledCurrentRequest = false;

    public static void startCountDownRequestHttp(final Call call, final HttpMethod.OnDataFinish onDataFinish) {
        cancelCurrentRequest = false;
        hasCanceledCurrentRequest = false;
        countDownTimer = new CountDownTimer(10000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                endCountDownTime();
                if (call != null) {
                    call.cancel();
                    hasCanceledCurrentRequest = true;
                    onDataFinish.OnError("超时");
                    if (CheckNet.isHaveNetWork()) {
                        ToastUtil.toastDes("网络连接失败");
                        LogUtil.logMsg("网络连接失败-----------定时器onFinish");
                    }
                }

            }
        };
        countDownTimer.start();
    }

    public static void endCountDownTime() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}

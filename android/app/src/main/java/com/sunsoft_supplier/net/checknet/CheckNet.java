package com.sunsoft_supplier.net.checknet;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.sunsoft_supplier.utils.UIUtil;


/**
 * 检查网络
 * Created by MJX on 2017/1/11.
 */
public class CheckNet {
    private static ConnectivityManager connectMager;
    private static Boolean hasNetFlag = false;

    /**
     * 有无网络的判断
     */
    public static boolean isHaveNetWork() {
        if (connectMager == null) {
            connectMager = (ConnectivityManager) UIUtil.getContext()
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
        }
        NetworkInfo info = connectMager.getActiveNetworkInfo();
        if (info == null) {
            hasNetFlag = false;
        } else {
            hasNetFlag = true;
        }
        return hasNetFlag;
    }

    /**
     * 检查当前网络
     * 0 ： 无网
     * 1：wifi
     * 2：手机流量
     *
     * @return
     */
    public static int checkWifiOrMobile() {
        int isNetFlag  = 0;
        if (connectMager == null) {
            connectMager = (ConnectivityManager) UIUtil.getContext()
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
        }
        if (isHaveNetWork()) {
            NetworkInfo wifi = connectMager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (wifi.isConnected()) {
                isNetFlag = 1;
            }
            NetworkInfo mobile = connectMager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (mobile.isConnected()) {
                isNetFlag = 2;
            }
        } else {
            isNetFlag = 0;
        }
        return isNetFlag;
    }
}

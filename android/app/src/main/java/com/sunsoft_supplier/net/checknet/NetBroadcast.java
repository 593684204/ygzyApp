package com.sunsoft_supplier.net.checknet;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.util.Log;

import com.sunsoft_supplier.MainApplication;
import com.sunsoft_supplier.data.AppSp;
import com.sunsoft_supplier.utils.AppisBackgroundUtil;

import org.greenrobot.eventbus.EventBus;


/**
 * 监听网络的变化
 * Created by XL on 2017/4/12.
 */
public class NetBroadcast extends BroadcastReceiver {
    private long firstTime;

    @Override
    public void onReceive(Context context, Intent intent) {
        /*拦截第一次动态注册时候，两次刷新界面*/
       /* if (MainApplication.getInstance().isInterceptNet()) {
            MainApplication.getInstance().setInterceptNet(false);
            return;
        }*/
        String action = intent.getAction();
        if (TextUtils.equals(action, "android.net.conn.CONNECTIVITY_CHANGE")) {//网络变化的时候会发送通知
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mobNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            NetworkInfo wifiNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            /*vivo手机检测不到在后台运行，增加home键检测判断是否进入后台*/
            if (AppisBackgroundUtil.isApplicationBroughtToBackground() || AppSp.getIsClickHome()) {
                return;
            }
            firstTime = System.currentTimeMillis();
            if (wifiNetInfo.isConnected()) {//wifi网络
                if (firstTime - MainApplication.lastTime > 3000) {
                    MainApplication.lastTime = firstTime;
                    Log.i("--------", "网络可用,wifi:" + wifiNetInfo.isConnected());
                    EventBus.getDefault().post(
                            new NetEvent(true));
                }
            } else if (mobNetInfo.isConnected()) {//手机网络
                Log.i("--------", "网络可用,mob:" + mobNetInfo.isConnected());
                EventBus.getDefault().post(
                        new NetEvent(true));
            } else { //无网络
                Log.i("--------", "网络不可用");
                EventBus.getDefault().post(
                        new NetEvent(false));
            }
        }
    }
}

package com.sunsoft_supplier.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.sunsoft_supplier.constant.Constant;
import com.sunsoft_supplier.utils.LogUtil;

import java.util.Timer;
import java.util.TimerTask;

public class KillAppService extends Service {
    private Timer timer;
    private TimerTask timerTask;

    public KillAppService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtil.logMsg("onStartCommand");
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                LogUtil.logMsg("后台杀掉APP");
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        };
        timer.schedule(timerTask, Constant.KILL_TIME * 1000);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.logMsg("onStartCommand");
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }
}

package com.sunsoft_supplier.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;

import com.sunsoft_supplier.utils.LogUtil;

public class KillSelfService extends Service {
    /**关闭应用后多久重新启动*/
    private static  long stopDelayed=2000;
    private Handler handler;
    private String PackageName="";
    public KillSelfService() {
        handler=new Handler();
    }

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {
//        stopDelayed=intent.getLongExtra("Delayed",1000);
        if(intent!=null) {
            PackageName = intent.getStringExtra("PackageName");
        }
        LogUtil.logMsg(PackageName);
        handler.post(new Runnable() {
            @Override
            public void run() {
                Intent LaunchIntent = getPackageManager().getLaunchIntentForPackage(PackageName);
                startActivity(LaunchIntent);
                KillSelfService.this.stopSelf();
            }
        });
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

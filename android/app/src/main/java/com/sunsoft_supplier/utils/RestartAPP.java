package com.sunsoft_supplier.utils;

import android.content.Context;
import android.content.Intent;

import com.sunsoft_supplier.service.KillAppService;
import com.sunsoft_supplier.service.KillSelfService;

/**
 * Created by Administrator on 2018/5/11.
 */

public class RestartAPP {
    /**
     * 重启整个APP
     * @param context
     * @param
     */
    public static void restartAPP(Context context){

        /**开启一个新的服务，用来重启本APP*/
        Intent intent1=new Intent(context, KillSelfService.class);
        intent1.putExtra("PackageName",context.getPackageName());
        context.startService(intent1);

        /**杀死整个进程**/
        android.os.Process.killProcess(android.os.Process.myPid());
    }
    public static void startKillAPPService(Context context){
        if(context==null){
            return;
        }
        /**开启一个新的服务，用来重启本APP*/
        Intent intent=new Intent(context, KillAppService.class);
        context.startService(intent);
    }
    public static void stopKillAPPService(Context context){
        if(context==null){
            return;
        }
        /**开启一个新的服务，用来重启本APP*/
        Intent intent=new Intent(context, KillAppService.class);
        context.stopService(intent);
    }

}

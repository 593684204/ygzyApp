package com.sunsoft_supplier.utils;

/**
 * Created by Administrator on 2017/4/25.
 */

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.util.Log;

import java.util.List;

/**
 * 判断当前的App是在前端还是在后台
 * Created by MJX on 2016/8/10.
 */
public class AppisBackgroundUtil {
    /**
     * 判断当前应用程序处于前台还是后台
     *
     * @return true是进入后台，false是进入前台
     */
    public static boolean isApplicationBroughtToBackground() {
        Context context = UIUtil.getContext();
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断应用是否在运行
     *
     * @param context
     * @return
     */
    public static boolean isRun(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(100);
        boolean isAppRunning = false;
        String MY_PKG_NAME = context.getPackageName();
        //100表示取的最大的任务数，info.topActivity表示当前正在运行的Activity，info.baseActivity表系统后台有此进程在运行
        for (ActivityManager.RunningTaskInfo info : list) {
            if (info.topActivity.getPackageName().equals(MY_PKG_NAME) || info.baseActivity.getPackageName().equals(MY_PKG_NAME)) {
                isAppRunning = true;
                Log.i("ActivityService isRun()", info.topActivity.getPackageName() + " info.baseActivity.getPackageName()=" + info.baseActivity.getPackageName());
                break;
            }
        }
        Log.i("ActivityService isRun()", "com.ad 程序   ...isAppRunning......" + isAppRunning);
        return isAppRunning;
    }

}

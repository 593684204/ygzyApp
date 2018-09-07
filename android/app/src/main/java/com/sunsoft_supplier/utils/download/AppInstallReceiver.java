package com.sunsoft_supplier.utils.download;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.widget.Toast;

/**
 * APK安装的
 * Created by MJX on 2017/4/26.
 */
public class AppInstallReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        PackageManager manager = context.getPackageManager();
        if (intent.getAction().equals(Intent.ACTION_PACKAGE_ADDED)) {
            String packageName = intent.getData().getSchemeSpecificPart();
            Toast.makeText(context, "安装成功"+packageName, Toast.LENGTH_LONG).show();
        }
        if (intent.getAction().equals(Intent.ACTION_PACKAGE_REMOVED)) {
            String packageName = intent.getData().getSchemeSpecificPart();
            Toast.makeText(context, "卸载成功"+packageName, Toast.LENGTH_LONG).show();
        }
        if (intent.getAction().equals(Intent.ACTION_PACKAGE_REPLACED)) {
            String packageName = intent.getData().getSchemeSpecificPart();
            Toast.makeText(context, "替换成功"+packageName, Toast.LENGTH_LONG).show();
        }


    }

}
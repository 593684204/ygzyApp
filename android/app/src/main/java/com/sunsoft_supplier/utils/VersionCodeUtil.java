package com.sunsoft_supplier.utils;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;


/**
 * 获取Apk的版本号
 * Created by MJX on 2017/4/12.
 */
public class VersionCodeUtil {
    public static String getVersionName()  {
        try {
            PackageManager packageManager = UIUtil.getContext().getPackageManager();
            PackageInfo packInfo = packageManager.getPackageInfo(UIUtil.getContext().getPackageName(),
                    0);
            return packInfo.versionName;
        }catch (Exception e){

        }
        return null;
    }
}

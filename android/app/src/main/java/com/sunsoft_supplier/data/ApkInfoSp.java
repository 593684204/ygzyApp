package com.sunsoft_supplier.data;

/**
 * 存储当前的apk的版本号,APK覆盖安装时，解压bundle
 * Created by MJX on 2017/6/13.
 */
public class ApkInfoSp {
    /**
     * 保存当前的Apk的版本号
     * @param versionCode
     */
    public static void saveCurrentApkVersionCode(String versionCode){
        GetSpInsance.saveSp("APkVersion","versionCode",versionCode);
    }

    /**
     * 得到当前的apk的版本号
     * @return
     */
    public static String getCurrentApkVersionCode(){
       return (String) GetSpInsance.getSpValue("APkVersion","versionCode","-1");
    }

}

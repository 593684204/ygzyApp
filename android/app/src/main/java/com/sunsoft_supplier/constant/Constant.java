package com.sunsoft_supplier.constant;

import com.sunsoft_supplier.utils.download.ApkUtil;

import java.io.File;

/**
 * 常量
 * Created by MJX on 2017/3/30.
 */
public class Constant {
    /**
     * 是否开启调试模式，true调试模式，false正式模式
     * 影响范围：极光推送，友盟统计
     */
    public static final boolean ISDebugMode = false;
    //初始化的时候，bundle的zip包的名字
    public static final String BUNDLE_NAME = "android_20180522_1_1_4_1_0";
    public static boolean isShow = true;
    //Apk的下载路径
    public static final File APK_PATH = ApkUtil.getDownLoadApkPath();
    //超时时间
    public static final int TIME_OUT = 10;
    //实体返回键间隔
    public static final int DOUBLE_BACK_INTERVAL = 2000;
    //密码最小长度
    public static final int MIN_LENGTH = 6;
    //密码最大长度
    public static final int MAX_LENGTH = 20;

    /*无网络*/
    public static final int NET_NO = 0;
    /*手机WiFi*/
    public static final int NET_WIFI = 1;
    /*手机流量*/
    public static final int NET_MOBILE = 2;

    public static String BASE_URL = "https://ssl.ygzykj.com:8480/sunsoft-supplier-app/";
    public static int KILL_TIME = 30 * 60;
}

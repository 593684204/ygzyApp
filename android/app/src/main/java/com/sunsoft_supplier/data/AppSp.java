package com.sunsoft_supplier.data;

/**
 * Created by Administrator on 2018/4/26.
 */

public class AppSp {
    /*是否点击了home键*/
    public static void saveIsClickHome(boolean isBounceUp){
        GetSpInsance.saveSp("APP_UTILS","isClickHome",isBounceUp);
    }

    public static boolean getIsClickHome(){
        return (Boolean)GetSpInsance.getSpValue("APP_UTILS","isClickHome",false);
    }

    public static void saveIsInterceptNet(boolean isBounceUp){
        GetSpInsance.saveSp("APP_UTILS","isInterceptNet",isBounceUp);
    }

    public static boolean getIsInterceptNet(){
        return (Boolean)GetSpInsance.getSpValue("APP_UTILS","isInterceptNet",false);
    }

}

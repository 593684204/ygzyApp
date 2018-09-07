package com.sunsoft_supplier.mvp.updateapk;

/**
 * WIFI或者流量监听
 * Created by MJX on 2017/4/20.
 */
public interface IWifiOrMobileListener {
    /**
     * 取消正常更新
     */
    void cancelNormalDownLoad();

    /**
     * 继续正常更新
     */
    void goOnNormalDownLoad();
    /**
     * 继续强制更新
     */
    void goOnForcedDownLoad();

}

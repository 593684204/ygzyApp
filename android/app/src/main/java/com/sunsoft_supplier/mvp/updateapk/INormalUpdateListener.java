package com.sunsoft_supplier.mvp.updateapk;

/**
 * 正常更新，。监听
 * Created by MJX on 2017/4/24.
 */
public interface INormalUpdateListener {
    /**
     * 正常去更新APK
     */
    void normalUpdateApk();

    /**
     * 取消正常更新
     */
    void normalCancelUpdateApk();
}

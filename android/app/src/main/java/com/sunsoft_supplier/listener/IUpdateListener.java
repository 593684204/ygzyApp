package com.sunsoft_supplier.listener;

/**
 * Created by Administrator on 2017/4/13.
 */
public interface IUpdateListener {
    /**
     * 下载apk
     */
    void updateApk();

    /**
     * 继续进入启动页
     */
    void closeDialog();
}

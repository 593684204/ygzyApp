package com.sunsoft_supplier.mvp.updateapk.contract;


import com.sunsoft_supplier.net.network.IDownloadListen;

import java.io.File;

/**
 * APK的更新
 * Created by MJX on 2017/4/21.
 */
public interface UpdateApkContract {
    interface IUpdateApkPresenter{
        /**
         * 下载APK
         */
        void download(String tag,String url);
    }

    interface IUpdateApkModel{
        /**
         * 下载文件
         * @param url
         * @param savePath
         * @param listen
         */
        void downloadUpdateFile(String tag, String url, File savePath, IDownloadListen listen);
    }
}

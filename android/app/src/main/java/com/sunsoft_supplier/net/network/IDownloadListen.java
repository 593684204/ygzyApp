package com.sunsoft_supplier.net.network;

/**
 * Created by Administrator on 2017/4/11.
 */
public interface IDownloadListen {
    /**
     * 下载成功
     */
    void onDownloadSuccess(String tag, String path);

    /**
     * @param progress 下载进度
     */
    void onDownloading(int progress, String tag);

    /**
     * 下载失败
     */
    void onDownloadFailed(String s, String tag);
}

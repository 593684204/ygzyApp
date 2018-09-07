package com.sunsoft_supplier.mvp.updateapk.model;

import android.util.Log;

import com.sunsoft_supplier.data.GetSpInsance;
import com.sunsoft_supplier.mvp.base.BaseModel;
import com.sunsoft_supplier.mvp.updateapk.contract.UpdateApkContract;
import com.sunsoft_supplier.net.network.IDownloadListen;
import com.sunsoft_supplier.net.network.ISecondaryCallBackData;
import com.sunsoft_supplier.net.network.ProgressDownloader;
import com.sunsoft_supplier.net.network.ProgressResponseBody;

import java.io.File;

/**
 * APK的下载
 * Created by MJX on 2017/4/21.
 */
public class UpdateApkModel extends BaseModel implements UpdateApkContract.IUpdateApkModel {

    private long breakPoints;
    private ProgressDownloader downloader;
    private File file;
    private long totalBytes;
    private long length;
    private int pro;

    public UpdateApkModel(ISecondaryCallBackData iSecondaryCallBackData) {
        super(iSecondaryCallBackData);
    }

    @Override
    public void downloadUpdateFile(final String tag, String url, final File savePath, final IDownloadListen listen) {
        breakPoints = (long) GetSpInsance.getSpValue("download", tag + "current", 0L);
        length = (long) GetSpInsance.getSpValue("download", tag + "length", 0L);
        //        breakPoints = 0;
        //        length = 0;
        downloader = new ProgressDownloader(url, savePath, listen, new ProgressResponseBody.ProgressListener() {
            @Override
            public void onPreExecute(long contentLength) {
                // 文件总长只需记录一次，要注意断点续传后的contentLength只是剩余部分的长度
                Log.i("-------", "contentLength=" + contentLength);
                if (length == 0L || length == -1L) {
                    GetSpInsance.saveSp("download", tag + "length", contentLength);
                    length = contentLength;
                    //progressBar.setMax(100);
                }
            }

            @Override
            public void update(long totalBytes, boolean done) {
                // 注意加上断点的长度
                pro = (int) ((totalBytes + breakPoints) * 100 / length);
                Log.i("-------", "pro=" + pro);
                listen.onDownloading(pro, tag);
                GetSpInsance.saveSp("download", tag + "current", totalBytes + breakPoints);
                if (done && length == totalBytes + breakPoints) {
                    // 下载完成，切换到主线程
                    listen.onDownloadSuccess(tag, savePath.getAbsolutePath());
                }
            }
        }, tag);
        downloader.download(breakPoints);
    }


}

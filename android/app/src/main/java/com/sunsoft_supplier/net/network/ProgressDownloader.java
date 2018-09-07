package com.sunsoft_supplier.net.network;


import com.sunsoft_supplier.utils.LogUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by Administrator on 2017/4/10.
 */
public class ProgressDownloader {

    public static final String TAG = "ProgressDownloader";

    private ProgressResponseBody.ProgressListener progressListener;
    private String url;
    private OkHttpClient client;
    private File destination;
    private Call call;
    private IDownloadListen listen;
    private String tag;

    public ProgressDownloader(String url, File destination, IDownloadListen listen, ProgressResponseBody.ProgressListener progressListener, String tag) {
        this.url = url;
        this.destination = destination;
        this.progressListener = progressListener;
        this.listen = listen;
        //在下载、暂停后的继续下载中可复用同一个client对象
        client = getProgressClient();
        this.tag = tag;
    }

    //每次下载需要新建新的Call对象
    private Call newCall(long startPoints) {
        try {
            Request request = new Request.Builder()
                    .url(url)
                    .header("RANGE", "bytes=" + startPoints + "-")//断点续传要用到的，指示下载的区间
                    .build();
            return client.newCall(request);
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.logMsg("--------newCall:" + e.getMessage());
        }
        return null;
    }

    public OkHttpClient getProgressClient() {
        // 拦截器，用上ProgressResponseBody
        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Response originalResponse = chain.proceed(chain.request());
                return originalResponse.newBuilder()
                        .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                        .build();
            }
        };

        return new OkHttpClient.Builder()
                .addNetworkInterceptor(interceptor)
                .build();
    }

    // startsPoint指定开始下载的点
    public void download(final long startsPoint) {

        call = newCall(startsPoint);
        if (call == null) {
            listen.onDownloadFailed("error", tag);
            return;
        }
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                listen.onDownloadFailed(e.getMessage(), tag);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    save(response, startsPoint);
                } else {
                    listen.onDownloadFailed("error", tag);
                }

            }
        });
    }

    public void pause() {
        if (call != null) {
            call.cancel();
        }
    }

    private void save(Response response, long startsPoint) {
        ResponseBody body = response.body();
        InputStream in = body.byteStream();
        FileChannel channelOut = null;
        // 随机访问文件，可以指定断点续传的起始位置
        RandomAccessFile randomAccessFile = null;
        try {

            randomAccessFile = new RandomAccessFile(destination, "rwd");
            //Chanel NIO中的用法，由于RandomAccessFile没有使用缓存策略，直接使用会使得下载速度变慢，亲测缓存下载3.3秒的文件，用普通的RandomAccessFile需要20多秒。
            channelOut = randomAccessFile.getChannel();
            // 内存映射，直接使用RandomAccessFile，是用其seek方法指定下载的起始位置，使用缓存下载，在这里指定下载位置。
            MappedByteBuffer mappedBuffer = channelOut.map(FileChannel.MapMode.READ_WRITE, startsPoint, body.contentLength());
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = in.read(buffer)) != -1) {
                mappedBuffer.put(buffer, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
            LogUtil.logMsg("异常：" + e.toString());
            listen.onDownloadFailed("error", tag);//下载中断
        } finally {
            try {
                in.close();
                if (channelOut != null) {
                    channelOut.close();
                }
                if (randomAccessFile != null) {
                    randomAccessFile.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
                LogUtil.logMsg("关闭异常：" + e.toString());
            }
        }
    }


    public void startTestHttp() {
        call = newCall(0);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LogUtil.logMsg("请求成功");
                //                listen.onDownloadFailed(e.getMessage(),tag);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                LogUtil.logMsg("请求成功");
                if (response.isSuccessful()) {
                    //                    save(response, startsPoint);
                } else {
                    //                    listen.onDownloadFailed("error",tag);
                }

            }
        });
    }

}

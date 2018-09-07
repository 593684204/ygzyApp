package com.sunsoft_supplier.net.network;

import android.annotation.SuppressLint;
import android.os.Handler;

import com.google.gson.Gson;
import com.sunsoft_supplier.bean.UpdateBean;
import com.sunsoft_supplier.constant.Constant;
import com.sunsoft_supplier.data.UserSp;
import com.sunsoft_supplier.mvp.updateapk.UpdateUtils;
import com.sunsoft_supplier.net.checknet.CheckNet;
import com.sunsoft_supplier.utils.LogUtil;
import com.sunsoft_supplier.utils.VersionCodeUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by MJX on 2017/1/8.
 */
public abstract class HttpMethod {
    private static OkHttpClient client;
    private static Request request;
    private static Handler handler = new Handler();
    private static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");//mdiatype 这个需要和服务端保持一致
    /**
     * 判断当前页面是否有缓存，true有缓存，false没有缓存
     */
    private static boolean isHaveCache = false;

    public static boolean isHaveCache() {
        return isHaveCache;
    }

    public static void setIsHaveCache(boolean isHaveCache) {
        HttpMethod.isHaveCache = isHaveCache;
    }
    //    private static Call call;

    /**
     * OkHttp的get请求
     */
    public static void OkHttpGet(String url, final OnDataFinish onDataFinish) {
        request = new Request.Builder().url(url).build();
        HttpMethod.getClientInstance().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                onDataFinish.OnError(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String result = response.body().string();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            onDataFinish.OnSuccess(result);
                        }
                    });
                }
            }
        });
    }

    /**
     * OkHttp的post请求
     */

    public static void OkHttpPost(String url, HashMap<String, String> paramsMap, final OnDataFinish onDataFinish) {

        if ("".equals(Constant.BASE_URL) || Constant.BASE_URL == null) {
            return;
        } else {
            url = Constant.BASE_URL + url;
        }

        LogUtil.logMsg("网络发出请求");
        if (paramsMap != null) {
            paramsMap.put("versionCode", VersionCodeUtil.getVersionName());
            paramsMap.put("versionType", "10");
            paramsMap.put("upPwsNum", UserSp.getUpPwsNum());
        }
        StringBuilder tempParams = new StringBuilder();
        try {
            int pos = 0;
            for (String key : paramsMap.keySet()) {
                if (pos > 0) {
                    tempParams.append("&");
                }
                tempParams.append(String.format("%s=%s", key, URLEncoder.encode(paramsMap.get(key), "utf-8")));
                pos++;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        //生成参数
        String params = tempParams.toString();
        LogUtil.logMsg(url + "?" + params);
        RequestBody body = RequestBody.create(MEDIA_TYPE_JSON, params);
        //创建一个请求实体对象 RequestBody
        request = new Request.Builder().url(url).post(body).build();
        Call call = HttpMethod.getClientInstance().newCall(request);
        HttpCountDown.endCountDownTime();
        HttpCountDown.startCountDownRequestHttp(call, onDataFinish);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        LogUtil.logMsg("HttpMethod的onFailure-----------缓存no" + e.toString());
                        setIsHaveCache(false);
                        if (HttpCountDown.hasCanceledCurrentRequest) {
                            HttpCountDown.hasCanceledCurrentRequest = false;
                            return;
                        } else {
                            HttpCountDown.endCountDownTime();
                        }
                        onDataFinish.OnError(e.getMessage());
                        if (CheckNet.isHaveNetWork()) {
                            if (!"Canceled".equals(e.getMessage())) {
                                //                                ToastUtil.toastDes("网络连接失败");
                                LogUtil.logMsg("网络连接失败-----------请求网络onFinish" + e.getMessage());
                            }

                        }
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                HttpCountDown.endCountDownTime();
                LogUtil.logMsg("Http获取到的code：" + response.code());
                if (response.isSuccessful()) {
                    setIsHaveCache(true);
                    LogUtil.logMsg("HttpMethod的onResponse-----------缓存YES");
                    try {
                        final String result = response.body().string();
                        if (result != null) {
                            JSONObject jsonObject = new JSONObject(result);
                            if (jsonObject.has("msgCode")) {
                                String msgCode = jsonObject.getString("msgCode");
                                if ("99".equals(msgCode)) {
                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                           /* JPushSet.setMesTag("**");
                                            AppManager.getAppManager().exitLogin(AppManager.getAppManager().currentActivity());*/
                                        }
                                    });

                                } else if ("4".equals(msgCode)) { /*强制更新*/
                                    LogUtil.logMsg("强制更新：" + result);
                                    forcedUpdate(result);
                                } else {
                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            onDataFinish.OnSuccess(result);
                                        }
                                    });

                                }

                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        LogUtil.logMsg("HttpMethod的onResponse-----------" + e.toString());
                        onDataFinish.OnError(e.toString());
                    }
                } else {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            onDataFinish.OnError("请求错误");
                        }
                    });
                }
            }
        });
    }

    /**
     * 回调接口
     */
    public interface OnDataFinish {
        void OnSuccess(String result);

        void OnError(String error);
    }


    /**
     * OkHttpClient单例对象实例
     */
    public static OkHttpClient getClientInstance() {
        if (client == null) {
            synchronized (HttpMethod.class) {
                if (client == null) {
                    client = new OkHttpClient.Builder()
                            .connectTimeout(Constant.TIME_OUT, TimeUnit.SECONDS)
                            .sslSocketFactory(createSSLSocketFactory())
                            .hostnameVerifier(new TrustAllHostnameVerifier())
                            .build();
                }
            }
        }
        return client;
    }

    public static void forcedUpdate(final String result) {
        try {
            Gson gson = new Gson();
            UpdateBean updateApkBean = gson.fromJson(result, UpdateBean.class);
            final String contentStr = updateApkBean.getObj().getBody().getContent();
            final String url = updateApkBean.getObj().getBody().getUrl();
            if ("".equals(url)) {
                return;
            }
            handler.post(new Runnable() {
                @Override
                public void run() {
                    UpdateUtils.needUpdate(0, result);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.logMsg("强制更新解析数据出错");
            return;
        }

    }

    public static void cancelAllRequest() {
        if (getClientInstance() != null) {
            getClientInstance().dispatcher().cancelAll();
        }
    }


    /**
     * 默认信任所有的证书
     * TODO 最好加上证书认证，主流App都有自己的证书
     *
     * @return
     */
    @SuppressLint("TrulyRandom")
    private static SSLSocketFactory createSSLSocketFactory() {

        SSLSocketFactory sSLSocketFactory = null;

        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{new TrustAllManager()},
                    new SecureRandom());
            sSLSocketFactory = sc.getSocketFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sSLSocketFactory;
    }

    private static class TrustAllManager implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType)

                throws CertificateException {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            X509Certificate[] x509Certificates = new X509Certificate[0];
            return x509Certificates;
        }
    }

    private static class TrustAllHostnameVerifier implements HostnameVerifier {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }


}

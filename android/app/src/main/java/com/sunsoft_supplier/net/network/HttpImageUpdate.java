package com.sunsoft_supplier.net.network;

import android.os.Handler;

import com.sunsoft_supplier.constant.AppManager;
import com.sunsoft_supplier.constant.Constant;
import com.sunsoft_supplier.data.UserSp;
import com.sunsoft_supplier.utils.LogUtil;
import com.sunsoft_supplier.utils.VersionCodeUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 上传图片
 * Created by Administrator on 2017/4/24.
 */
public class HttpImageUpdate {
    private static Handler handler = new Handler();
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");

    public static void update(String reqUrl, Map<String, String> params, String pic_key, File file, final HttpMethod.OnDataFinish dataFinish) {
        MultipartBody.Builder multipartBodyBuilder = new MultipartBody.Builder();
        multipartBodyBuilder.setType(MultipartBody.FORM);
        //遍历map中所有参数到builder
        if (params != null) {
            for (String key : params.keySet()) {
                multipartBodyBuilder.addFormDataPart(key, params.get(key));
            }
        }
        multipartBodyBuilder.addFormDataPart("versionCode", VersionCodeUtil.getVersionName());
        multipartBodyBuilder.addFormDataPart("versionType", "10");
        multipartBodyBuilder.addFormDataPart("upPwsNum", UserSp.getUpPwsNum());
        //遍历paths中所有图片绝对路径到builder，并约定key如“upload”作为后台接受多张图片的key
        //        if (files != null) {
        //            for (File file : files) {
        multipartBodyBuilder.addFormDataPart(pic_key, file.getName(), RequestBody.create(MEDIA_TYPE_PNG, file));
        //            }
        //        }
        //构建请求体
        RequestBody requestBody = multipartBodyBuilder.build();
        reqUrl = Constant.BASE_URL + reqUrl;
        Request.Builder RequestBuilder = new Request.Builder();
        RequestBuilder.url(reqUrl);// 添加URL地址
        //        RequestBuilder.addHeader("Connection", "close");
        RequestBuilder.post(requestBody);
        final Request request = RequestBuilder.build();
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(Constant.TIME_OUT, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();
        Call call = client.newCall(request);
        HttpHeadCountDown.endCountDownTime();
        HttpHeadCountDown.startCountDownRequestHttp(call, dataFinish);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                LogUtil.logMsg("-----" + e.getMessage());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (HttpHeadCountDown.hasCanceledCurrentRequest) {
                            HttpHeadCountDown.hasCanceledCurrentRequest = false;
                            return;
                        } else {
                            HttpHeadCountDown.endCountDownTime();
                        }
                        dataFinish.OnError(e.getMessage());
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                HttpHeadCountDown.endCountDownTime();
                if (response.isSuccessful()) {
                    final String result = response.body().string();
                    try {
                        if (result != null) {
                            LogUtil.logMsg("上传头像获取的result：" + result);
                            JSONObject jsonObject = new JSONObject(result);
                            if (jsonObject.has("msgCode")) {
                                String msgCode = jsonObject.getString("msgCode");
                                if ("99".equals(msgCode)) {
                                    AppManager.getAppManager().exitLogin(AppManager.getAppManager().currentActivity());
                                } else if ("4".equals(msgCode)) { /*强制更新*/
                                    LogUtil.logMsg("强制更新：" + result);
                                    HttpMethod.forcedUpdate(result);
                                } else {
                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            dataFinish.OnSuccess(result);
                                        }
                                    });
                                }

                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();

                    }
                } else {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            dataFinish.OnError("请求错误");
                        }
                    });
                }
            }
        });

    }


}

package com.sunsoft_supplier.react;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.sunsoft_supplier.constant.Constant;
import com.sunsoft_supplier.data.BundleInfoSp;
import com.sunsoft_supplier.mvp.progress.ProgressUtil;
import com.sunsoft_supplier.mvp.updateapk.UpdateUtils;
import com.sunsoft_supplier.utils.LogUtil;
import com.sunsoft_supplier.utils.PhoneUniqueUtil;
import com.sunsoft_supplier.utils.VersionCodeUtil;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

/**
 * 和ReactNative交互的类
 * Created by MJX on 2016/7/22.
 */
public class RNBridgeModule extends ReactContextBaseJavaModule {
    private static final String MODULE_NAME = "RNBridgeModule";
    private String suppliersId;

    public RNBridgeModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @ReactMethod
    public void testUpdate() {

    }

    /*
    * 给RN传入常量
    * */
    @Nullable
    @Override
    public Map<String, Object> getConstants() {
        Map<String, Object> params = new HashMap<>();
        params.put("versionCode", VersionCodeUtil.getVersionName());
        params.put("versionType", "10");
        params.put("bundleCode", BundleInfoSp.getBundleValue("bundleCode"));
        LogUtil.logMsg("bundle000:" + BundleInfoSp.getBundleValue("bundleCode"));
        return params;
    }

    @ReactMethod
    public void getBundleCode(Callback callback) {
        LogUtil.logMsg("bundle:" + BundleInfoSp.getBundleValue("bundleCode"));
        callback.invoke(BundleInfoSp.getBundleValue("bundleCode"));
    }

    @ReactMethod
    public void getUUID(Callback callback) {
        callback.invoke(PhoneUniqueUtil.getUniqueStr());
    }

    /*在JavaScript端标记的这个模块*/
    @Override
    public String getName() {
        return MODULE_NAME;
    }

    @ReactMethod
    public void update(int code, String json) {
        LogUtil.logMsg("更新：" + code + json);
        if (code == 2) {
            ProgressUtil.getProgressDialog().initUpdateBean(json);
        } else {
            UpdateUtils.needUpdate(code, json);
        }
    }

    @ReactMethod
    public void initProgress(String name) {
        ProgressUtil.initDialog();
    }

    @ReactMethod
    public void splashProgress(String name) {
        /*Message message = new Message();
        message.what = 0;
        message.obj = name;
        UpdateUtils.getUpdateFragmentDialog().getPresenter().getHandler().sendMessage(message);*/
        //ProgressUtil.getProgressDialog().sendMessageDelayed(0,name,20,0);
        ProgressUtil.getProgressDialog().setToast(name);
    }

    @ReactMethod
    public void dismissProgress() {
        //        ProgressUtil.getProgressDialog().sendMessageDelayed(1,"",60,0);
        ProgressUtil.dismiss();
    }

    @ReactMethod
    public void unzipBundle() {

    }

    /*初始化切换到后台多少秒后能够重启APP*/
    @ReactMethod
    public void killAppTime(int time) {
        Constant.KILL_TIME = time;
    }

    @ReactMethod
    public void reloadApp() {
        LogUtil.logMsg("reloadApp：");
        //        MainApplication.getInstance().initBundle();
    }

    public void cancelUpdate() {
        getReactApplicationContext().getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit("cancelUpdate", "cancel");
    }

    /*下载失败*/
    public void downloadFail(String tag) {
        getReactApplicationContext().getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit("downloadFail", tag);
    }

}

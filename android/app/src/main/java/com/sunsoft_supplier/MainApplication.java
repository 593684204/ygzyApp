package com.sunsoft_supplier;

import android.app.Application;
import android.content.Context;
import android.content.IntentFilter;

import com.beefe.picker.PickerViewPackage;
import com.dylanvann.fastimage.FastImageViewPackage;
import com.facebook.react.ReactApplication;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.ReactPackage;
import com.facebook.react.shell.MainReactPackage;
import com.facebook.soloader.SoLoader;
import com.sunsoft_supplier.data.ApkInfoSp;
import com.sunsoft_supplier.data.BundleArchiveSp;
import com.sunsoft_supplier.data.BundleInfoSp;
import com.sunsoft_supplier.net.checknet.NetBroadcast;
import com.sunsoft_supplier.react.MyMainReactPackage;
import com.sunsoft_supplier.utils.LogUtil;
import com.sunsoft_supplier.utils.VersionCodeUtil;
import com.sunsoft_supplier.utils.decompressionbundle.DecompressionInitBundleZipUtil;

import org.devio.rn.splashscreen.SplashScreenReactPackage;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Nullable;

import cn.jpush.reactnativejpush.JPushPackage;

public class MainApplication extends Application implements ReactApplication {
    private static MainApplication instance;
    private static Context context;
    public static long lastTime;
    /*检测是否在启动页按下了home键*/
    public static boolean isClickHome = false;
    private NetBroadcast mNetBroadcast;
    private MyMainReactPackage myMainReactPackage = new MyMainReactPackage();
    //设置为true将不弹出toast
    private boolean SHUTDOWN_TOAST=false;
    //设置为true将不打印log
    private boolean SHUTDOWN_LOG=false;

    public MyMainReactPackage getMyMainReactPackage() {
        return myMainReactPackage;
    }

    private ReactNativeHost mReactNativeHost = new ReactNativeHost(this) {
        @Override
        public boolean getUseDeveloperSupport() {
            return BuildConfig.DEBUG;
        }

        @Override
        protected List<ReactPackage> getPackages() {
            return Arrays.<ReactPackage>asList(
                    new MainReactPackage(),
                    new PickerViewPackage(),
                    new FastImageViewPackage(),
                    myMainReactPackage,
                    new SplashScreenReactPackage(),
                    new JPushPackage(SHUTDOWN_TOAST,SHUTDOWN_LOG)
            );
        }

        @Nullable
        @Override
        protected String getJSBundleFile() {
            String fileStr = BundleInfoSp.getBundleValue("fileStr");
            String bundleName = BundleInfoSp.getBundleValue("bundleName");
            String bundle = fileStr + "/" + bundleName + "/index.android.bundle";
            LogUtil.logMsg(bundle);
            //                return fileStr + "/" + bundleName + "/index.android";
            return bundle;
        }

        @Override
        protected String getJSMainModuleName() {
            String fileStr = BundleInfoSp.getBundleValue("fileStr");
            String bundleName = BundleInfoSp.getBundleValue("bundleName");
            String bundle = fileStr + "/" + bundleName + "/index.android.bundle";
            LogUtil.logMsg(bundle);
            //                return fileStr + "/" + bundleName + "/index.android";
            //return bundle;
            return "index";
        }
    };

    @Override
    public ReactNativeHost getReactNativeHost() {
        return mReactNativeHost;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        context = getApplicationContext();
        LogUtil.logMsg("--------------------2");
        unzipBundle();
        SoLoader.init(this, /* native exopackage */ false);
        //initHost();
        registNetWorkReceiver();
    }

    /**
     * 得到MainApplication实例化对象
     */
    public synchronized static MainApplication getInstance() {
        if (instance == null) {
            instance = new MainApplication();
        }
        return instance;
    }

    public static Context getContext() {
        return context;
    }

    private void unzipBundle() {
        //验证当前的两个apk的版本是否一致，true是需要解压，flase是不需要解压
        boolean isDecompression = (ApkInfoSp.getCurrentApkVersionCode().equals(VersionCodeUtil.getVersionName()));
        if (!BundleArchiveSp.isArchiveBundleZip() || !(isDecompression)) {
            DecompressionInitBundleZipUtil.decompressionInitBundleZip();
            BundleArchiveSp.saveIsArchiveBundleZip(true);
            ApkInfoSp.saveCurrentApkVersionCode(VersionCodeUtil.getVersionName());
        }
    }
    private void registNetWorkReceiver() {
//        isInterceptNet = true;
        mNetBroadcast = new NetBroadcast();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");//要接收的广播
        registerReceiver(mNetBroadcast, intentFilter);//注册接收者
    }
    @Override
    public void onTerminate() {
        // 程序终止的时候执行
        unregisterReceiver(mNetBroadcast);//取消注册
        super.onTerminate();
    }
}

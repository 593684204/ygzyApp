package com.sunsoft_supplier.react;


import android.content.Context;
import android.os.Bundle;

import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactRootView;
import com.facebook.react.common.LifecycleState;
import com.sunsoft_supplier.BuildConfig;
import com.sunsoft_supplier.MainApplication;
import com.sunsoft_supplier.data.BundleInfoSp;

import org.devio.rn.splashscreen.SplashScreenReactPackage;


/**
 * Created by MJX on 2016/8/18.
 */
public class CreateReactViewManager {
    public static ReactInstanceManager mReactInstanceManager;
    public static ReactRootView mRootView;
        public static ReactInstanceManager mReactInstanceManager2;

    /**
     * 创建ReactManager
     */
    public static void creatReactManager() {
        String fileStr = BundleInfoSp.getBundleValue("fileStr");
        String bundleName = BundleInfoSp.getBundleValue("bundleName");
        if (fileStr == "") {
            mReactInstanceManager = ReactInstanceManager.builder()
                    .setApplication(MainApplication.getInstance())
                    .setBundleAssetName("index.android.bundle")
                    //                    .setJSMainModuleName("index.android")
                    .addPackage(new MyMainReactPackage())
                     .addPackage(new SplashScreenReactPackage())
                    .setUseDeveloperSupport(BuildConfig.DEBUG)
                    .setInitialLifecycleState(LifecycleState.RESUMED)
                    .build();
            return;
        }
        mReactInstanceManager = ReactInstanceManager.builder()
                .setApplication(MainApplication.getInstance())
                .setJSBundleFile(fileStr + "/" + bundleName + "/index.android.bundle")
                //                .setJSMainModuleName("index.android")
                .addPackage(new MyMainReactPackage())
                .addPackage(new SplashScreenReactPackage())
                .setUseDeveloperSupport(BuildConfig.DEBUG)
                .setInitialLifecycleState(LifecycleState.RESUMED)
                .build();
    }

    public static void init(Context context) {
        if (mReactInstanceManager == null) {
            creatReactManager();
        }
        if (mRootView != null) {
            mRootView = null;
        }
        mRootView = new ReactRootView(context);
        Bundle bundle = new Bundle();
        mRootView.startReactApplication(mReactInstanceManager2, "sunsoft_supplier", bundle);
    }


    /**
     * 创建ReactManager2
     */
        public static void creatReactManager2(){
            String fileStr = BundleInfoSp.getBundleValue("fileStr");
            String bundleName = BundleInfoSp.getBundleValue("bundleName");

            if(fileStr == ""){
                mReactInstanceManager2 = ReactInstanceManager.builder()
                        .setApplication(MainApplication.getInstance())
                        .setBundleAssetName("index.android.bundle")
//                        .setJSMainModuleName("index.android")
                        .addPackage(new MyMainReactPackage())
//                        .addPackage(new PickerViewPackage())
                        .setUseDeveloperSupport(BuildConfig.DEBUG)
                        .setInitialLifecycleState(LifecycleState.RESUMED)
                        .build();
                return;
            }
            mReactInstanceManager2 = ReactInstanceManager.builder()
                    .setApplication(MainApplication.getInstance())
                    .setJSBundleFile(fileStr+ "/" + bundleName+"/index.android.bundle")
//                    .setJSMainModuleName("index.android")
                    .addPackage(new MyMainReactPackage())
//                    .addPackage(new PickerViewPackage())
                    .setUseDeveloperSupport(BuildConfig.DEBUG)
                    .setInitialLifecycleState(LifecycleState.RESUMED)
                    .build();
        }
}

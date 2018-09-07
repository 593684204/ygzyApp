package com.sunsoft_supplier;

import android.os.Bundle;
import android.os.Handler;
import android.widget.RelativeLayout;

import com.facebook.react.ReactActivity;
import com.facebook.react.ReactRootView;
import com.sunsoft_supplier.constant.AppManager;
import com.sunsoft_supplier.constant.Constant;
import com.sunsoft_supplier.utils.LogUtil;
import com.sunsoft_supplier.utils.RestartAPP;

import org.devio.rn.splashscreen.SplashScreen;

import cn.jpush.android.api.JPushInterface;

public class MainActivity extends ReactActivity {
    public static ReactRootView mRootView;

    /**
     * Returns the name of the main component registered from JavaScript.
     * This is used to schedule rendering of the component.
     */
    @Override
    protected String getMainComponentName() {
        return "sunsoft_supplier";
    }

    private boolean ss = true;
    private MainFragment mainFragment;
    private RelativeLayout parent;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Constant.isShow)
            SplashScreen.show(this);
        super.onCreate(savedInstanceState);
        JPushInterface.init(this);
        AppManager.getAppManager().pushActivity(this);
       /* handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (ss) {
                    UpdateUtils.needUpdate(2, "{\"msgCode\":\"6\",\"obj\":{\"title\":\"bundle有需要更新版本！\",\"body\":{\"pageSize\":0,\"pageNo\":0,\"operatorUid\":null,\"operatorUname\":null,\"operatorRname\":null,\"operatorTime\":null,\"createTime\":\"2018-05-11 12:47:55\",\"reserve1\":null,\"reserve2\":null,\"reserve3\":null,\"referer\":null,\"tranIP\":null,\"auditBaseEntity\":null,\"bundleId\":\"4ba4f6bac035464f81c40ff159ce4ad2\",\"versionCode\":\"1.1.4\",\"bundleCode\":\"1.1\",\"bundleName\":\"20180512\",\"type\":\"10\",\"url\":\"http://ssl.ygzykj.com:6789/supplierbundle/android/1_1_4/bundle/1_1/20180512.zip\",\"serverUrl\":\"https://ssl.ygzykj.com:8480/sunsoft-supplier-app/\",\"deleteFlag\":\"0\",\"isUpdate\":\"1\",\"hashCode\":\"819004A08FCB811BD91B21D81E34FD08\",\"content\":\"66\",\"sha1\":\"09D7AD0DF4084B5A83253F9D727E53850AF328B7\",\"isIncrement\":\"0\"},\"message\":null}}");
                    ss = false;
                }
            }
        }, 2000);*/
        /*setContentView(R.layout.main);
        CreateReactViewManager.creatReactManager();
        CreateReactViewManager.creatReactManager2();
        mRootView = new ReactRootView(this);
        mRootView.startReactApplication(CreateReactViewManager.mReactInstanceManager, "sunsoft_supplier", null);
*/
       /* if (mainFragment == null) {
            mainFragment = new MainFragment();
        }
        MyFragmentManager.addFragmentNoBack(this, R.id.parent, mainFragment);*/
    }

   /* public void initFragment() {
        if (mainFragment != null) {
            MyFragmentManager.removeFragment(this, mainFragment);
            mainFragment = null;
        }
        mainFragment = new MainFragment();
        MyFragmentManager.addFragmentNoBack(this, R.id.parent, mainFragment);
    }*/

    @Override
    protected void onStart() {
        super.onStart();
        LogUtil.logMsg("onStart");
        RestartAPP.stopKillAPPService(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(this);
        LogUtil.logMsg("onPause");
    }
    @Override
    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogUtil.logMsg("onStop");
    }

    @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();
        LogUtil.logMsg("onUserLeaveHint");
        RestartAPP.startKillAPPService(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().popActivity(this);
    }
}

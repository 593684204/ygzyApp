package com.sunsoft_supplier;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactRootView;
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler;
import com.sunsoft_supplier.data.UserSp;
import com.sunsoft_supplier.react.CreateReactViewManager;
import com.sunsoft_supplier.utils.LogUtil;
import com.sunsoft_supplier.utils.UIUtil;
import com.sunsoft_supplier.utils.VersionCodeUtil;

/**
 * Created by Administrator on 2018/5/12.
 */

public class MainFragment extends Fragment implements DefaultHardwareBackBtnHandler {
    private ReactRootView mReactRootView;
    private ReactInstanceManager mReactInstanceManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mBaseView = UIUtil.inflate(R.layout.fragment_main);
        initView(mBaseView);
        initDate();
        LogUtil.logMsg("onCreateView");
        return mBaseView;
    }
    private void initView(View mBaseView) {
        mReactRootView = (ReactRootView) mBaseView.findViewById(R.id.react_native_view);
    }
    public void initDate() {
        Bundle bundle = new Bundle();
        //新添加的数据
        bundle.putString("token", UserSp.getToken());
        bundle.putString("userId", UserSp.getUserId());
        bundle.putString("versionCode", VersionCodeUtil.getVersionName());
        bundle.putString("upPwsNum", UserSp.getUpPwsNum());
//        bundle.putString("serverUrl", ApiUrl.BASE_URL);
        Bundle bundleInit = new Bundle();
        bundleInit.putBundle("initData", bundle);
        bundleInit.putString("initRoute", "IndexPage");

        mReactInstanceManager = CreateReactViewManager.mReactInstanceManager;
        mReactRootView.startReactApplication(mReactInstanceManager, "sunsoft_supplier", bundleInit);

    }

    @Override
    public void onResume() {
        super.onResume();
        if (mReactInstanceManager != null) {
            mReactInstanceManager.onHostResume(getActivity(), this);
        }
        LogUtil.logMsg("onCreateView");
       /* if (!"".equals(getCurrentViewName())) {
            MobclickAgent.onResume(getActivity());
            MobclickAgent.onPageStart(getCurrentViewName());
        }*/
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mReactInstanceManager.onHostDestroy(getActivity());
    }

    @Override
    public void invokeDefaultOnBackPressed() {

    }
}

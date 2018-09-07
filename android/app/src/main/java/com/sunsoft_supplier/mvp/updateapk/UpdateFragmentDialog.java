package com.sunsoft_supplier.mvp.updateapk;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.sunsoft_supplier.MainApplication;
import com.sunsoft_supplier.R;
import com.sunsoft_supplier.bean.UpdateBean;
import com.sunsoft_supplier.listener.IUpdateListener;
import com.sunsoft_supplier.mvp.base.BaseFragmentDialog;
import com.sunsoft_supplier.mvp.updateapk.presenter.UpdateApkPresenter;
import com.sunsoft_supplier.net.checknet.CheckNet;
import com.sunsoft_supplier.net.checknet.NetEvent;
import com.sunsoft_supplier.utils.LogUtil;
import com.sunsoft_supplier.utils.UIUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;


/**
 * 弹框
 * Created by MJX on 2017/4/19.
 */
public class UpdateFragmentDialog extends BaseFragmentDialog implements IWifiOrMobileListener {

    private String fragmentTag;
    private String contentStr;
    private String url;
    private UpdateBean updateBean;

    public String getFragmentTag() {
        return fragmentTag;
    }

    public void setFragmentTag(String fragmentTag) {
        this.fragmentTag = fragmentTag;
    }

    public String getContentStr() {
        return contentStr;
    }

    public void setContentStr(String contentStr) {
        this.contentStr = contentStr;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public UpdateBean getUpdateBean() {
        return updateBean;
    }

    private IUpdateListener updateListener;
    private WifiOrMobileDialog wifiOrMobileDialog;
    private RelativeLayout rl_bg;
    private RelativeLayout rl_parent;
    private TextView btn_confirm;
    private TextView tv_content;
    private RelativeLayout updateProgressRl;
    private TextView progressTv, progressTvToast;
    private ProgressBar progressPb;
    private UpdateApkPresenter updateApkPresenter;
    private ImageView img_close;
    private ImageView iv_update_bg;
    private INormalUpdateListener normalUpdateListener;

    public TextView getProgressTv() {
        return progressTv;
    }

    public TextView getProgressTvToast() {
        return progressTvToast;
    }

    public ProgressBar getProgressPb() {
        return progressPb;
    }

    public UpdateApkPresenter getPresenter() {
        return updateApkPresenter;
    }

    private TextView updateTitleTv;
    private Gson gson = new Gson();

    @Override
    public View subCreateView() {
        return UIUtil.inflate(R.layout.dialog_update);
    }


    @Override
    public void subInitView() {
        EventBus.getDefault().register(this);
        setCancelable(false);
        rl_bg = (RelativeLayout) mView.findViewById(R.id.update_rl_bg);
        rl_parent = (RelativeLayout) mView.findViewById(R.id.update_parent);
        img_close = (ImageView) mView.findViewById(R.id.update_img_close);
        btn_confirm = (TextView) mView.findViewById(R.id.update_btn_confirm);
        tv_content = (TextView) mView.findViewById(R.id.update_tv_content);
        iv_update_bg = (ImageView) mView.findViewById(R.id.update_bg_iv);
        updateTitleTv = (TextView) mView.findViewById(R.id.update_title);

        updateProgressRl = (RelativeLayout) mView.findViewById(R.id.update_progress);
        progressTv = (TextView) mView.findViewById(R.id.progress_tv);
        progressTvToast = (TextView) mView.findViewById(R.id.progress_tv_toast);
        progressPb = (ProgressBar) mView.findViewById(R.id.progressbar_pb);

        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeFragmentDialog();
                //                updateListener.closeDialog();
                MainApplication.getInstance().getMyMainReactPackage().mModule.cancelUpdate();
            }
        });

        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateApkPresenter.clickDown = true;
                if (CheckNet.isHaveNetWork()) {
                    checkCurrentNet();
                }
            }
        });

    }

    @Override
    public void subInitData() {
        updateApkPresenter = new UpdateApkPresenter(this);
        String updateJson = getArguments().getString("updateBean", "");
        updateBean = gson.fromJson(updateJson, UpdateBean.class);
        fragmentTag = getArguments().getString("tag", "0");
        contentStr = updateBean.getObj().getBody().getContent();
        url = updateBean.getObj().getBody().getUrl();
        if ("0".equals(fragmentTag)) { /*强制更新*/
            img_close.setVisibility(View.GONE);
            iv_update_bg.setBackgroundResource(R.drawable.forced_update);
        } else if ("1".equals(fragmentTag)) { /*正常更新*/
            img_close.setVisibility(View.VISIBLE);
            iv_update_bg.setBackgroundResource(R.drawable.common_update);
        } else if ("2".equals(fragmentTag)) {
            String isincrement = updateBean.getObj().getBody().getIsIncrement();
            if ("0".equals(isincrement)) { //bundle全量更新
                updateBundle(updateApkPresenter.bundleTag);
            } else if ("1".equals(isincrement)) {  /*增量更新*/
                updateBundle(updateApkPresenter.bundlezipTag);
            }
        }
        tv_content.setText(contentStr);
    }

    @Subscribe
    public void onNetChange(NetEvent netEvent) {
        if (netEvent == null) {
            return;
        }
        if (netEvent.getNetFlag() && !updateApkPresenter.isDownSuccess) {
            updateApk();
        }

    }

    /**
     * 检测当前是wifi还是流量
     */
    private void checkCurrentNet() {
        int netType = CheckNet.checkWifiOrMobile();
        LogUtil.logMsg("当前网络的状况是：" + netType);
        switch (netType) {
            case 0:
                break;
            case 1: /*wifi*/
                updateApk();
                break;
            case 2:/*手机流量*/
                createWifiOrMobileDialog();
                break;
        }
    }

    private void createWifiOrMobileDialog() {
        if (wifiOrMobileDialog == null) {
            wifiOrMobileDialog = new WifiOrMobileDialog();
        }
        wifiOrMobileDialog.setWifiOrMobileListener(this);
        Bundle bundle = new Bundle();
        bundle.putString("tag", fragmentTag);
        wifiOrMobileDialog.setArguments(bundle);
        wifiOrMobileDialog.showFragmentDialog();
    }


    private void updateApk() {
        if ("0".equals(fragmentTag)) {
            goOnForcedDownLoad();
        } else if ("1".equals(fragmentTag)) {
            goOnNormalDownLoad();
        }
    }

    private void updateBundle(String tag) {
        rl_bg.setBackgroundResource(0);
        img_close.setVisibility(View.GONE);
        btn_confirm.setVisibility(View.GONE);
        tv_content.setVisibility(View.GONE);
        updateTitleTv.setVisibility(View.GONE);
        //        updateProgressRl.setVisibility(View.VISIBLE);
        updateApkPresenter.download(tag, url);
    }

    /**
     * 正常更新，流量的情况下，点击取消
     */
    @Override
    public void cancelNormalDownLoad() {
        closeFragmentDialog();
        normalUpdateListener.normalCancelUpdateApk();
    }

    @Override
    public void goOnNormalDownLoad() {
        //        closeFragmentDialog();
        //        normalUpdateListener.normalUpdateApk();
        img_close.setVisibility(View.GONE);
        btn_confirm.setVisibility(View.GONE);
        tv_content.setVisibility(View.GONE);
        updateTitleTv.setVisibility(View.GONE);
        updateProgressRl.setVisibility(View.VISIBLE);
        updateApkPresenter.download(updateApkPresenter.apkTag, url);
    }

    @Override
    public void goOnForcedDownLoad() {
        btn_confirm.setVisibility(View.GONE);
        tv_content.setVisibility(View.GONE);
        updateTitleTv.setVisibility(View.GONE);
        updateProgressRl.setVisibility(View.VISIBLE);
        updateApkPresenter.download(updateApkPresenter.apkTag, url);
    }

    public void setUpdateListener(IUpdateListener updateListener) {
        this.updateListener = updateListener;
    }

    public void setNormalUpdateListener(INormalUpdateListener normalUpdateListener) {
        this.normalUpdateListener = normalUpdateListener;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}

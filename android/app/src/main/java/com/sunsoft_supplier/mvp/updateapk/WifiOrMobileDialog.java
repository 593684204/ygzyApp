package com.sunsoft_supplier.mvp.updateapk;

import android.view.View;
import android.widget.TextView;

import com.sunsoft_supplier.R;
import com.sunsoft_supplier.mvp.base.BaseFragmentDialog;
import com.sunsoft_supplier.constant.AppManager;
import com.sunsoft_supplier.utils.UIUtil;


/**
 * Wifi或者流量监控的弹框
 * Created by MJX on 2017/4/20.
 */
public class WifiOrMobileDialog extends BaseFragmentDialog {

    private TextView dialogTitle;
    private TextView dialogContentTv;
    private TextView dialogCancelTv;
    private TextView dialogConfirmTv;
    private IWifiOrMobileListener wifiOrMobileListener;

    /**
     * 更新的标识，默认是-1，不做处理
     */
    private String updateTag = "-1";
    @Override
    public View subCreateView() {
        return UIUtil.inflate(R.layout.dialog_wifi_mobile);
    }

    @Override
    public void subInitView() {
        dialogTitle = (TextView)mView.findViewById(R.id.dialog_title);
        dialogContentTv = (TextView)mView.findViewById(R.id.dialog_content_tv);
        dialogCancelTv = (TextView)mView.findViewById(R.id.dialog_cancel_tv);
        dialogConfirmTv = (TextView)mView.findViewById(R.id.dialog_confirm_tv);
        dialogCancelTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if("0".equals(updateTag)){ /*强制更新*/
                    AppManager.getAppManager().AppExit();
                }else if("1".equals(updateTag)){ /*不是强制更新*/
                    closeFragmentDialog();
                    wifiOrMobileListener.cancelNormalDownLoad();
                }
            }
        });

        dialogConfirmTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeFragmentDialog();
                if("0".equals(updateTag)){
                    wifiOrMobileListener.goOnForcedDownLoad();
                }else if("1".equals(updateTag)){
                    wifiOrMobileListener.goOnNormalDownLoad();
                }

            }
        });
    }

    @Override
    public void subInitData() {
        updateTag = getArguments().getString("tag","0");
        dialogTitle.setText("网络连接提醒");
        dialogContentTv.setText("当前网络无Wi-Fi，继续下载可能会被运营商收取流量费用");
        dialogCancelTv.setText("取消");
        dialogConfirmTv.setText("继续下载");
        setCancelable(false);
    }


    public void setWifiOrMobileListener(IWifiOrMobileListener wifiOrMobileListener){
        this.wifiOrMobileListener = wifiOrMobileListener;
    }
}

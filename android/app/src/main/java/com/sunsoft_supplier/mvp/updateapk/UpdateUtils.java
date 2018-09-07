package com.sunsoft_supplier.mvp.updateapk;

import android.os.Bundle;

import com.sunsoft_supplier.utils.LogUtil;


/**
 * 更新
 * Created by MJX on 2017/4/20.
 */
public class UpdateUtils {

    private static UpdateFragmentDialog updateFragmentDialog;

    public static UpdateFragmentDialog getUpdateFragmentDialog() {
        return updateFragmentDialog;
    }

    /**
     * 需要更新
     */
    public static void needUpdate(int tag, String updateBean) {
        try {
            if (updateFragmentDialog == null) {
                updateFragmentDialog = new UpdateFragmentDialog();

            }
            Bundle bundle = new Bundle();
            bundle.putString("tag", tag+"");
            bundle.putString("updateBean", updateBean);
            updateFragmentDialog.setArguments(bundle);
            updateFragmentDialog.showFragmentDialog();
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.logMsg("needModule中更新弹框出问题了" + e.toString());
            return;
        }

    }
    public static void dismiss(){
        try {
            if (updateFragmentDialog != null) {
                updateFragmentDialog.dismiss();
            }
        }catch (Exception e){
            LogUtil.logMsg("更新弹框出问题了" + e.toString());
        }

    }


}

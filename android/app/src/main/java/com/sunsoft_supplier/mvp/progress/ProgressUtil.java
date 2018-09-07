package com.sunsoft_supplier.mvp.progress;

/**
 * Created by Administrator on 2018/5/14.
 */

public class ProgressUtil {
    private static ProgressFragmentDialog progressDialog;

    public static ProgressFragmentDialog getProgressDialog() {
        return progressDialog;
    }

    public static void initDialog() {
        try {
            if (progressDialog == null) {
                progressDialog = new ProgressFragmentDialog();
            }
            if (!isShow())
                progressDialog.showFragmentDialog();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isShow() {
        if (progressDialog != null) {
            return progressDialog.isAdded();
        }
        return false;
    }

    public static void dismiss() {
        try {
            if (progressDialog != null) {
                progressDialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

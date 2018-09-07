package com.sunsoft_supplier.mvp.base;

import android.app.Activity;
import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.sunsoft_supplier.constant.AppManager;


/**
 * 弹框
 * Created by MJX on 2017/4/20.
 */
public abstract class BaseFragmentDialog extends DialogFragment {

    protected View mView;
    private Activity mActivity;

    public Activity getmActivity() {
        return mActivity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        initWindow();

        mView = subCreateView();
        subInitView();
        subInitData();
        return mView;
    }

    public void showFragmentDialog() {
        mActivity = AppManager.getAppManager().currentActivity();
        if (mActivity == null) {
            //// TODO: 2018/4/25
            //            mActivity = SupplierTabActivity.getSupplierTabActivity();
            if (mActivity == null) {
                return;
            }
        }

        //        if(!isAdded()){
        //            FragmentTransaction ft = mActivity.getFragmentManager().beginTransaction();
        //            ft.add(this,null);
        //            ft.commit();
        //        }else{
        //            show(mActivity.getFragmentManager(), "");
        //        }
        if (isVisible()) {
            dismiss();
        }
        show(mActivity.getFragmentManager(), "");

    }

    @Override
    public void onStart() {
        super.onStart();

    }

    private void initWindow() {

        Window window = getDialog().getWindow();
        window.requestFeature(Window.FEATURE_NO_TITLE);
//        window.setType(WindowManager.LayoutParams.TYPE_TOAST);
        //设置背景透明
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        setCancelable(false);
    }


    public void closeFragmentDialog() {
        dismiss();
    }

    /**
     * 创建布局
     *
     * @return
     */
    public abstract View subCreateView();

    /**
     * 初始化控件
     *
     * @return
     */
    public abstract void subInitView();

    /**
     * 初始化数据
     */
    public abstract void subInitData();


}

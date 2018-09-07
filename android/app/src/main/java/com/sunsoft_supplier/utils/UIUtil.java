package com.sunsoft_supplier.utils;

import android.content.Context;
import android.view.View;
import android.widget.EditText;

import com.sunsoft_supplier.MainApplication;

/**
 * 界面上的工具类
 * Created by MJX on 2017/1/11.
 */
public class UIUtil {
    /**
     * 获取Context
     * @return
     */
    public static Context getContext(){
        return MainApplication.getContext();
    }

    /**
     * 根据id获取String
     * @param id 字符串id
     * @return
     */
    public static String getString(int id){
        return getContext().getResources().getString(id);
    }


    /**
     * dp和px的转换关系
     * @param dip
     * @return
     */
    public static int dip2px(int dip){
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int)(dip*density+0.5);
    }

    /**
     * px和dp的装换关系
     * @param px
     * @return
     */
    public static int px2dip(int px){
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int)(px/density+0.5);
    }

    /**
     * 布局
     * @param id
     * @return
     */
    public static View inflate(int id){
        return View.inflate(getContext(), id, null);
    }

    /**
     * 得到EditText的数据
     * @param editText
     * @return
     */
    public static String getEditStr(EditText editText){
        return editText.getText().toString();
    }


}

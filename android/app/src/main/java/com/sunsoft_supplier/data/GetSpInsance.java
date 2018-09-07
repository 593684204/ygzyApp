package com.sunsoft_supplier.data;

import android.content.SharedPreferences;

import com.sunsoft_supplier.utils.UIUtil;


/**
 * Created by MJX on 2017/1/13.
 */
public class GetSpInsance {
    /**
     * 得到sp对象
     *
     * @param spName sp的名字
     * @return
     */
    public static SharedPreferences getSp(String spName) {
        SharedPreferences sp = UIUtil.getContext().getSharedPreferences(spName, UIUtil.getContext().MODE_PRIVATE);
        return sp;
    }

    /**
     * 得到Editor对象
     *
     * @param spName sp的名字
     * @return
     */
    public static SharedPreferences.Editor getEdit(String spName) {
        SharedPreferences sp = UIUtil.getContext().getSharedPreferences(spName, UIUtil.getContext().MODE_PRIVATE);
        return sp.edit();
    }

    /**
     * 保存sp的信息
     *
     * @param spName
     * @param key
     * @param value
     * @param <T>
     */
    public static <T> void saveSp(String spName, String key, T value) {
        final SharedPreferences sp = getSp(spName);
        if (value != null) {
            if (value instanceof String) {
                sp.edit().putString(key, (String) value).commit();
            } else if (value instanceof Boolean) {
                sp.edit().putBoolean(key, (Boolean) value).commit();
            } else if (value instanceof Integer) {
                sp.edit().putInt(key, (Integer) value).commit();
            } else if (value instanceof Long) {
                sp.edit().putLong(key, (Long) value).commit();
            }
        }
    }

    /**
     * 得到保存的数据类型
     *
     * @param spName
     * @param key
     * @param defauleValue
     * @param <T>
     * @return
     */
    public static <T> Object getSpValue(String spName, String key, T defauleValue) {
        final SharedPreferences sp = getSp(spName);
        if (defauleValue instanceof String) {
            return sp.getString(key, (String) defauleValue);
        } else if (defauleValue instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) defauleValue);
        } else if (defauleValue instanceof Integer) {
            return sp.getInt(key, (Integer) defauleValue);
        } else if (defauleValue instanceof Long) {
            return sp.getLong(key, (Long) defauleValue);
        }
        return null;
    }
}

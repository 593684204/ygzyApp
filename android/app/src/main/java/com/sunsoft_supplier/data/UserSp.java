package com.sunsoft_supplier.data;

import android.content.SharedPreferences;



import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 用户信息
 * Created by MJX on 2017/1/11.
 */
public class UserSp {

    /**
     * 存储用户信息
     *
     * @param map
     */
    public static void saveUserInfo(Map<String, String> map) {
        SharedPreferences.Editor userInfoEdit = getUserInfoEdit();
        Set<Map.Entry<String, String>> entries = map.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            userInfoEdit.putString(entry.getKey(), entry.getValue());
        }
        userInfoEdit.commit();
    }

    /**
     * 得到存储用户信息的Editor
     *
     * @return
     */
    public static SharedPreferences.Editor getUserInfoEdit() {
        return GetSpInsance.getEdit("userInfo");
    }

    /**
     * 登录，通过调用这个方法获取保存用户信息的key值的一致性
     *
     * @param type
     */
    public static String getSaveUserInfoMapKey(String type) {
        String key = "";
        HashMap<String, String> hashMap = null;
        if (hashMap == null) {
            hashMap = new HashMap<>();
            hashMap.clear();
            hashMap.put("userName", "");
            hashMap.put("passWord", "");
            hashMap.put("token", "");
            hashMap.put("userRealName", "");
            hashMap.put("headPortrait", "");
            hashMap.put("userType", "");
            hashMap.put("upPwsNum", ""); /*修改密码次数*/
            hashMap.put("sex", "");
            hashMap.put("birthday", "");
            hashMap.put("mobilePhone", "");
            hashMap.put("userId", "");
            hashMap.put("supplierName", ""); /*厂商名称*/
            hashMap.put("upPwsNum", ""); /*修改密码的次数*/
        }
        if (hashMap.containsKey(type)) {
            key = type;
        }
        return key;
    }

    /**
     * 清除用户信息
     */
    public static void clearUserInfo() {
        GetSpInsance.getEdit("userInfo").clear();
    }

    /**
     * 获取token
     *
     * @return
     */
    public static String getToken() {
        return (String) GetSpInsance.getSpValue("userInfo", "token", "");
    }

    /**
     * 获取用户id
     *
     * @return
     */
    public static String getUserId() {
        return (String) GetSpInsance.getSpValue("userInfo", "userId", "");
    }

    /**
     * 获取用户头像
     *
     * @return
     */
    public static String getHeadPortrait() {
        return (String) GetSpInsance.getSpValue("userInfo", "headPortrait", "");
    }

    /**
     * 保存用户头像
     *
     * @return
     */
    public static void saveHeadPortrait(String head) {
        GetSpInsance.saveSp("userInfo", "headPortrait", head);
    }

    /**
     * 获取用户名
     *
     * @return
     */
    public static String getUserName() {
        return (String) GetSpInsance.getSpValue("userInfo", "userName", "");
    }

    /**
     * 获取厂商名称
     *
     * @return
     */
    public static String getSupplierName() {
        return (String) GetSpInsance.getSpValue("userInfo", "supplierName", "");
    }

    /**
     * 获取绑定的手机号
     *
     * @return
     */
    public static String getMobilePhone() {
        return (String) GetSpInsance.getSpValue("userInfo", "mobilePhone", "");
    }

    /**
     * 获取绑定的手机号
     *
     * @return
     */
    public static String getRealName() {
        return (String) GetSpInsance.getSpValue("userInfo", "userRealName", "");
    }

    /**
     * 修改密码的次数
     *
     * @return
     */
    public static String getUpPwsNum() {
        return (String) GetSpInsance.getSpValue("userInfo", "upPwsNum", "");
    }


    /**
     * 修改用户的某项信息用户的信息
     * @param userKey
     * @param userValue
     */
    public static void changeUserInfo(String userKey,String userValue){
        GetSpInsance.saveSp("userInfo",userKey,userValue);
    }
}

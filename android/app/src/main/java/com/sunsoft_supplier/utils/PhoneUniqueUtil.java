package com.sunsoft_supplier.utils;


import com.sunsoft_supplier.data.PhoneDataSp;

import java.util.UUID;

/**
 * 获取随机字符串
 * Created by MJX on 2017/2/9.
 */
public class PhoneUniqueUtil {
    /**
     * 得到随机的字符串
     * @return
     */
    public static String getUniqueStr(){
        String uniqueStr = PhoneDataSp.getPhoneData();
        if("".equals(uniqueStr)){
            uniqueStr = UUID.randomUUID().toString().trim().replaceAll("-", "");
            PhoneDataSp.savePhoneData(uniqueStr);
        }
        LogUtil.logMsg("unique是"+uniqueStr);
        return uniqueStr;
    }
}
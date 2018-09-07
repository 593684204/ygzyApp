package com.sunsoft_supplier.data;

/**
 * bundle存储的位置信息
 * Created by MJX on 2017/3/30.
 */
public class BundleInfoSp {
    /**
     * 保存bundle的信息
     * @param fileStr
     * @param bundleCode
     * @param bundleName
     * @param initBundleFile
     */
    public static void saveBundleInfo(String fileStr,String bundleCode,String bundleName,String initBundleFile){
        GetSpInsance.saveSp("react","fileStr",fileStr);
        GetSpInsance.saveSp("react","bundleCode",bundleCode);
        GetSpInsance.saveSp("react","bundleName",bundleName);
        GetSpInsance.saveSp("react","initBundleFile",initBundleFile);
    }

    /**
     * 全量更新时，保存bundle的信息
     * @param fileStr
     * @param bundleCode
     * @param bundleName
     */
    public static void saveFullUpdateBundleInfo(String fileStr,String bundleCode,String bundleName){
        GetSpInsance.saveSp("react","fileStr",fileStr);
        GetSpInsance.saveSp("react","bundleCode",bundleCode);
//        GetSpInsance.saveSp("react","bundleName",bundleName);
    }


    /**
     * bundle存储的信息，根据key获取value
     * @param key
     * @return
     */
    public static String getBundleValue(String key){
      return   (String) GetSpInsance.getSpValue("react",key,"");
    }

    /**
     * 清除bundle的信息
     */
    public static void clearBundleInfo(){
        GetSpInsance.getEdit("react").clear();
    }
}

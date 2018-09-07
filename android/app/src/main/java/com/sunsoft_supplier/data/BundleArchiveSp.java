package com.sunsoft_supplier.data;

import com.sunsoft_supplier.data.GetSpInsance;

/**
 * Bundle的压缩包是否解压了
 * Created by MJX on 2017/4/10.
 */
public class BundleArchiveSp {
    /**
     * 保存Bundle的压缩包是否解压了
     *
     * @param isArchiveBundleZip
     */
    public static void saveIsArchiveBundleZip(boolean isArchiveBundleZip) {
        GetSpInsance.saveSp("BundleArchive", "isArchiveBundleZip", isArchiveBundleZip);
    }

    /**
     * 判断Bundle的压缩包是否解压了
     *
     * @return
     */
    public static boolean isArchiveBundleZip() {
        return (Boolean) GetSpInsance.getSpValue("BundleArchive", "isArchiveBundleZip", false);
    }

    /**
     * 清除用户登录
     */
    public static void clearIsArchiveBundleZip() {
        saveIsArchiveBundleZip(false);
    }
}

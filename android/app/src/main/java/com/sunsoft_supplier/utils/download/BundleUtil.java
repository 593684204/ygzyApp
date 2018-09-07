package com.sunsoft_supplier.utils.download;

import com.sunsoft_supplier.utils.decompressionbundle.CreateFileUtil;

import java.io.File;

/**
 * Bundle更新
 * Created by MJX on 2017/4/13.
 */
public class BundleUtil {
    /**
     * 下载Bundle的zip包时，下载的路径
     *
     * @param bundleName
     * @return
     */
    public static File getLoadBundZipFile(String bundleName) {
        //        String bundlezip =  HomeActivity.this.getFilesDir() +  "/" + "bundlezip"+"/" +  bundleName + ".zip";
        CreateFileUtil.creatSingleFile("bundlezip");
        //         CreateFileUtil.creatSecondFile("bundlezip",bundleName+".zip");
        File bundlezip = CreateFileUtil.getFile("bundlezip", bundleName + ".zip");
        return bundlezip;
    }

    public static void deleteBundZipFile(String bundleName) {
        //        String bundlezip =  HomeActivity.this.getFilesDir() +  "/" + "bundlezip"+"/" +  bundleName + ".zip";
        CreateFileUtil.creatSingleFile("bundlezip");
        //         CreateFileUtil.creatSecondFile("bundlezip",bundleName+".zip");
        File bundlezip = CreateFileUtil.getFile("bundlezip", bundleName + ".zip");
        if (bundlezip.exists()) {
            bundlezip.delete();
        }
    }
}

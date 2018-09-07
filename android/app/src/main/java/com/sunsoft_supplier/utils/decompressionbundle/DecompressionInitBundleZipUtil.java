package com.sunsoft_supplier.utils.decompressionbundle;

import com.sunsoft_supplier.constant.Constant;
import com.sunsoft_supplier.data.BundleInfoSp;
import com.sunsoft_supplier.utils.UIUtil;
import com.sunsoft_supplier.utils.updatebundle.DelFileUtil;

import java.io.File;

/**
 * 解压assets文件夹下的初始bundle的zip包
 * Created by MJX on 2017/4/12.
 */
public class DecompressionInitBundleZipUtil {
    /**
     * 解压bundle的zip包
     */
    public static void decompressionInitBundleZip() {
            CreateFileUtil.creatSingleFile("bundle");
            CreateFileUtil.creatSingleFile("bundlezip");
            CreateFileUtil.creatSingleFile("usebundle");
            clearBundleAndBundleZipFile();
            String bundlezip = CreateFileUtil.getFilePath("bundlezip", Constant.BUNDLE_NAME + ".zip");
            boolean flag = CopyDataToSdUtil.copyDataToSd(bundlezip, Constant.BUNDLE_NAME + ".zip");
            if (!flag) {
                return;
            }
            clearUseBundleFile();
            String usebundleStr = CreateFileUtil.getFilePath("usebundle", "");
            File file = new File(bundlezip);
            DecompressionUtil.unZipFile(file, usebundleStr);
//            String str =  CreateFileUtil.getFilePath("usebundle","");
            String str = UIUtil.getContext().getFilesDir() + "/" + "usebundle";
            //保存初始化的bundle信息
            BundleInfoSp.saveBundleInfo(str, "1.0", Constant.BUNDLE_NAME, Constant.BUNDLE_NAME);
    }

    /**
     * 清空Bundle文件夹和BundleZip文件夹
     */
    private static void clearBundleAndBundleZipFile(){
        String clearBundleZipPath = CreateFileUtil.getFilePath("bundlezip","");
        String clearBundlePath = CreateFileUtil.getFilePath("bundle","");
        DelFileUtil.delAllFile(clearBundleZipPath);
        DelFileUtil.delAllFile(clearBundlePath);
    }

    /**
     * 清空UseBundle文件夹
     */
    private static void clearUseBundleFile(){
        String clearUseBundlePath = CreateFileUtil.getFilePath("usebundle","");
        DelFileUtil.delAllFile(clearUseBundlePath);
    }
}

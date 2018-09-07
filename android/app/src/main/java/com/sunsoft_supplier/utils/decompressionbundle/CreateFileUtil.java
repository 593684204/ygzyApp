package com.sunsoft_supplier.utils.decompressionbundle;

import com.sunsoft_supplier.utils.UIUtil;

import java.io.File;

/**
 * 创建File文件
 * Created by MJX on 2017/3/30.
 */
public class CreateFileUtil {
    public static void creatSingleFile(String fileName){
        File cacheDir = UIUtil.getContext().getFilesDir();
        String tempFileStr = cacheDir + "/" + fileName;
        File file = new File(tempFileStr);
        if(!file.exists()){
            File dir = new File(tempFileStr);
            dir.mkdir();
        }
    }

    public static void creatSecondFile(String firstDirName,String secondaryDirName){
        File cacheDir = UIUtil.getContext().getFilesDir();
        String tempFileStr = cacheDir + "/" + firstDirName + "/" + secondaryDirName;
        File file = new File(tempFileStr);
        if(!file.exists()){
            File dir = new File(tempFileStr);
            dir.mkdir();
        }
    }

    /**
     * 根据名字寻找路径
     * @param firstDirName
     * @param secondaryDirName
     * @return
     */
    public static String getFilePath(String firstDirName,String secondaryDirName){
        File cacheDir = UIUtil.getContext().getFilesDir();
        return  cacheDir + "/" + firstDirName + "/" + secondaryDirName;
    }

    /**
     * 返回File
     * @param firstDirName
     * @param secondaryDirName
     * @return
     */
    public static File getFile(String firstDirName,String secondaryDirName){
        String filePath = getFilePath(firstDirName, secondaryDirName);
        File file = new File(filePath);
        return  file;
    }

}

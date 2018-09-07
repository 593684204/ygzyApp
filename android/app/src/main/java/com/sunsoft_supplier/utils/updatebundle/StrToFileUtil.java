package com.sunsoft_supplier.utils.updatebundle;

import java.io.File;
import java.io.FileOutputStream;

/**
 * 字符串转成file文件
 * Created by MJX on 2017/4/12.
 */
public class StrToFileUtil {
    /**
     * 把字符串转化到对应的文件中
     * @param toSaveString
     * @param saveFile
     */
    public static void saveStrToFile(String toSaveString,File saveFile){
        FileOutputStream outStream = null;
        try {
            outStream = new FileOutputStream(saveFile);
            outStream.write(toSaveString.getBytes());
            outStream.flush();
            outStream.close();
        } catch (Exception e) {
//            isException = true;
            e.printStackTrace();
        }

    }
}

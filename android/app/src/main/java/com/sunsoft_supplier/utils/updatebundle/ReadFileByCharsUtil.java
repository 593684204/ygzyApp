package com.sunsoft_supplier.utils.updatebundle;

import org.apache.commons.io.IOUtils;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2017/4/12.
 */
public class ReadFileByCharsUtil {
    /**
     * 以字符为单位读取文件，常用于读文本，数字等类型的文件
     */
    public static String readFileByChars(String fileName) {
        //File file = new File(fileName);
        FileInputStream fis = null;
        String result="";
        try {
            // 一次读多个字符
            fis = new FileInputStream(fileName);
            //修改io的读取
            result = IOUtils.toString(fis, "utf-8");
        } catch (Exception e1) {
            e1.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e1) {
//                    isException = true;
                }
            }
        }
        return result;
    }
}

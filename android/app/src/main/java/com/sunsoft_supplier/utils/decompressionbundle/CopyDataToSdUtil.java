package com.sunsoft_supplier.utils.decompressionbundle;

import com.sunsoft_supplier.utils.UIUtil;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 复制数据到sd卡
 * Created by MJX on 2017/3/30.
 */
public class CopyDataToSdUtil {
    /**
     * 复制数据
     * @param strOutFileName
     * @param openFileName
     * @return 是否复制成功，true是复制成功，false是复制失败
     */
    public static boolean copyDataToSd( String strOutFileName,String openFileName){
        boolean flag = true;
        InputStream myInput;
        OutputStream myOutput = null;
        try {
            myOutput = new FileOutputStream(strOutFileName);
            myInput = UIUtil.getContext().getAssets().open(openFileName);
            byte[] buffer = new byte[1024];
            int length = myInput.read(buffer);
            while(length > 0)
            {
                myOutput.write(buffer, 0, length);
                length = myInput.read(buffer);
            }

            myOutput.flush();
            myInput.close();
            myOutput.close();
        } catch (Exception e) {
            flag = false;
            e.printStackTrace();
        }
        return flag;
    }
}

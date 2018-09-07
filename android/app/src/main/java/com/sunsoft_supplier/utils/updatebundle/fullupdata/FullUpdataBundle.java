package com.sunsoft_supplier.utils.updatebundle.fullupdata;

import android.os.Handler;
import android.text.TextUtils;

import com.sunsoft_supplier.data.BundleInfoSp;
import com.sunsoft_supplier.utils.LogUtil;
import com.sunsoft_supplier.utils.UIUtil;
import com.sunsoft_supplier.utils.decompressionbundle.CreateFileUtil;
import com.sunsoft_supplier.utils.updatebundle.CopyFileUtil;
import com.sunsoft_supplier.utils.updatebundle.DelFileUtil;
import com.sunsoft_supplier.utils.updatebundle.FileMD5;
import com.sunsoft_supplier.utils.updatebundle.ReadFileByCharsUtil;
import com.sunsoft_supplier.utils.updatebundle.StrToFileUtil;
import com.sunsoft_supplier.utils.updatebundle.diff_match_patch;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.LinkedList;

/**
 * 全量更新bundle
 * Created by MJX on 2017/4/12.
 */
public class FullUpdataBundle {
    public static void startFullUpdataBundle(String oldPath, String newPath, String hashCode, String bundleName, String bundleCode) {
        String md5Str = FileMD5.getFileMD5String(newPath);
        //md5加密的小写装换成大写
        md5Str = md5Str.toUpperCase();
        //1.比较验证增量包是否有问题
        if (!TextUtils.isEmpty(md5Str) && !TextUtils.isEmpty(hashCode)) {
            if (!md5Str.equals(hashCode)) {  /*比较获取得到的md5值和得到的MD5数值是否一致*/
                return;
            }
        }
        //2.把图片全部复制到新的文件夹下（drawable-mdpi）
        //a、清空旧的文件夹
        //b、复制新的图片
        String fromPicPath = CreateFileUtil.getFilePath("bundle", bundleName + "/drawable-mdpi");
        String picNamePath = BundleInfoSp.getBundleValue("initBundleFile");
        String toPicPath = CreateFileUtil.getFilePath("usebundle", picNamePath + "/drawable-mdpi");
        DelFileUtil.delAllFile(toPicPath);
        CopyFileUtil.copyFolder(fromPicPath, toPicPath);
        //3.把index.android.bundle放到新的文件夹下(index.android.bundle)
        File fileStr2 = new File(oldPath);
        if (fileStr2.exists()) {
            fileStr2.delete();
        }
        String newStr = ReadFileByCharsUtil.readFileByChars(newPath);
        StrToFileUtil.saveStrToFile(newStr, fileStr2);
        //3.保存数据
        //        String str = CreateFileUtil.getFilePath("usebundle","");
        String str = UIUtil.getContext().getFilesDir() + "/" + "usebundle";
        BundleInfoSp.saveFullUpdateBundleInfo(str, bundleCode, bundleName);
        String clearBundleZipPath = CreateFileUtil.getFilePath("bundlezip", "");
        String clearBundlePath = CreateFileUtil.getFilePath("bundle", "");
       /* DelFileUtil.delAllFile(clearBundleZipPath);
        DelFileUtil.delAllFile(clearBundlePath);*/
    }

    /*bundle的增量更新
    *
    *
    * */
    public static void BundleIncrementalUpdate(String oldPath, String newPath, String bundleName, String bundleCode, String hashCode, Handler handler) {
        String oldParentPath = new File(oldPath).getParent();
        String newParentPath = new File(newPath).getParent();

        String oldBundle = ReadFileByCharsUtil.readFileByChars(oldPath);
        String newBundle = ReadFileByCharsUtil.readFileByChars(newPath);
        //把新的bundle的pathStr转化成patches
        Long ll = System.currentTimeMillis();
        diff_match_patch dmp = new diff_match_patch();
        LinkedList<diff_match_patch.Patch> patches = (LinkedList<diff_match_patch.Patch>) dmp.patch_fromText(newBundle);
        Long aa = System.currentTimeMillis();
        LogUtil.logMsg("合并花费时间1：" + (aa - ll));
        //把旧的bundle的字符串和新的patches生成全新的增量包
        Object[] results = dmp.patch_apply(patches, oldBundle);
        LogUtil.logMsg("合并花费时间2：" + (System.currentTimeMillis() - aa));
        //全新的bundle的增量包
        String newStr = (String) results[0];
        //把字符串存储到相对应的文件中
        //1.存在index.android.bundle文件就将其删除
        //2.不存在文件就新建一个
        String needDelBundle = UIUtil.getContext().getFilesDir() + "/" + "bundle" + "/" + "index.android.bundle";
        File fileStr = new File(needDelBundle);
        if (fileStr.exists()) {
            fileStr.delete();
        }
        StrToFileUtil.saveStrToFile(newStr, fileStr);

        //校验全新的增量包是否正确
        String md5Str = FileMD5.getFileMD5String(fileStr);
        //md5加密的小写装换成大写
        md5Str = md5Str.toUpperCase();
        //1.比较验证增量包是否有问题，hashcode是合成后的新的bundle文件的MD5码
        if (!TextUtils.isEmpty(md5Str) && !TextUtils.isEmpty(hashCode)) {
            if (!md5Str.equals(hashCode)) {  /*比较获取得到的md5值和得到的MD5数值是否一致*/
                LogUtil.logMsg("BundleIncrementalUpdate:hashcode不一致");
                return;
            }
        }
//        handler.sendEmptyMessage(0);

        //回滚使用的存放图片的文件夹
        String tempPicFile = "";
        String updateJson = newParentPath + "/" + "update.json";
        String jsonStr = ReadFileByCharsUtil.readFileByChars(updateJson);
        if (!TextUtils.isEmpty(jsonStr)) {
            try {
                LogUtil.logMsg("增量需要刪除的：" + jsonStr);
                String url = "";
                JSONArray jsonArray = new JSONArray(jsonStr);
                //创建一个回滚的文件夹
                String picFile = createPicFile();
                tempPicFile = picFile;
                String delPath;
                if (jsonArray != null && jsonArray.length() > 0) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        if (jsonObject1.has("url")) {
                            url = jsonObject1.getString("url");
                            // String delPath = activity.getFilesDir() +  "/" + "usebundle"+"/" + SpReact.getInitBundleFileName(MainApplication.getInstance()) + "/" + "drawable-mdpi";
                            // delPath = delPath  + url;
                            delPath = oldParentPath + url;
                            LogUtil.logMsg("增量需要刪除的路径：" + delPath);
                            //为了回滚，复制图片
                            CopyFileUtil.copyFile(delPath, picFile);
                            DelFileUtil.delFile(delPath);
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
                //出现异常的情况，把以前删除的图片再回滚
                CopyFileUtil.copyFile(tempPicFile, oldParentPath);
                LogUtil.logMsg("增量更新异常：" + e.toString());
                return;
            }
        }
//        handler.sendEmptyMessage(0);
        //4.将新的生成的增量包复制到需要用到的目录下
        File fileStr2 = new File(oldPath);
        if (fileStr2.exists()) {
            fileStr2.delete();
        }
        //更新bundle文件内容
        StrToFileUtil.saveStrToFile(newStr, fileStr2);
//        handler.sendEmptyMessage(0);
        //增量文件夹中的内容复制进使用的bundle文件夹中
        CopyFileUtil.copyFolderIncrementalUpdateBundle(newParentPath, fileStr2.getParent());
        //保存bundle信息
        String str = UIUtil.getContext().getFilesDir() + "/" + "usebundle";
        BundleInfoSp.saveFullUpdateBundleInfo(str, bundleCode, BundleInfoSp.getBundleValue("initBundleFile"));
        //更新成功后，清楚掉临时文件
        String clearBundleZipPath = CreateFileUtil.getFilePath("bundlezip", "");
        String clearBundlePath = CreateFileUtil.getFilePath("bundle", "");
        DelFileUtil.delAllFile(clearBundleZipPath);
        DelFileUtil.delAllFile(clearBundlePath);
    }

    /**
     * 为了回滚需要的方法
     * 1.创建一个文件夹
     * 2.把删除的图片再复制回去
     */
    private static String createPicFile() {
        //错误
        //        File cacheDir = activity.getCacheDir();
        File cacheDir = UIUtil.getContext().getFilesDir();
        //增量更新之后使用的bundle
        String bundlePic = cacheDir + "/" + "bundle" + "/pic";
        File bundleFilePic = new File(bundlePic);
        if (!bundleFilePic.exists()) {
            File dir = new File(bundlePic);
            dir.mkdir();
        }
        return bundlePic;
    }
}

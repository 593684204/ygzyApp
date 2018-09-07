package com.sunsoft_supplier.utils.updatebundle;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 计算文件的MD5
 */
public class FileMD5 {
    protected static char hexDigits[] = {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};
    protected static MessageDigest messageDigest = null;
    static{
        try{
            messageDigest = MessageDigest.getInstance("MD5");
        }catch (NoSuchAlgorithmException e) {
            System.err.println(FileMD5.class.getName()+"初始化失败，MessageDigest不支持MD5Util.");
            e.printStackTrace();
        }
    }

    /**
     * 计算文件的MD5
     * @param fileName 文件的绝对路径
     * @return
     * @throws IOException
     */
    public static String getFileMD5String(String fileName){
        File f = new File(fileName);
        return getFileMD5String(f);
    }

    /**
     * 计算文件的MD5，重载方法
     * @param file 文件对象
     * @return
     * @throws IOException
     */
    public static String getFileMD5String(File file) {
        FileInputStream in = null;
        try {
            in = new FileInputStream(file);
            FileChannel ch = in.getChannel();
            MappedByteBuffer byteBuffer = null;
            try {
                byteBuffer = ch.map(FileChannel.MapMode.READ_ONLY, 0, file.length());
            } catch (IOException e) {
                e.printStackTrace();
            }
            messageDigest.update(byteBuffer);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return bufferToHex(messageDigest.digest());
    }

    private static String bufferToHex(byte bytes[]) {
        return bufferToHex(bytes, 0, bytes.length);
    }

    private static String bufferToHex(byte bytes[], int m, int n) {
        StringBuffer stringbuffer = new StringBuffer(2 * n);
        int k = m + n;
        for (int l = m; l < k; l++) {
            appendHexPair(bytes[l], stringbuffer);
        }
        return stringbuffer.toString();
    }

    private static void appendHexPair(byte bt, StringBuffer stringbuffer) {
        char c0 = hexDigits[(bt & 0xf0) >> 4];
        char c1 = hexDigits[bt & 0xf];
        stringbuffer.append(c0);
        stringbuffer.append(c1);
    }

    public static void main(String[] args) throws IOException {
//        String fileName = "H:\\appworkspace\\iOS_20160813_2_0_0_1_6.zip";
        String fileName = "H:\\111.txt";
        String fileName2 = "H:\\123.txt";
        String fileName3 = "i:\\666\\Android_20160816_2_0_0_1_3.zip";
/*
        long start = System.currentTimeMillis();
        System.out.println("md5:"+getFileMD5String(fileName));
        System.out.println("md5:"+getFileMD5String(fileName2));
        System.out.println("正式md5:"+getFileMD5String(fileName3));
        long end = System.currentTimeMillis();
        System.out.println("Consume " + (end - start) + "ms");*/
//        Unzip(fileName3,"i:\\666\\");
    }

//    private static void Unzip(String zipFile, String targetDir) {
//        int BUFFER = 4096; //这里缓冲区我们使用4KB，
//        String strEntry; //保存每个zip的条目名称
//
//        try {
//            BufferedOutputStream dest = null; //缓冲输出流
//            FileInputStream fis = new FileInputStream(zipFile);
//            ZipInputStream zis = new ZipInputStream(new BufferedInputStream(fis));
//            ZipEntry entry; //每个zip条目的实例
//            while ((entry = zis.getNextEntry()) != null) {
//
//
//                try {
//                   // Log.i("Unzip: ","="+ entry);
//                    int count;
//                    byte data[] = new byte[BUFFER];
//                    strEntry = entry.getName();
//
////                  修改路径
////                    File entryFile = new File(targetDir + strEntry);
//                    File entryFile = new File(targetDir +"/"+ strEntry);
//                    //File entryFile = new File(targetDir+"/");
////                    File entryFile = new File(targetDir );
//                    File entryDir = new File(entryFile.getParent());
//                    if(entryDir.isDirectory()==false){
//                        if (!entryDir.exists()) {
//                            entryDir.mkdirs();
//                        }
//                    }else{
//                        FileOutputStream fos = new FileOutputStream(entryFile);
//                        dest = new BufferedOutputStream(fos, BUFFER);
//                        while ((count = zis.read(data, 0, BUFFER)) != -1) {
//                            dest.write(data, 0, count);
//                        }
//                        dest.flush();
//                    }
//                } catch (Exception ex) {
//                    ex.printStackTrace();
//                }
//            }
//            // dest.flush();
//            dest.close();
//            zis.close();
//        } catch (Exception cwj) {
//            cwj.printStackTrace();
//        }
//    }
}


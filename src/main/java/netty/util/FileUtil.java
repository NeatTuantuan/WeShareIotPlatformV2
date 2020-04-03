package netty.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @ClassName FileUtil
 * @Description TODO
 * @Author tuantuan
 * @Date 2020/4/2 下午5:25
 * @Version 1.0
 * @Attention Copyright (C), 2004-2019, BDILab, XiDian University
 **/
public class FileUtil {
    /**
     * 获取用户脚本的二进制字节流
     * @return
     */
    public static byte[] getData(String path){
        File file = new File(path);
        if (file.exists()){
            FileInputStream in = null;
            ByteArrayOutputStream out = null;
            try {
                in = new FileInputStream(file);
                out = new ByteArrayOutputStream();

                byte[] buffer = new byte[1024];
                int size = 0;
                while ((size = in.read(buffer)) != -1) {
                    out.write(buffer, 0, size);
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    in.close();
                } catch (IOException e) {

                    e.printStackTrace();
                }
            }
            return out.toByteArray();
        }else{
            return null;
        }
    }

    /**
     * 获取目录下所有文件名字
     * @param path
     * @param fileNameList
     */
    public static void getAllFileName(String path,ArrayList<String> fileNameList) {
        //ArrayList<String> files = new ArrayList<String>();
        boolean flag = false;
        File file = new File(path);
        File[] tempList = file.listFiles();

        for (int i = 0; i < tempList.length; i++) {
            if (tempList[i].isFile()) {
                //fileNameList.add(tempList[i].toString());
                fileNameList.add(tempList[i].getName());
            }
            if (tempList[i].isDirectory()) {
                getAllFileName(tempList[i].getAbsolutePath(),fileNameList);
            }
        }
        return;
    }
}

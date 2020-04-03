package netty.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @ClassName UserClassLoader
 * @Description TODO
 * @Author tuantuan
 * @Date 2020/4/2 下午5:21
 * @Version 1.0
 * @Attention Copyright (C), 2004-2019, BDILab, XiDian University
 **/
public class UserClassLoader extends ClassLoader {
    private String path;

    public UserClassLoader(String path){
        this.path = path;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        Class log = null;
        // 获取该class文件字节码数组
        byte[] classData = FileUtil.getData(path);

        if (classData != null) {
            // 将class的字节码数组转换成Class类的实例
            log = defineClass(name, classData, 0, classData.length);
        }
        return log;
    }


}

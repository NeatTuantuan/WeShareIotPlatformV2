package netty.util;

import java.util.ResourceBundle;

/**
 * @ClassName PropertiesUtil
 * @Description TODO 获取properties配置文件工具类
 * @Author tuantuan
 * @Date 2020/4/3 上午10:37
 * @Version 1.0
 * @Attention Copyright (C), 2004-2019, BDILab, XiDian University
 **/
public class PropertiesUtil {
    private static final ResourceBundle resourceBundle;

    static{
        resourceBundle = ResourceBundle.getBundle("systemConfig");
    }

    public static String getKey(String key){
        return resourceBundle.getString(key);
    }
}

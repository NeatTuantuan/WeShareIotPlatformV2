package flink.utils;

/**
 * @ClassName StringUtils
 * @Description TODO
 * @Author tuantuan
 * @Date 2020/6/1 下午1:06
 * @Version 1.0
 * @Attention Copyright (C), 2004-2019, BDILab, XiDian University
 **/
public class StringUtils {
    /**
     * 获取设备id
     * @param topic /weshare/iot/sys/产品id/设备id/thing/model/up_raw
     * @return
     */
    public static String getDeviceId(String topic){
        String[] temp = topic.split("/");
        return temp[5];
    }

    /**
     * 获取产品id
     * @param topic /weshare/iot/sys/产品id/设备id/thing/model/up_raw
     * @return
     */
    public static String getProductId(String topic){
        String[] temp = topic.split("/");
        return temp[4];
    }
}

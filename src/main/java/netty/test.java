package netty;

import com.alibaba.fastjson.JSONObject;
import netty.deviceMessage.DeviceMessage;
import netty.util.FileUtil;
import netty.util.PropertiesUtil;

import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * @ClassName test
 * @Description TODO
 * @Author tuantuan
 * @Date 2020/4/3 上午10:34
 * @Version 1.0
 * @Attention Copyright (C), 2004-2019, BDILab, XiDian University
 **/
public class test {
    public static void main(String[] args) {
        DeviceMessage deviceMessage = new DeviceMessage();
        deviceMessage.setDEVICE_ID("test");
        deviceMessage.setPRODUCT_ID("test2");
        JSONObject.parseObject(deviceMessage.toString());
    }
}

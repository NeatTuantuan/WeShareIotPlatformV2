package flink;

import netty.devicemessage.DeviceMessage;
import netty.util.DeviceMessageJson;

import java.io.IOException;

/**
 * @ClassName Main
 * @Description TODO
 * @Author tuantuan
 * @Date 2020/4/5 下午5:05
 * @Version 1.0
 * @Attention Copyright (C), 2004-2019, BDILab, XiDian University
 **/
public class Main {
    public static void main(String[] args) throws IOException {
        DeviceMessage deviceMessage = DeviceMessageJson.JsonToDeviceMessage("{\"deviceMessageType\":0,\"topic\":\"topic\"}");

        System.out.print(deviceMessage.getTopic());
    }
}

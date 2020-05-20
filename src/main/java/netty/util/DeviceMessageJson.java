package netty.util;

import com.alibaba.fastjson.JSONObject;
import netty.deviceMessage.DeviceMessage;

/**
 * @ClassName DeviceMessageJson
 * @Description TODO
 * @Author tuantuan
 * @Date 2020/4/24 上午9:59
 * @Version 1.0
 * @Attention Copyright (C), 2004-2019, BDILab, XiDian University
 **/
public class DeviceMessageJson {
    public static JSONObject deviceMessageJson;
    public static JSONObject deviceMessageToJson(DeviceMessage deviceMessage){
        deviceMessageJson = new JSONObject();
        deviceMessageJson.put("PRODUCT_ID",deviceMessage.getPRODUCT_ID());
        deviceMessageJson.put("DEVICE_ID",deviceMessage.getDEVICE_ID());
        deviceMessageJson.put("formatData",deviceMessage.getFormatData());
        deviceMessageJson.put("META_DATA",deviceMessage.getMETA_DATA());
        return deviceMessageJson;
    }

    public static DeviceMessage JsonToDeviceMessage(String msg){
        JSONObject jsonObject = JSONObject.parseObject(msg);

        String productId = jsonObject.getString("PRODUCT_ID");
        String deviceId = jsonObject.getString("DEVICE_ID");
        JSONObject temp = (JSONObject) jsonObject.get("formatData");
        byte[] metaData = jsonObject.getBytes("META_DATA");

        return new DeviceMessage(productId,deviceId,temp,metaData);
    }
}

package netty.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import netty.devicemessage.DeviceMessage;

/**
 * @ClassName DeviceMessageJson
 * @Description TODO
 * @Author tuantuan
 * @Date 2020/4/24 上午9:59
 * @Version 1.0
 * @Attention Copyright (C), 2004-2019, BDILab, XiDian University
 **/
public class DeviceMessageJson {

    /**
     * 实体类转json对象
     * @param deviceMessage
     * @return
     */
    public static JSONObject deviceMessageToJson(DeviceMessage deviceMessage){
        return JSON.parseObject(JSON.toJSONString(deviceMessage));
    }

    /**
     * 字符串转实体类
     * @param msg
     * @return
     */
    public static DeviceMessage JsonToDeviceMessage(String msg){
        return JSONObject.parseObject(msg,DeviceMessage.class);
    }
}

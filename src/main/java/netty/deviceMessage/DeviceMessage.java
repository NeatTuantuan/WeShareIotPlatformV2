package netty.deviceMessage;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.util.JSONPObject;
import lombok.Data;

/**
 * @ClassName DeviceMessage
 * @Description TODO
 * @Author tuantuan
 * @Date 2020/4/2 下午4:54
 * @Version 1.0
 * @Attention Copyright (C), 2004-2019, BDILab, XiDian University
 **/
@Data
public class DeviceMessage {
    /**
     * 产品ID
     */
    private String PRODUCT_ID;
    /**
     * 设备ID
     */
    private String DEVICE_ID;

    /**
     * 解析的json对象
     */
    private JSONObject formatData;

    /**
     * 未被解析的byte数组
     */
    private byte[] META_DATA;
}

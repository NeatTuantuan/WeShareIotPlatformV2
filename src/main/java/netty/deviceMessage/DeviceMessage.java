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
public class DeviceMessage{
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

    public String getPRODUCT_ID() {
        return PRODUCT_ID;
    }

    public void setPRODUCT_ID(String PRODUCT_ID) {
        this.PRODUCT_ID = PRODUCT_ID;
    }

    public String getDEVICE_ID() {
        return DEVICE_ID;
    }

    public void setDEVICE_ID(String DEVICE_ID) {
        this.DEVICE_ID = DEVICE_ID;
    }

    public JSONObject getFormatData() {
        return formatData;
    }

    public void setFormatData(JSONObject formatData) {
        this.formatData = formatData;
    }

    public byte[] getMETA_DATA() {
        return META_DATA;
    }

    public void setMETA_DATA(byte[] META_DATA) {
        this.META_DATA = META_DATA;
    }

    public DeviceMessage(){
    }

    public DeviceMessage(String productId, String deviceId, JSONObject formatData, byte[] metaData){
        this.PRODUCT_ID = productId;
        this.DEVICE_ID = deviceId;
        this.formatData = formatData;
        this.META_DATA = metaData;
    }

}

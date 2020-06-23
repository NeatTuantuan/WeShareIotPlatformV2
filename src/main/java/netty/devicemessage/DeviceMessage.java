package netty.devicemessage;

import lombok.Data;

/**
 * @ClassName DeviceMessage
 * @Description TODO 变量上报实体类封装的是设备发送的数据，设备影子实体类相当于心跳，主要包括在线离线信息
 * @Author tuantuan
 * @Date 2020/4/2 下午4:54
 * @Version 1.0
 * @Attention Copyright (C), 2004-2019, BDILab, XiDian University
 **/
@Data
public class DeviceMessage{
    /**
     * 消息类型：
     * 0-设备影子
     * 1-变量上报
     */
    private int deviceMessageType;
    /**
     * 设备topic
     */
    private String topic;
    /**
     * 设备影子实体
     */
    private DeviceShadow deviceShadow;
    /**
     * 变量上报实体类
     */
    private VariableReport variableReport;

    public DeviceMessage(){
    }

    public DeviceMessage(int deviceMessageType, String topic, DeviceShadow deviceShadow, VariableReport variableReport){
        this.deviceMessageType = deviceMessageType;
        this.topic = topic;
        this.deviceShadow = deviceShadow;
        this.variableReport = variableReport;
    }

    public String getTopic() {
        return topic;
    }

    public void setDeviceMessageType(int deviceMessageType) {
        this.deviceMessageType = deviceMessageType;
    }

    public VariableReport getVariableReport() {
        return variableReport;
    }

    public int getDeviceMessageType() {
        return deviceMessageType;
    }

    public DeviceShadow getDeviceShadow() {
        return deviceShadow;
    }
}

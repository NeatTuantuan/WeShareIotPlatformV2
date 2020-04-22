package flink.dao;

import lombok.Data;
import netty.deviceMessage.DeviceMessage;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;

/**
 * @ClassName Rule
 * @Description TODO
 * @Author tuantuan
 * @Date 2020/4/5 下午4:58
 * @Version 1.0
 * @Attention Copyright (C), 2004-2019, BDILab, XiDian University
 **/
@Data
public class Rule implements Serializable{
    //规则对应设备id
    private String DeviceID;
    //规则对应产品id
    private String productID;
    //通知频率标志，0为单次通知，1为重复通知
    private int flag = 0;

    //判断函数
    public Boolean Judge(DeviceMessage deviceMessage,String attribute){return false;}

//    public Rule(String attribute){
//        this.attribute = attribute;
//    }
//
//    public Rule(){
//    }

    public String getDeviceID() {
        return DeviceID;
    }

    public void setDeviceID(String deviceID) {
        DeviceID = deviceID;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}

package flink.dao;

import lombok.Data;
import netty.deviceMessage.DeviceMessage;

/**
 * @ClassName DigitTypeRule
 * @Description TODO 数字类型规则。区间划分
 * @Author tuantuan
 * @Date 2020/4/5 下午4:53
 * @Version 1.0
 * @Attention Copyright (C), 2004-2019, BDILab, XiDian University
 **/
@Data
public class DigitTypeRule extends Rule{

    //区间起始值
    private double START_SECTION = 0;
    //区间结束值
    private double END_SECTION = 1;
    /**
     * 触发条件：0-大于
     * 1-小与
     * 2-区间
     * 3-小于或大于
     * 默认为区间
     */
    private int triggerCondition = 2;

    @Override
    public Boolean Judge(DeviceMessage deviceMessage,String attribute) {
        //拿到deviceMessage中对应的规则字段的值
        String attributeValue = deviceMessage.getFormatData().getString(attribute);

        Double doubleAttributeValue = Double.parseDouble(attributeValue);
        switch (triggerCondition){
            case 0:
                return doubleAttributeValue > END_SECTION ? true : false;
            case 1:
                return doubleAttributeValue < START_SECTION ? true : false;
            case 2:
                return (doubleAttributeValue <= END_SECTION && doubleAttributeValue >= START_SECTION) ? true : false;
            case 3:
                return(doubleAttributeValue > END_SECTION || doubleAttributeValue < START_SECTION) ? true : false;
            default :
                return false;
        }
    }

}

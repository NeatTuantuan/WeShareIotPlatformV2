package flink.dao;

import lombok.Data;
import netty.deviceMessage.DeviceMessage;

/**
 * @ClassName BoolTypeRule
 * @Description TODO
 * @Author tuantuan
 * @Date 2020/4/5 下午4:59
 * @Version 1.0
 * @Attention Copyright (C), 2004-2019, BDILab, XiDian University
 **/
@Data
public class BoolTypeRule extends Rule{
    /**
     * 触发条件：0或者1
     */
    private int triggerCondition = 0;

    @Override
    public Boolean Judge(DeviceMessage deviceMessage, String attribute) {
        return deviceMessage.getFormatData().getString(attribute).equals(String.valueOf(triggerCondition));
    }

    public void setTriggerCondition(int triggerCondition) {
        this.triggerCondition = triggerCondition;
    }


//    public BoolTypeRule(String attribute){
//        super(attribute);
//    }
}

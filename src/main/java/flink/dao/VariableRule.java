package flink.dao;

import lombok.Data;
import netty.deviceMessage.DeviceMessage;

/**
 * @ClassName VariableRule
 * @Description TODO
 * @Author tuantuan
 * @Date 2020/4/5 下午5:02
 * @Version 1.0
 * @Attention Copyright (C), 2004-2019, BDILab, XiDian University
 **/
@Data
public class VariableRule extends Rule {
    /**
     * 规则flag：0-bool类型规则
     * 1-digit类型规则
     */
    private int variableFlag = 0;

    private BoolTypeRule boolTypeRule = null;

    private DigitTypeRule digitTypeRule = null;
    //规则字段
    private String attribute;

    public Boolean Judge(DeviceMessage deviceMessage) {
        return variableFlag == 0 ? boolTypeRule.Judge(deviceMessage,attribute) : digitTypeRule.Judge(deviceMessage,attribute);
    }
}
